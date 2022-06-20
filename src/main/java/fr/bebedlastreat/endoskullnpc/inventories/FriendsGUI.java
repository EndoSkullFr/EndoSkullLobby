package fr.bebedlastreat.endoskullnpc.inventories;

import fr.bebedlastreat.endoskullnpc.utils.LobbyMessage;
import fr.endoskull.api.commons.EndoSkullAPI;
import fr.endoskull.api.commons.lang.MessageUtils;
import fr.endoskull.api.commons.paf.FriendUtils;
import fr.endoskull.api.spigot.inventories.RequestsGui;
import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import fr.endoskull.api.spigot.utils.Languages;
import fr.endoskull.api.spigot.utils.SpigotPlayerInfos;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FriendsGUI extends CustomGui {
    private int[] glassSlot = {0,1,9,7,8,17,36,45,46,44,52,53};
    private int[] slots = {10,11,12,13,14,15,16, 19,20,21,22,23,24,25, 28,29,30,31,32,33,34, 37,38,39,40,41,42,43};
    public FriendsGUI(Player p, int page) {
        super(6, Languages.getLang(p).getMessage(LobbyMessage.GUI_FRIENDS), p);
        Languages lang = Languages.getLang(p);
        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1f, 1f);

        List<UUID> friends = FriendUtils.getOrderedFriends(p.getUniqueId());
        for (int i = 0; i < slots.length * page; i++) {
            friends.remove(0);
        }

        for (int i : glassSlot) {
            setItem(i, CustomItemStack.getPane(3).setName("§r"));
        }
        int i = 0;
        setItem(slots[i], new CustomItemStack(Material.BARRIER).setName(lang.getMessage(LobbyMessage.NO_FRIEND)));
        for (UUID uuid : friends) {
            String name = SpigotPlayerInfos.getNameFromUuid(uuid);
            boolean online = FriendUtils.isOnline(uuid);
            setItem(slots[i], CustomItemStack.getPlayerSkull(name).setName(EndoSkullAPI.getPrefix(uuid) + name + " " + (online ? "§a" + lang.getMessage(MessageUtils.Paf.ONLINE) : "§c" + lang.getMessage(MessageUtils.Paf.OFFLINE))));
            i++;
            if (i >= slots.length) break;
        }
        if (page > 0) {
            setItem(48, new CustomItemStack(Material.ARROW).setName(lang.getMessage(MessageUtils.Global.PREVIOUS_PAGE)), player -> {
                new RequestsGui(player, page - 1);
            });
        }
        if (friends.size() > slots.length) {
            setItem(50, new CustomItemStack(Material.ARROW).setName(lang.getMessage(MessageUtils.Global.NEXT_PAGE)), player -> {
                new RequestsGui(player, page + 1);
            });
        }
    }
}
