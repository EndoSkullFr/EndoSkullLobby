package fr.bebedlastreat.endoskullnpc.inventories;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.utils.FriendSettings;
import fr.bebedlastreat.endoskullnpc.utils.PlayerManager;
import fr.endoskull.api.commons.Account;
import fr.endoskull.api.commons.AccountProvider;
import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Set;

public class SettingsGUI extends CustomGui {
    public SettingsGUI(Player player) {
        super(3, "§c§lEndoSkull §8» §e§lParamètres");
        player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            Account account = AccountProvider.getAccount(player.getUniqueId());

            setItem(10, new CustomItemStack(Material.ENDER_PEARL).setName("§eCouleur du Pearl Rider")
                    .setLore("\n§7Couleur actuelle: §f" + account.getProperty("pearl_rider_color", "Green")), player1 -> {
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
                new SettingsGUI(player1).open(player1);
                player1.getInventory().setItem(8, PlayerManager.getPearl(player1));
            });

            setItem(26, CustomItemStack.getBackGuiItem(), player1 -> {
                new ProfileGUI(player1).open(player1);
            });
            PAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(player.getUniqueId());
            int i = 11;
            for (FriendSettings value : FriendSettings.values()) {
                int state = pafPlayer.getSettingsWorth(value.getSettingId());
                setItem(i, new CustomItemStack(value.getItem()).setName("§e" + value.getName())
                        .setLore("\n§7État: " + (state == 0 ? "§a" : "§c") +
                                (state == 0 ? value.getSetting0() : value.getSetting1())), player1 -> {
                    pafPlayer.setSetting(value.getSettingId(), state == 0 ? 1 : 0);
                    new SettingsGUI(player1).open(player1);
                });
                i++;
            }
        });
    }
}
