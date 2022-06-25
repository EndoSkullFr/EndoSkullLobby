package fr.bebedlastreat.endoskullnpc.inventories;

import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;
import fr.bebedlastreat.endoskullnpc.utils.LobbyMessage;
import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import fr.endoskull.api.spigot.utils.Languages;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameMenuGUI extends CustomGui {
    private static int[] grayGlass = {2,3,4,5,6,18,26,38,39,40,41,42};
    private static int[] blueGlass = {0,1,7,8,9,17,27,35,36,37,43,44};

    public GameMenuGUI(Player player) {
        super(5, Languages.getLang(player).getMessage(LobbyMessage.GUI_MAIN_MENU), player);
        Languages lang = Languages.getLang(player);
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f);
        for (int glass : grayGlass) {
            setItem(glass, CustomItemStack.getPane(7).setName("§r"));
        }
        for (int glass : blueGlass) {
            setItem(glass, CustomItemStack.getPane(3).setName("§r"));
        }

        setItem(21, CustomItemStack.getSkull("http://textures.minecraft.net/texture/941466199b72cbc8889bb8a52e0f4ba13bcdd5fab97eeb948214187b62f0640d")
                .setName("§6§lPvpKit")
                .setLore(lang.getMessage(LobbyMessage.PVPKIT_DESC).replace("{online}", String.valueOf(ServiceInfoSnapshotUtil.getTaskOnlineCount("PvpKit")))), player1 -> {
            player1.performCommand("join PvpKit");
        });

        setItem(23, CustomItemStack.getSkull("http://textures.minecraft.net/texture/fe6978cec1eaf5b90ae1531c30cd9cdc778d18ae25ed5296d224d9afdc089d2a")
                .setName("§a§lBedwars Goulag")
                .setLore(lang.getMessage(LobbyMessage.BEDWARS_DESC).replace("{online}", String.valueOf(ServiceInfoSnapshotUtil.getTaskOnlineCount("Bedwars")))), player1 -> {
            player1.performCommand("join BedwarsSolo");
        });
    }
}
