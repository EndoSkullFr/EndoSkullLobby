package fr.bebedlastreat.endoskullnpc.utils;

import fr.endoskull.api.commons.account.Account;
import fr.endoskull.api.commons.account.AccountProvider;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import fr.endoskull.api.spigot.utils.Languages;
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
    private static CustomItemStack boutique = new CustomItemStack(Material.ENDER_CHEST).setName("§a§lBoutique");
    private static CustomItemStack profil = new CustomItemStack(Material.SKULL_ITEM, 1, (byte) 3).setName("§b§lProfil");
    private static CustomItemStack menu = new CustomItemStack(Material.NETHER_STAR).setName("§a§lMenu de jeux");
    private static CustomItemStack cosmetics = new CustomItemStack(Material.ENDER_PORTAL_FRAME).setName("§e§lCosmétiques");

    public static void resetPlayer(Player player) {
        Languages lang = Languages.getLang(player);
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
        for (PotionEffect activePotionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(activePotionEffect.getType());
        }
        //player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f);
        Title.sendTitle(player, 20, 40, 20, "§cEndoSkull Network", lang.getMessage(LobbyMessage.LOBBY_TITLE));
        player.sendMessage(lang.getMessage(LobbyMessage.LOBBY_MESSAGE).replace("{player}", player.getName()));
        player.getInventory().setItem(0, boutique.setName(lang.getMessage(LobbyMessage.SHOP_ITEM)));
        player.getInventory().setItem(1, getProfil(player).setName(lang.getMessage(LobbyMessage.PROFILE_ITEM)));
        player.getInventory().setItem(4, menu.setName(lang.getMessage(LobbyMessage.MENU_ITEM)));
        player.getInventory().setItem(7, cosmetics.setName(lang.getMessage(LobbyMessage.COSMETICS_ITEM)));
        player.getInventory().setItem(8, getPearl(player));
        Account account = AccountProvider.getAccount(player.getUniqueId());
        player.setLevel(account.getLevel());
        player.sendMessage(getXpToLevel(account.getLevel()) + " " + ((account.getXp()/account.xpToLevelSup()) + " " + account.getXp()/account.xpToLevelSup()*getXpToLevel(account.getLevel())));
        player.setExp((float) (account.getXp()/account.xpToLevelSup()*getXpToLevel(account.getLevel())));
    }

    public static void teleportToSpawn(Player player) {
        player.teleport(new Location(Bukkit.getWorld("Lobby"), -271.5, 62.1, -278.5, -45, 0));
    }

    public static void spawnFirework() {
        Firework firework = Bukkit.getWorld("Lobby").spawn(new Location(Bukkit.getWorld("Lobby"), -271.5, 62, -278.5), Firework.class);
        FireworkMeta fMeta = firework.getFireworkMeta();
        fMeta.setPower(1);
        fMeta.addEffect(FireworkEffect.builder().withColor(Color.AQUA, Color.RED, Color.TEAL, Color.WHITE).flicker(true).trail(true).with(FireworkEffect.Type.BALL_LARGE).build());
        firework.setFireworkMeta(fMeta);
    }

    public static CustomItemStack getProfil(Player player) {
        CustomItemStack skull = profil;
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
        ItemStack pearl = CustomItemStack.getSkull(PearlRider.valueOf(account.getProperty("pearl_rider_color", "Green").toUpperCase()).getUrl()).setName(Languages.getLang(player).getMessage(LobbyMessage.PEARL_RIDER));
        return pearl;
    }

    public static double getXpToLevel(int level) {
        if (level <= 15) return 2*level + 7;
        if (level <= 30) return 5*level - 38;
        return 9*level - 158;

    }
}
