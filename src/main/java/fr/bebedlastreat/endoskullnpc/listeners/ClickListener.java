package fr.bebedlastreat.endoskullnpc.listeners;

import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.inventories.GameMenuGUI;
import fr.bebedlastreat.endoskullnpc.inventories.box.BoxUltimeGUI;
import fr.bebedlastreat.endoskullnpc.inventories.box.BoxVoteGUI;
import fr.bebedlastreat.endoskullnpc.utils.PlayerManager;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.util.HashMap;
import java.util.UUID;

public class ClickListener implements Listener {

    private Main main;

    private HashMap<UUID, Long> pearlCooldown = new HashMap<>();

    public ClickListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction() == Action.PHYSICAL) return;
        ItemStack current = e.getItem();
        if (current == null) return;
        if (getName(current).equalsIgnoreCase(getName(PlayerManager.getBoutique()))) {
            player.performCommand("boutique");
            e.setCancelled(true);
        }
        if (getName(current).equalsIgnoreCase(getName(PlayerManager.getProfil(player)))) {
            player.performCommand("profile");
            e.setCancelled(true);
        }
        if (getName(current).equalsIgnoreCase(getName(PlayerManager.getMenu()))) {
            System.out.println(FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId()));
            if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
                FloodgatePlayer floodgatePlayer = FloodgateApi.getInstance().getPlayer(player.getUniqueId());
                SimpleForm simpleForm = SimpleForm.builder().title("§c§lEndoSkull §8» §a§lMenu Principal")
                        .button("PvpKit", FormImage.Type.URL, "https://images.emojiterra.com/twitter/512px/2694.png")
                        .button("Bedwars Goulag", FormImage.Type.URL, "https://images.emojiterra.com/google/android-nougat/512px/1f6cf.png")
                        .build();
                floodgatePlayer.sendForm(simpleForm);
                return;
            }
            new GameMenuGUI(player).open(player);
            e.setCancelled(true);
        }
        if (getName(current).equalsIgnoreCase(getName(PlayerManager.getCosmetics()))) {
            player.performCommand("cosmetics");
            e.setCancelled(true);
        }
        if (getName(current).equalsIgnoreCase(getName(PlayerManager.getPearl(player)))) {
            e.setCancelled(true);
            player.updateInventory();
            if (pearlCooldown.containsKey(player.getUniqueId()) && pearlCooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10f, 0.3f);
                return;
            }
            if (player.getVehicle() != null && player.getVehicle().getType() == EntityType.ENDER_PEARL) player.getVehicle().remove();
            EnderPearl pearl = player.launchProjectile(EnderPearl.class);
            pearlCooldown.put(player.getUniqueId(), System.currentTimeMillis() + 1000);
            pearl.setPassenger(player);
            player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 50, 50);
        }
    }
    @EventHandler
    public void onClickBlock(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        if (block == null || block.getType() == Material.AIR) return;
        Location loc = block.getLocation();
        if (loc.getX() == -256 && loc.getY() == 63 && block.getZ() == -274) {
            if (main.getOpeningKeys().containsKey(player)) {
                main.getOpeningKeys().get(player).open(player);
                return;
            }
            new BoxVoteGUI(player).open(player);
        }
        if (loc.getX() == -267 && loc.getY() == 63 && block.getZ() == -263) {
            new BoxUltimeGUI(player).open(player);
        }
    }

    public String getName(ItemStack it) {
        if (it.hasItemMeta() && it.getItemMeta().hasDisplayName()) return it.getItemMeta().getDisplayName();
        return it.getType().toString();
    }
}
