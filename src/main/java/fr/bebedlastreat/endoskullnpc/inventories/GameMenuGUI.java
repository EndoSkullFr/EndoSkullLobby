package fr.bebedlastreat.endoskullnpc.inventories;

import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;
import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameMenuGUI extends CustomGui {
    private static int[] grayGlass = {2,3,4,5,6,18,26,38,39,40,41,42};
    private static int[] blueGlass = {0,1,7,8,9,17,27,35,36,37,43,44};

    public GameMenuGUI(Player player) {
        super(5, "§c§lEndoSkull §8» §a§lMenu Principal");
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f);
        for (int glass : grayGlass) {
            setItem(glass, CustomItemStack.getPane(7).setName("§r"));
        }
        for (int glass : blueGlass) {
            setItem(glass, CustomItemStack.getPane(3).setName("§r"));
        }

        setItem(21, CustomItemStack.getSkull("http://textures.minecraft.net/texture/941466199b72cbc8889bb8a52e0f4ba13bcdd5fab97eeb948214187b62f0640d")
                .setName("§6§lPvpKit §8- §dFAMOUS")
                .setLore("\n§e2 §7à §e20§e joueurs\n\n§7◇ §fDans ce mode jeux choisissez un kit pour aller\n§fcombattre dans l'arène face aux autres joueurs\n\n§7Connectés: §e" + ServiceInfoSnapshotUtil.getTaskOnlineCount("PvpKit")), player1 -> {
            player1.performCommand("join PvpKit");
        });

        setItem(23, CustomItemStack.getSkull("http://textures.minecraft.net/texture/fe6978cec1eaf5b90ae1531c30cd9cdc778d18ae25ed5296d224d9afdc089d2a")
                .setName("§a§lBedwars Goulag")
                .setLore("\n§e2 §7à §e8§e joueurs\n\n§7◇ §fDans cette version du célèbre mode de jeux,\n§fvous pouvez combattre après votre élimination\n§fpour tenter de réapparaître\n\n§7Connectés: §e" + ServiceInfoSnapshotUtil.getTaskOnlineCount("BedwarsSolo")), player1 -> {
            player1.performCommand("join BedwarsSolo");
        });
    }
}
