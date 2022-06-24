package fr.bebedlastreat.endoskullnpc.inventories;

import fr.bebedlastreat.endoskullnpc.utils.LobbyMessage;
import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import fr.endoskull.api.spigot.utils.Languages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ProfileGUI extends CustomGui {
    public ProfileGUI(Player player) {
        super(3, Languages.getLang(player).getMessage(LobbyMessage.GUI_PROFILE));
        Languages lang = Languages.getLang(player);
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f);

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(player.getName());
        skull.setItemMeta(skullMeta);

        setItem(10, new CustomItemStack(skull).setName("§a" + player.getName()));

        if (player.hasPermission("endoskull.lang")) {
            setItem(12, CustomItemStack.getSkull("http://textures.minecraft.net/texture/2e2cc42015e6678f8fd49ccc01fbf787f1ba2c32bcf559a015332fc5db50").setName(lang.getMessage(LobbyMessage.LANGUAGE)), player1 -> {
                new LanguageGUI(player1).open();
            });
        }
        setItem(13, new CustomItemStack(Material.REDSTONE_COMPARATOR).setName(lang.getMessage(LobbyMessage.SETTINGS)), player1 -> {
            new SettingsGUI(player1).open(player1);
        });
        setItem(14, new CustomItemStack(Material.RAW_FISH, 1, (byte) 2).setName(lang.getMessage(LobbyMessage.FRIENDS)), player1 -> {
            new FriendsGUI(player1, 0).open(player1);
        });
        setItem(15, new CustomItemStack(Material.ITEM_FRAME).setName(lang.getMessage(LobbyMessage.FRIENDS_REQUESTS)), player1 -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "forward " + player.getName() + " friend requests ");
        });
        setItem(16, new CustomItemStack(Material.DOUBLE_PLANT, 1, (byte) 4).setName(lang.getMessage(LobbyMessage.STATS)), player1 -> {
            new StatsGUI(player1).open(player1);
        });
    }
}
