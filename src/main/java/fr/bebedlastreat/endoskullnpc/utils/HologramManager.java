package fr.bebedlastreat.endoskullnpc.utils;

import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;
import fr.endoskull.api.spigot.utils.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class HologramManager {
    private static Hologram bedwars;
    private static Hologram pvpKit;
    private static Hologram lobby;

    public static void initHolograms() {
        bedwars = new Hologram(new Location(Bukkit.getWorld("world"), -238.5, 64.585, -261.5), "§eConnecté(s): §6" + ServiceInfoSnapshotUtil.getTaskOnlineCount("BedwarsSolo"), "§a§lBEDWARS GOULAG");
        bedwars.spawn();
        pvpKit = new Hologram(new Location(Bukkit.getWorld("world"), -246.5, 64.585, -253.5), "§eConnecté(s): §6" + ServiceInfoSnapshotUtil.getTaskOnlineCount("PvpKit"), "§7✪ §6§lPvpKit §7✪");
        pvpKit.spawn();

        lobby = new Hologram(new Location(Bukkit.getWorld("world"), -268.5, 63.6, -281.5), "§d§lLOBBY");
        lobby.spawn();
        spawnBoxHolograms();
    }

    public static Hologram getBedwars() {
        return bedwars;
    }

    public static Hologram getPvpKit() {
        return pvpKit;
    }

    private static void spawnBoxHolograms() {
        Hologram ultime = new Hologram(new Location(Bukkit.getWorld("world"), -255.5, 63.8, -270.5), "§4§m----------", "§4§lBox Ultime", "§4§m----------");
        ultime.spawn();
        Hologram vote = new Hologram(new Location(Bukkit.getWorld("world"), -263.5, 63.8, -262.5), "§d§m----------", "§d§lBox Vote", "§d§m----------");
        vote.spawn();
    }
}
