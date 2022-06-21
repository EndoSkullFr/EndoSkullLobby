package fr.bebedlastreat.endoskullnpc.utils;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import fr.bebedlastreat.endoskullnpc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class HologramManager {
    private static HashMap<Player, Hologram> bedwars = new HashMap<>();
    private static HashMap<Player, Hologram> pvpkit = new HashMap<>();
    private static Hologram lobby;

    public static void initHolograms(Player player) {
        Hologram bedwarsHolo = HologramsAPI.createHologram(Main.getInstance(), new Location(Bukkit.getWorld("Lobby"), -238.5, 63.7 + 1.96, -261.5));
        bedwarsHolo.appendTextLine("");
        bedwarsHolo.appendTextLine("§a§lBEDWARS GOULAG");
        bedwarsHolo.getVisibilityManager().setVisibleByDefault(false);
        bedwarsHolo.getVisibilityManager().showTo(player);
        bedwars.put(player, bedwarsHolo);
        Hologram pvpkitHolo = HologramsAPI.createHologram(Main.getInstance(), new Location(Bukkit.getWorld("Lobby"), -246.5, 63.7 + 1.96, -253.5));
        pvpkitHolo.appendTextLine("");
        pvpkitHolo.appendTextLine("§7✪ §6§lPvpKit §7✪");
        pvpkitHolo.getVisibilityManager().setVisibleByDefault(false);
        pvpkitHolo.getVisibilityManager().showTo(player);
        pvpkit.put(player, pvpkitHolo);

        /*bedwars = new Hologram(new Location(Bukkit.getWorld("Lobby"), -238.5, 64.585 + 1.96, -261.5), "§eConnecté(s): §6" + ServiceInfoSnapshotUtil.getTaskOnlineCount("BedwarsSolo"), "§a§lBEDWARS GOULAG");
        bedwars.spawn();
        pvpKit = new Hologram(new Location(Bukkit.getWorld("Lobby"), -246.5, 64.585 + 1.96, -253.5), "§eConnecté(s): §6" + ServiceInfoSnapshotUtil.getTaskOnlineCount("PvpKit"), "§7✪ §6§lPvpKit §7✪");
        pvpKit.spawn();

        lobby = new Hologram(new Location(Bukkit.getWorld("Lobby"), -268.5, 63.585 + 1.96, -281.5), "§d§lLOBBY");
        lobby.spawn();*/
    }

    public static void clear(Player player) {
        if (bedwars.containsKey(player)) {
            bedwars.get(player).delete();
            bedwars.remove(player);
        }
        if (pvpkit.containsKey(player)) {
            pvpkit.get(player).delete();
            pvpkit.remove(player);
        }
    }

    private static void spawnBoxHolograms() {
        /*Hologram ultime = new Hologram(new Location(Bukkit.getWorld("Lobby"), -255.5, 63.8, -270.5), "§4§m----------", "§4§lBox Ultime", "§4§m----------");
        ultime.spawn();
        Hologram vote = new Hologram(new Location(Bukkit.getWorld("Lobby"), -263.5, 63.8, -262.5), "§d§m----------", "§d§lBox Vote", "§d§m----------");
        vote.spawn();*/
    }

    public static HashMap<Player, Hologram> getBedwars() {
        return bedwars;
    }

    public static HashMap<Player, Hologram> getPvpkit() {
        return pvpkit;
    }
}
