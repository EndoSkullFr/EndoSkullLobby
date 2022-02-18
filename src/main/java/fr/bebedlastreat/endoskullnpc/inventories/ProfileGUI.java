package fr.bebedlastreat.endoskullnpc.inventories;

import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
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
            player1.performCommand("friendsgui");
            player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
        });
        setItem(15, new CustomItemStack(Material.ITEM_FRAME).setName("§e§lDemandes d'amis"), player1 -> {
            player1.performCommand("friendrequest");
            player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
        });
        setItem(16, new CustomItemStack(Material.DOUBLE_PLANT, 1, (byte) 4).setName("§a§lStatistiques"), player1 -> {
            player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
            player1.sendMessage("");
            player1.sendMessage("§e§lEndoSkull §8» §ahttps://endoskull.fr/endostats/" + player1.getName());
            player1.sendMessage("");
        });
    }
}
