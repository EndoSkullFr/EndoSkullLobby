package fr.bebedlastreat.endoskullnpc.utils;

import fr.bebedlastreat.endoskullnpc.Main;
import fr.endoskull.api.commons.Account;
import fr.endoskull.api.commons.AccountProvider;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import fr.endoskull.api.spigot.utils.Title;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerManager {
    private static ItemStack boutique = new CustomItemStack(Material.ENDER_CHEST).setName("§a§lBoutique");
    private static ItemStack profil = new CustomItemStack(Material.SKULL_ITEM, 1, (byte) 3).setName("§b§lProfil");
    private static ItemStack menu = new CustomItemStack(Material.NETHER_STAR).setName("§a§lMenu de jeux");
    private static ItemStack cosmetics = new CustomItemStack(Material.ENDER_PORTAL_FRAME).setName("§e§lCosmétiques");

    public static void resetPlayer(Player player) {
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setWalkSpeed(0.2f);
        player.setFireTicks(0);
        player.setFallDistance(0);
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[player.getInventory().getArmorContents().length]);
        teleportToSpawn(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f);
        Title.sendTitle(player, 20, 40, 20, "§cEndoSkull Network", "§eVous êtes connecté sur le Lobby");
        player.sendMessage(
                "§8§m+---------------***---------------+\n" +
                "§r \n" +
                "§r    §7Bienvenue, §b" + player.getName() + "§r §7sur le serveur\n" +
                "§r \n" +
                "§r  §c§lSITE WEB §fwww.endoskull.fr\n" +
                "§r  §6§lBOUTIQUE §fboutique.endoskull.fr\n" +
                "§r  §9§lDISCORD §fdiscord.endoskull.fr\n" +
                "§r \n" +
                "§8§m+---------------***---------------+");
        player.getInventory().setItem(0, boutique);
        player.getInventory().setItem(1, getProfil(player));
        player.getInventory().setItem(4, menu);
        player.getInventory().setItem(7, cosmetics);
        player.getInventory().setItem(8, getPearl(player));
    }

    public static void teleportToSpawn(Player player) {
        player.teleport(new Location(Bukkit.getWorld("world"), -271.5, 62.1, -278.5, -45, 0));
    }

    public static void spawnFirework() {
        Firework firework = Bukkit.getWorld("world").spawn(new Location(Bukkit.getWorld("world"), -271.5, 62, -278.5), Firework.class);
        FireworkMeta fMeta = firework.getFireworkMeta();
        fMeta.setPower(1);
        fMeta.addEffect(FireworkEffect.builder().withColor(Color.AQUA, Color.RED, Color.TEAL, Color.WHITE).flicker(true).trail(true).with(FireworkEffect.Type.BALL_LARGE).build());
        firework.setFireworkMeta(fMeta);
    }

    public static ItemStack getProfil(Player player) {
        ItemStack skull = profil;
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(player.getName());
        skull.setItemMeta(skullMeta);
        return skull;
    }

    public static ItemStack getBoutique() {
        return boutique;
    }

    public static ItemStack getMenu() {
        return menu;
    }

    public static ItemStack getCosmetics() {
        return cosmetics;
    }

    public static ItemStack getPearl(Player player) {
        Account account = AccountProvider.getAccount(player.getUniqueId());
        ItemStack pearl = CustomItemStack.getSkull(PearlRider.valueOf(account.getProperty("pearl_rider_color", "Green").toUpperCase()).getUrl()).setName("§2§lPearl Rider");
        return pearl;
    }
}
