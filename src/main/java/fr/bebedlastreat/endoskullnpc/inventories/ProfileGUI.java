package fr.bebedlastreat.endoskullnpc.inventories;

import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ProfileGUI extends CustomGui {
    public ProfileGUI(Player player) {
        super(3, "§c§lEndoSkull §8» §b§lProfil");
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f);

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(player.getName());
        skull.setItemMeta(skullMeta);

        setItem(10, new CustomItemStack(skull).setName("§a" + player.getName()));

        setItem(13, new CustomItemStack(Material.REDSTONE_COMPARATOR).setName("§e§lParamètres"), player1 -> {
            new SettingsGUI(player1).open(player1);
        });
        setItem(14, new CustomItemStack(Material.RAW_FISH, 1, (byte) 2).setName("§a§lAmis"), player1 -> {
            new FriendsGUI(player1, 0).open(player1);
        });
        setItem(15, new CustomItemStack(Material.ITEM_FRAME).setName("§e§lDemandes d'amis"), player1 -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "forward " + player.getName() + " friend requests ");
        });
        setItem(16, new CustomItemStack(Material.DOUBLE_PLANT, 1, (byte) 4).setName("§a§lStatistiques"), player1 -> {
            new StatsGUI(player1).open(player1);
        });
    }
}
