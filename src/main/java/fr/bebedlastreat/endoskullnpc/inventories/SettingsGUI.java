package fr.bebedlastreat.endoskullnpc.inventories;

import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.utils.LobbyMessage;
import fr.bebedlastreat.endoskullnpc.utils.PlayerManager;
import fr.endoskull.api.commons.account.Account;
import fr.endoskull.api.commons.account.AccountProvider;
import fr.endoskull.api.commons.lang.MessageUtils;
import fr.endoskull.api.commons.paf.FriendSettingsSpigot;
import fr.endoskull.api.commons.paf.FriendUtils;
import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import fr.endoskull.api.spigot.utils.Languages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Set;

public class SettingsGUI extends CustomGui {
    public SettingsGUI(Player p) {
        super(4, Languages.getLang(p).getMessage(MessageUtils.Global.GUI_SETTINGS), p);
        Languages lang = Languages.getLang(p);
        p.playSound(p.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            Account account = AccountProvider.getAccount(p.getUniqueId());
            String nexColor = getNextColor(account);

            setItem(9, new CustomItemStack(Material.ENDER_PEARL).setName(lang.getMessage(LobbyMessage.PEARL_COLOR)));
            setItem(18, new CustomItemStack(PlayerManager.getPearl(p)).setName(lang.getMessage(LobbyMessage.CLICK_SKIP) + ChatColor.valueOf(nexColor.toUpperCase()) + lang.getMessage(LobbyMessage.valueOf(nexColor.toUpperCase()))), player -> {
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

            setItem(26, new CustomItemStack(Material.ARROW).setName(lang.getMessage(MessageUtils.Global.BACK)), player1 -> {
                new ProfileGUI(player1).open(player1);
            });
            for (FriendSettingsSpigot value : FriendSettingsSpigot.values()) {
                boolean b = FriendUtils.getSetting(p.getUniqueId(), value).equalsIgnoreCase("1");
                setItem(value.getSlot() + 1, new CustomItemStack(value.getItem()).setName("§e" + lang.getMessage(value.getName())));
                setItem(value.getSlot() + 10, new CustomItemStack(Material.INK_SACK).setData((byte) (b ? 10 : 1)).setName(lang.getMessage(MessageUtils.Global.CLICK_FOR) + " " + (b ? lang.getMessage(MessageUtils.Global.DISABLE) : lang.getMessage(MessageUtils.Global.ENABLE))), player -> {
                    FriendUtils.setSetting(player.getUniqueId(), value, (b ? "0" : "1"));
                    player.sendMessage(lang.getMessage(MessageUtils.Global.FRIENDS) + (b ? lang.getMessage(value.getDisable()) : lang.getMessage(value.getEnable())));
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
