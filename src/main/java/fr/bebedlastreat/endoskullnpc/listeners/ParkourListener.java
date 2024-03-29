package fr.bebedlastreat.endoskullnpc.listeners;

import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.database.ParkourSQL;
import fr.bebedlastreat.endoskullnpc.inventories.GameMenuGUI;
import fr.bebedlastreat.endoskullnpc.utils.*;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import fr.endoskull.api.spigot.utils.LanguageChangeEvent;
import fr.endoskull.api.spigot.utils.Languages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ParkourListener implements Listener {
    private Main main;
    private HashMap<Player, Long> cooldowns = new HashMap<>();
    private HashMap<Player, List<PotionEffect>> effects = new HashMap<>();
    private CustomItemStack checkpointItem = new CustomItemStack(Material.INK_SACK, 1, (byte) 11).setName("§e§lDernier checkpoint");
    private CustomItemStack resetItem = new CustomItemStack(Material.INK_SACK, 1, (byte) 14).setName("§6§lRecommencer");
    private CustomItemStack cancelItem = new CustomItemStack(Material.INK_SACK, 1, (byte) 1).setName("§c§lQuitter");
    private List<Player> teleporting = new ArrayList<>();

    public ParkourListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Languages lang = Languages.getLang(player);
        Block block = player.getLocation().getBlock();
        if (cooldowns.containsKey(player) && cooldowns.get(player) > System.currentTimeMillis()) return;
        if (main.getJumping().containsKey(player)) {
            ParkourProgress progress = main.getJumping().get(player);

            if (player.getFallDistance() > 10) {
                teleporting.add(player);
                player.setFallDistance(0);
                if (progress.getStage() == 0) {
                    player.teleport(progress.getParkour().getSpawn());
                } else {
                    player.teleport(progress.getParkour().getCheckPoints().get(progress.getStage() - 1).clone().add(0.5, 0, 0.5));
                }
                return;
            }

            if (progress.getParkour().getStart().equals(block.getLocation())) {
                progress.setStart(System.currentTimeMillis());
                progress.setStage(0);
                player.sendMessage(lang.getMessage(LobbyMessage.PARKOUR_RESTART).replace("{parkour}", progress.getParkour().getName()));
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f);
                cooldowns.put(player, System.currentTimeMillis() + 1000);
                return;
            }
            if (progress.getParkour().getEnd().equals(block.getLocation())) {
                main.getJumping().remove(player);
                long time = System.currentTimeMillis() - progress.getStart();
                if (ParkourSQL.hasTime(player.getUniqueId(), progress.getParkour().getName())) {
                    long record = ParkourSQL.getTime(player.getUniqueId(), progress.getParkour().getName());
                    if (time >= record) {
                        player.sendMessage(lang.getMessage(LobbyMessage.PARKOUR_FINISH).replace("{parkour}", progress.getParkour().getName()).replace("{time}", TimeUtils.getTime(time)).replace("{record}", TimeUtils.getTime(record)));
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
                    } else {
                        player.sendMessage(lang.getMessage(LobbyMessage.PARKOUR_FINISH_BEST).replace("{parkour}", progress.getParkour().getName()).replace("{time}", TimeUtils.getTime(time)).replace("{old}", TimeUtils.getTime(record)));
                        player.playSound(player.getLocation(), Sound.EXPLODE, 1f, 1f);
                        ParkourSQL.updateTime(player.getUniqueId(), player.getName(), progress.getParkour().getName(), time);
                    }
                } else {
                    player.sendMessage(lang.getMessage(LobbyMessage.PARKOUR_FINISH_FIRST).replace("{parkour}", progress.getParkour().getName()).replace("{time}", TimeUtils.getTime(time)));
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f, 1f);
                    ParkourSQL.insertTime(player.getUniqueId(), player.getName(), progress.getParkour().getName(), time);
                }
                cancelParkour(player);
                return;
            }
            int stage = progress.getStage();
            Parkour parkour = progress.getParkour();
            if (parkour.getCheckPoints().size() > stage) {
                Location nextCheckPoint = parkour.getCheckPoints().get(stage);
                if (block.equals(nextCheckPoint.getBlock())) {
                    player.sendMessage(lang.getMessage(LobbyMessage.PARKOUR_CHECKPOINT).replace("{checkpoint}", String.valueOf(stage + 1)).replace("{time}", TimeUtils.getTime(System.currentTimeMillis() - progress.getStart())));
                    progress.setStage(progress.getStage() + 1);
                    player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
                }
            }
        } else {
            Parkour parkour = main.getParkours().stream().filter(j -> j.getStart().equals(block.getLocation())).findFirst().orElse(null);
            if (parkour == null) return;
            main.getJumping().put(player, new ParkourProgress(parkour, 0, System.currentTimeMillis()));
            player.sendMessage(lang.getMessage(LobbyMessage.PARKOUR_START).replace("{parkour}", parkour.getName()));
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f);
            cooldowns.put(player, System.currentTimeMillis() + 1000);
            effects.put(player, new ArrayList<>(player.getActivePotionEffects()));
            player.setFlying(false);
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());

            }
            player.getInventory().setHeldItemSlot(8);
            player.getInventory().setItem(8, cancelItem.setName(lang.getMessage(LobbyMessage.PARKOUR_LEAVE_ITEM)));
            player.getInventory().setItem(7, resetItem.setName(lang.getMessage(LobbyMessage.PARKOUR_RESET_ITEM)));
            player.getInventory().setItem(6, checkpointItem.setName(lang.getMessage(LobbyMessage.PARKOUR_CHECKPOINT_ITEM)));

        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        main.getJumping().remove(player);
        cooldowns.remove(player);
        effects.remove(player);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Languages lang = Languages.getLang(player);
        if (e.getAction() == Action.PHYSICAL || !main.getJumping().containsKey(player)) return;
        ItemStack current = e.getItem();
        if (current == null) return;
        ParkourProgress progress = main.getJumping().get(player);
        if (getName(current).equalsIgnoreCase(lang.getMessage(LobbyMessage.PARKOUR_CHECKPOINT_ITEM))) {
            teleporting.add(player);
            if (progress.getStage() == 0) {
                player.teleport(progress.getParkour().getSpawn());
            } else {
                player.teleport(progress.getParkour().getCheckPoints().get(progress.getStage() - 1).clone().add(0.5, 0, 0.5));
            }
            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
        }
        if (getName(current).equalsIgnoreCase(lang.getMessage(LobbyMessage.PARKOUR_RESET_ITEM))) {
            teleporting.add(player);
            player.teleport(progress.getParkour().getSpawn());
            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
        }
        if (getName(current).equalsIgnoreCase(lang.getMessage(LobbyMessage.PARKOUR_LEAVE_ITEM))) {
            main.getJumping().remove(player);
            player.playSound(player.getLocation(), Sound.CAT_MEOW, 1f, 1f);
            cancelParkour(player);
        }
    }

    public String getName(ItemStack it) {
        if (it.hasItemMeta() && it.getItemMeta().hasDisplayName()) return it.getItemMeta().getDisplayName();
        return it.getType().toString();
    }

    @EventHandler
    public void onFly(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        if (main.getJumping().containsKey(player)) {
            cancelParkour(player);
            player.sendMessage(Languages.getLang(player).getMessage(LobbyMessage.PARKOUR_FLY));
        }
    }

    @EventHandler
    public void onTp(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        if (main.getJumping().containsKey(player)) {
            if (teleporting.contains(player)) {
                teleporting.remove(player);
                return;
            }
            cancelParkour(player);
            player.sendMessage(Languages.getLang(player).getMessage(LobbyMessage.PARKOUR_TELEPORT));
        }
    }

    @EventHandler
    public void onPet(EntityMountEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        if (main.getJumping().containsKey(player)) {
            e.setCancelled(true);
            player.sendMessage(Languages.getLang(player).getMessage(LobbyMessage.PARKOUR_MOUNT));
        }
    }

    private void cancelParkour(Player player) {
        main.getJumping().remove(player);
        if (effects.containsKey(player)) {
            for (PotionEffect effect : effects.get(player)) {
                player.addPotionEffect(effect);
            }
            effects.remove(player);
        }

        player.getInventory().clear(6);
        player.getInventory().setItem(7, PlayerManager.getCosmetics());
        player.getInventory().setItem(8, PlayerManager.getPearl(player));
    }

    @EventHandler
    public void onLangChange(LanguageChangeEvent e) {
        if (e.getOldLang() == e.getNewLang()) return;
        Player player = e.getPlayer();
        for (Parkour parkour : main.getParkours()) {
            parkour.clearHolo(player);
            parkour.initHologram(player);
        }
    }
}
