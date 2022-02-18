package fr.bebedlastreat.endoskullnpc.tasks;

import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;
import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.utils.HologramManager;
import org.bukkit.scheduler.BukkitRunnable;

public class HologramTask extends BukkitRunnable {
    @Override
    public void run() {
        HologramManager.getBedwars().setLines("§eConnecté(s): §6" + ServiceInfoSnapshotUtil.getTaskOnlineCount("BedwarsSolo"), "§a§lBEDWARS GOULAG");
        HologramManager.getBedwars().update();
        HologramManager.getPvpKit().setLines("§eConnecté(s): §6" + ServiceInfoSnapshotUtil.getTaskOnlineCount("PvpKit"), "§7✪ §6§lPvpKit §7✪");
        HologramManager.getPvpKit().update();
    }
}
