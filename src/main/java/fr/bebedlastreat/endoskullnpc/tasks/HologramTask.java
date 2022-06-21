package fr.bebedlastreat.endoskullnpc.tasks;

import com.gmail.filoghost.holographicdisplays.api.line.HologramLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;
import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.utils.HologramManager;
import fr.bebedlastreat.endoskullnpc.utils.LobbyMessage;
import fr.endoskull.api.spigot.utils.Languages;
import org.bukkit.scheduler.BukkitRunnable;

public class HologramTask extends BukkitRunnable {
    @Override
    public void run() {
        /*HologramManager.getBedwars().setLines("§eConnecté(s): §6" + ServiceInfoSnapshotUtil.getTaskOnlineCount("BedwarsSolo"), "§a§lBEDWARS GOULAG");
        HologramManager.getBedwars().update();
        HologramManager.getPvpKit().setLines("§eConnecté(s): §6" + ServiceInfoSnapshotUtil.getTaskOnlineCount("PvpKit"), "§7✪ §6§lPvpKit §7✪");
        HologramManager.getPvpKit().update();*/
        HologramManager.getBedwars().forEach((player, hologram) -> {
            HologramLine line = hologram.getLine(0);
            TextLine textLine = (TextLine) line;
            textLine.setText(Languages.getLang(player).getMessage(LobbyMessage.ONLINE).replace("{amount}", String.valueOf(ServiceInfoSnapshotUtil.getTaskOnlineCount("Bedwars"))));
        });
        HologramManager.getPvpkit().forEach((player, hologram) -> {
            HologramLine line = hologram.getLine(0);
            TextLine textLine = (TextLine) line;
            textLine.setText(Languages.getLang(player).getMessage(LobbyMessage.ONLINE).replace("{amount}", String.valueOf(ServiceInfoSnapshotUtil.getTaskOnlineCount("PvpKit"))));
        });
    }
}
