package fr.bebedlastreat.endoskullnpc.utils;

import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.database.ParkourSQL;
import fr.endoskull.api.spigot.utils.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.*;

public class Parkour {

    private String name;
    private Location start;
    private Location spawn;
    private List<Location> checkPoints;
    private Location end;
    private Location leaderboard;

    public Parkour(String name, Location start, Location spawn, List<Location> checkPoints, Location end, Location leaderboard) {
        this.name = name;
        this.start = start;
        this.spawn = spawn;
        this.checkPoints = checkPoints;
        this.end = end;
        this.leaderboard = leaderboard;
        initHologram();
        start.getBlock().setType(Material.STONE_PLATE);
        end.getBlock().setType(Material.IRON_PLATE);
        for (Location checkPoint : checkPoints) {
            checkPoint.getBlock().setType(Material.GOLD_PLATE);
        }
    }

    public String getName() {
        return name;
    }

    public Location getStart() {
        return start;
    }

    public List<Location> getCheckPoints() {
        return checkPoints;
    }

    public Location getEnd() {
        return end;
    }

    public Location getLeaderboard() {
        return leaderboard;
    }

    public Location getSpawn() {
        return spawn;
    }

    private Hologram hologram;

    public void initHologram() {
        hologram = new Hologram(leaderboard, "§e§lEndoSkull §8§l» §f§lParkour §a§l" + name, "§c");
        hologram.spawn();

        Hologram holoStart = new Hologram(start.clone().add(0.5, 0.5 + 1.975, 0.5), "§a" + name, "§aDEBUT DU PARKOUR");
        holoStart.spawn();
        int i = 1;
        for (Location checkPoint : checkPoints) {
            Hologram holoCheckpoint = new Hologram(checkPoint.clone().add(0.5, 0.5 + 1.975, 0.5), "§e" + name, "§eCHECKPOINT #" + i);
            holoCheckpoint.spawn();
            i++;
        }
        Hologram holoEnd = new Hologram(end.clone().add(0.5, 0.5 + 1.975, 0.5), "§a" + name, "§aFIN DU PARKOUR");
        holoEnd.spawn();
    }

    public void updateHolograms() {
        List<UUID> leaderboard = ParkourSQL.getLeaderboard(name);
        int i = 0;
        String[] lines = new String[12];
        lines[0] = "§e§lEndoSkull §8§l» §f§lParkour §a§l" + name;
        lines[1] = "§c";
        for (UUID uuid : leaderboard) {
            if (i >= 10) break;
            String name = ParkourSQL.getName(uuid, this.name);
            lines[i+2] = "§f#" + (i+1) + " §7■ §a" + name + " §7■ §e" + TimeUtils.getTime(ParkourSQL.getTime(uuid, this.name));
            i++;
        }
        for (int j = 0; j < lines.length; j++) {
            if (lines[j] == null) lines[j] = "§f#" + (j+1);
        }
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            hologram.setLines(lines);
            hologram.update();
        });
    }
}
