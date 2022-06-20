package fr.bebedlastreat.endoskullnpc.inventories.box;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.box.Ultime;
import fr.bebedlastreat.endoskullnpc.utils.LobbyMessage;
import fr.endoskull.api.commons.account.Account;
import fr.endoskull.api.commons.account.AccountProvider;
import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import fr.endoskull.api.spigot.utils.Languages;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;

public class BoxUltimeGUI extends CustomGui {
    private static boolean openingBox = false;

    public BoxUltimeGUI(Player p) {
        super((Ultime.values().length-1)/9 + 3, Languages.getLang(p).getMessage(LobbyMessage.GUI_BOX_ULTIME), p);
        Languages lang = Languages.getLang(p);
        int lines = (Ultime.values().length-1)/9 + 3;
        p.playSound(p.getLocation(), Sound.CAT_MEOW, 50, 50);
        Account account = new AccountProvider(p.getUniqueId()).getAccount();
        int i = 0;
        for (Ultime value : Ultime.values()) {
            setItem(i, new CustomItemStack(value.getItem()).setName(lang.getMessage(value.getName())).setLore("\n" + lang.getMessage(LobbyMessage.PROBABILITY) + " " + lang.getMessage(value.getName()).substring(0, 2) + value.getPourcent() + "%"));
            i++;
        }
        for (int j = lines * 9 - 18; j < lines * 9 - 9; j++) {
            setItem(j, new CustomItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14).setName("§r"));
        }
        boolean s = account.getStatistic("key/ultime") > 1;
        setItem(lines * 9 - 5, new CustomItemStack(Material.ANVIL).setName("§c§l" + lang.getMessage(LobbyMessage.BOX_OPEN)).setLore(lang.getMessage(LobbyMessage.ULTIME_AMOUNT).replace("{amount}", String.valueOf(account.getStatistic("key/ultime"))).replace("{s}", (s ? "s" : "" ))), player -> {
            player.closeInventory();
            Account account1 = new AccountProvider(player.getUniqueId()).getAccount();
            if (account1.getStatistic("key/ultime") < 1) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 50, 50);
                player.sendMessage(lang.getMessage(LobbyMessage.NEED_ULTIME_KEY));
                return;
            } else {
                if (openingBox) {
                    player.sendMessage(lang.getMessage(LobbyMessage.ULTIME_ALREADY_OPENING));
                    player.playSound(player.getLocation(), Sound.CAT_HIT, 1f, 1f);
                    return;
                }
                account1.incrementStatistic("key/ultime", -1);
                openBox(player);
            }
        });

    }

    private void openBox(Player player) {
        openingBox = true;
        /*Location loc = new Location(player.getWorld(), -266.25, 67.5, -262.5, -180, 0);
        loc.add(-0.25, -1.66, 0);
        int i = 0;
        for(double t = 0; t < Math.PI * 2; t += Math.PI * 2 / ((double) Ultime.values().length)) {
            if (Ultime.values().length <= i) break;
            double cos = Math.cos(t);
            double sin = Math.sin(t);
            /*ArmorStand as = player.getWorld().spawn(loc.clone().add(cos, sin + (Ultime.values()[i].isSmall() ? 0.5 : 0), 0), ArmorStand.class);
            as.setVisible(false);
            as.setMarker(true);
            as.setGravity(false);
            as.setItemInHand(Ultime.values()[i].getItem());
            as.setRightArmPose(new EulerAngle(Math.toRadians(100), Math.toRadians(166), Math.toRadians(167)));*/
            /*Hologram hologram = HologramsAPI.createHologram(Main.getInstance(), loc.clone().add(cos, sin, 0));
            ItemLine itemLine = hologram.appendItemLine(Ultime.values()[i].getItem());
            i++;
        }*/

        Location loc = new Location(player.getWorld(), -266.5, 66, -262.5, -180, 0);
        scheduleFirstStep(new ArrayList<>(), 0, player, loc, Languages.getLang(player));

        /*Animatronic animatronic = new Animatronic("BoxAnim");
        List<Ultime> loots = new ArrayList<>();
        for (Ultime value : Ultime.values()) {
            for (int i = 0; i < value.getProbability() * 2; i++) {
                loots.add(value);
            }
        }
        Collections.shuffle(loots);
        animatronic.getArmorstand().setHelmet(loots.get(new Random().nextInt(loots.size())).getItem());
        animatronic.start();

        int[] ticks = {46,8,8,6,6,6,6,5,5,5,5,4,4,4,4,3,3,3,3,3,2,2,2,2,2,2,2,2};
        player.getWorld().playSound(player.getLocation(), Sound.ANVIL_USE, 1f, 1f);
        playTick(player, animatronic, loots, ticks, 0);*/
    }

    private void scheduleFirstStep(List<Hologram> holograms, int step, Player player, Location loc, Languages lang) {
        if (Ultime.values().length <= step) {
            scheduleSecondStep(holograms, player, loc, lang);
            return;
        }
        double t = Math.PI * 2 / ((double) Ultime.values().length) * step;
        double cos = Math.cos(t);
        double sin = Math.sin(t);
        loc.getWorld().playSound(loc, Sound.NOTE_PLING, 1, 1);
        Hologram hologram = HologramsAPI.createHologram(Main.getInstance(), loc.clone().add(cos, sin, 0));
        hologram.appendItemLine(Ultime.values()[step].getItem());
        holograms.add(hologram);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            scheduleFirstStep(holograms, step + 1, player, loc, lang);
        }, 3);
    }

    private void scheduleSecondStep(List<Hologram> holograms, Player player, Location loc, Languages lang) {
        if (holograms.isEmpty()) {
            List<Ultime> loots = new ArrayList<>();
            for (Ultime value : Ultime.values()) {
                for (int i = 0; i < value.getProbability() * 2; i++) {
                    loots.add(value);
                }
            }
            Collections.shuffle(loots);
            Hologram hologram = HologramsAPI.createHologram(Main.getInstance(), loc.add(0, 0.25, 0));
            TextLine textLine = hologram.appendTextLine(lang.getMessage(loots.get(0).getName()));
            ItemLine itemLine = hologram.appendItemLine(loots.get(0).getItem());
            scheduleThirdStep(hologram, textLine, itemLine, loots, 0, player, loc, lang);
            return;
        }
        loc.getWorld().playSound(loc, Sound.NOTE_BASS, 1, 1);
        holograms.get(0).delete();
        holograms.remove(0);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            scheduleSecondStep(holograms, player, loc, lang);
        }, 2);
    }

    private void scheduleThirdStep(Hologram hologram, TextLine textLine, ItemLine itemLine, List<Ultime> loots, int step, Player player, Location loc, Languages lang) {
        double timer = getTimer(1d + ((double) step /10d));
        if (timer <= 1) {
            hologram.teleport(loc);
            textLine.setText(lang.getMessage(loots.get(step).getName()));
            itemLine.setItemStack(loots.get(step).getItem());
            loc.getWorld().playSound(hologram.getLocation(), Sound.FIREWORK_LAUNCH, 1f, 1f);
            loc.getWorld().playEffect(hologram.getLocation(), Effect.LARGE_SMOKE, 500);

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                scheduleFourthStep(hologram, textLine, itemLine, loots, step + 1, 0, player, loc, lang);
            }, 2);
            return;
        }

        loc.getWorld().playSound(loc, Sound.NOTE_STICKS, 1, 1);

        double t = Math.PI * 2 / ((double) Ultime.values().length) * step;
        double sin = Math.sin(t)*0.5;
        hologram.teleport(loc.clone().add(0, sin, 0));
        textLine.setText(lang.getMessage(loots.get(step).getName()));
        itemLine.setItemStack(loots.get(step).getItem());

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            scheduleThirdStep(hologram, textLine, itemLine, loots, step + 1, player, loc, lang);
        }, Math.round(timer));
    }


    private void scheduleFourthStep(Hologram hologram, TextLine textLine, ItemLine itemLine, List<Ultime> loots, int lootStep, int step, Player player, Location loc, Languages lang) {
        if (step >= 10) {
            hologram.teleport(loc.clone().add(0, step, 0));
            textLine.setText(lang.getMessage(loots.get(step).getName()));
            itemLine.setItemStack(loots.get(step).getItem());
            loc.getWorld().playSound(hologram.getLocation(), Sound.EXPLODE, 1f, 1f);
            loc.getWorld().playEffect(hologram.getLocation(), Effect.EXPLOSION_LARGE, 500);
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                scheduleFifthStep(hologram, textLine, itemLine, loots, lootStep + 1, 0, player, loc, lang);
            }, 2);
            return;
        }
        hologram.teleport(loc.clone().add(0, step, 0));
        textLine.setText(lang.getMessage(loots.get(lootStep).getName()));
        itemLine.setItemStack(loots.get(lootStep).getItem());
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            scheduleFourthStep(hologram, textLine, itemLine, loots, lootStep + 1, step + 1, player, loc, lang);
        }, 2);
    }

    private void scheduleFifthStep(Hologram hologram, TextLine textLine, ItemLine itemLine, List<Ultime> loots, int lootStep, int step, Player player, Location loc, Languages lang) {
        if (step >= 22) {
            endOpening(loots, textLine, itemLine, lootStep, loc, player, hologram, lang);
            return;
        }
        hologram.teleport(loc.clone().add(0, 10, 0).add(0, -((double) step/2d), 0));
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            scheduleFifthStep(hologram, textLine, itemLine, loots, lootStep + 1, step + 1, player, loc, lang);
        }, 2);
    }

    private double getTimer(double x) {
        return 10/Math.pow(x, 1.05);
    }

    private void endOpening(List<Ultime> loots, TextLine textLine, ItemLine itemLine, int step, Location loc, Player player, Hologram hologram, Languages lang) {
        Ultime reward = loots.get(step);
        textLine.setText(lang.getMessage(reward.getName()));
        itemLine.setItemStack(reward.getItem());
        loc.getWorld().playSound(hologram.getLocation(), Sound.ENDERDRAGON_GROWL, 1f, 1f);
        loc.getWorld().playEffect(hologram.getLocation(), Effect.NOTE, 500);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward.getCommand().replace("%player%", player.getName()));
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(Languages.getLang(p).getMessage(LobbyMessage.ULTIME_BROADCAST).replace("{player}", player.getDisplayName()).replace("{reward}", Languages.getLang(p).getMessage(reward.getName())));
        }
        Bukkit.getConsoleSender().sendMessage(Languages.getLang(Bukkit.getConsoleSender()).getMessage(LobbyMessage.ULTIME_BROADCAST).replace("{player}", player.getDisplayName()).replace("{reward}", Languages.getLang(Bukkit.getConsoleSender()).getMessage(reward.getName())));
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            openingBox = false;
            hologram.delete();
        }, 60);
    }

    /*private void launch(Player player, Animatronic animatronic, List<Ultime> loots) {
        player.getWorld().playSound(animatronic.getArmorstand().getLocation(), Sound.FIREWORK_LAUNCH, 1f, 1f);
        player.getWorld().playEffect(animatronic.getArmorstand().getLocation(), Effect.LARGE_SMOKE, 500);
        animatronic.getArmorstand().setHelmet(loots.get(new Random().nextInt(loots.size())).getItem());
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            player.getWorld().playSound(animatronic.getArmorstand().getLocation(), Sound.EXPLODE, 1f, 1f);
            player.getWorld().playEffect(animatronic.getArmorstand().getLocation(), Effect.EXPLOSION_LARGE, 500);
            Ultime reward = loots.get(new Random().nextInt(loots.size()));
            animatronic.getArmorstand().setHelmet(reward.getItem());
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                player.getWorld().playSound(animatronic.getArmorstand().getLocation(), Sound.ENDERDRAGON_GROWL, 1f, 1f);
                player.getWorld().playEffect(animatronic.getArmorstand().getLocation().add(0,1,0), Effect.NOTE, 500);
                animatronic.getArmorstand().setHelmet(reward.getItem());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward.getCommand().replace("%player%", player.getName()));
                Bukkit.broadcastMessage("§cEndoSkull §8» §a" + player.getName() + " §fvient d'obtenir " + reward.getName() + " §fdans sa Clé Ultime");
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    animatronic.getArmorstand().remove();
                    openingBox = false;
                }, 60);
            }, 40);
        }, 15);
    }

    private void playTick(Player player, Animatronic animatronic, List<Ultime> loots, int[] ticks, final int i) {
        if (ticks.length <= i) {
            launch(player, animatronic, loots);
            return;
        }
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            player.getWorld().playSound(animatronic.getArmorstand().getLocation(), Sound.NOTE_PLING, 1f, 1f);
            animatronic.getArmorstand().setHelmet(loots.get(new Random().nextInt(loots.size())).getItem());
            playTick(player, animatronic, loots, ticks, i + 1);
        }, ticks[i]);
    }*/
}