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

import java.util.List;

public class FriendsGUI extends CustomGui {
    public FriendsGUI(Player player, int page) {
        super(3, "§c§lEndoSkull §8» §e§lAmis");
        player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            for (int i = 45; i < 53; i++) {
                setItem(i, CustomItemStack.getPane(7).setName("§r"));
            }
            setItem(49, CustomItemStack.getBackGuiItem(), player1 -> {
                new ProfileGUI(player1).open(player1);
            });
            PAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(player.getUniqueId());
            List<PAFPlayer> friends = pafPlayer.getFriends();
            if (friends.size() > 45) {
                setItem(53, new CustomItemStack(Material.ARROW).setName("§ePage Suivante"));
            }
            for (int i = 0; i < 45; i++) {
                if (friends.size() <= page*45 + i) break;
                PAFPlayer friend = friends.get(page*45 + i);
                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                skullMeta.setOwner(player.getName());
                skull.setItemMeta(skullMeta);
            }
        });
    }
}
