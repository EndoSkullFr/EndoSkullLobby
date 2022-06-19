package fr.bebedlastreat.endoskullnpc.inventories;

import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.utils.PlayerManager;
import fr.endoskull.api.commons.account.Account;
import fr.endoskull.api.commons.account.AccountProvider;
import fr.endoskull.api.commons.paf.FriendSettingsSpigot;
import fr.endoskull.api.commons.paf.FriendUtils;
import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Set;

public class SettingsGUI extends CustomGui {
    public SettingsGUI(Player p) {
        super(4, "§c§lEndoSkull §8» §e§lParamètres");
        p.playSound(p.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            Account account = AccountProvider.getAccount(p.getUniqueId());
            String nexColor = getNextColor(account);

            setItem(9, new CustomItemStack(Material.ENDER_PEARL).setName("§eCouleur du pearl rider"));
            setItem(18, new CustomItemStack(PlayerManager.getPearl(p)).setName("§7Cliquez pour passer au " + ChatColor.valueOf(nexColor.toUpperCase()) + nexColor), player -> {
                String color = account.getProperty("pearl_rider_color", "Green");
                if (color.equalsIgnoreCase("Green")) {
                    color = "Red";
                }
                else if (color.equalsIgnoreCase("Red")) {
                    color = "Blue";
                }
                else if (color.equalsIgnoreCase("Blue")) {
                    color = "Green";
                }
                account.setProperty("pearl_rider_color", color);
                new SettingsGUI(player).open(player);
                player.getInventory().setItem(8, PlayerManager.getPearl(player));
            });

            setItem(26, new CustomItemStack(Material.ARROW).setName("§eRetour"), player1 -> {
                new ProfileGUI(player1).open(player1);
            });
            for (FriendSettingsSpigot value : FriendSettingsSpigot.values()) {
                boolean b = FriendUtils.getSetting(p.getUniqueId(), value).equalsIgnoreCase("1");
                setItem(value.getSlot() + 1, new CustomItemStack(value.getItem()).setName("§e" + value.getName()));
                setItem(value.getSlot() + 10, new CustomItemStack(Material.INK_SACK).setData((byte) (b ? 10 : 1)).setName("§7Cliquez pour " + (b ? "§cDésativer" : "§aActiver")), player -> {
                    FriendUtils.setSetting(player.getUniqueId(), value, (b ? "0" : "1"));
                    player.sendMessage("§a§lAMIS §8» " + (b ? value.getDisable() : value.getEnable()));
                    new SettingsGUI(player).open(player);
                });
            }
        });
    }

    private static String getNextColor(Account account) {
        String color = account.getProperty("pearl_rider_color", "Green");
        if (color.equalsIgnoreCase("Green")) {
            color = "Red";
        }
        else if (color.equalsIgnoreCase("Red")) {
            color = "Blue";
        }
        else if (color.equalsIgnoreCase("Blue")) {
            color = "Green";
        }
        return color;
    }
}
