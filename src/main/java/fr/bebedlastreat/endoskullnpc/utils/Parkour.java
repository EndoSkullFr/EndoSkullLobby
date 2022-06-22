package fr.bebedlastreat.endoskullnpc.utils;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.database.ParkourSQL;
import fr.endoskull.api.spigot.utils.Languages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class Parkour {

    private String name;
    private Location start;
    private Location spawn;
    private List<Location> checkPoints;
    private Location end;
    private Location leaderboard;
    private final HashMap<Player, Hologram> startHolos = new HashMap<>();
    private final HashMap<Player, Hologram> endHolos = new HashMap<>();
    private final HashMap<Player, List<Hologram>> checkpointsHolos = new HashMap<>();

    public Parkour(String name, Location start, Location spawn, List<Location> checkPoints, Location end, Location leaderboard) {
        this.name = name;
        this.start = start;
        this.spawn = spawn;
        this.checkPoints = checkPoints;
        this.end = end;
        this.leaderboard = leaderboard;
        hologram = new fr.endoskull.api.spigot.utils.Hologram(leaderboard, "§e§lEndoSkull §8§l» §f§lParkour §a§l" + name, "§c");
        hologram.spawn();
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

    private fr.endoskull.api.spigot.utils.Hologram hologram;

    public void initHologram(Player player) {

        /*Hologram holoStart = new Hologram(start.clone().add(0.5, 0.5 + 1.975, 0.5), "§a" + name, "§aSTART");
        holoStart.spawn();
        int i = 1;
        for (Location checkPoint : checkPoints) {
            Hologram holoCheckpoint = new Hologram(checkPoint.clone().add(0.5, 0.5 + 1.975, 0.5), "§e" + name, "§eCHECKPOINT #" + i);
            holoCheckpoint.spawn();
            i++;
        }
        Hologram holoEnd = new Hologram(end.clone().add(0.5, 0.5 + 1.975, 0.5), "§a" + name, "§aEND");
        holoEnd.spawn();*/
        Languages lang = Languages.getLang(player);
        Hologram startHolo = HologramsAPI.createHologram(Main.getInstance(), start.clone().add(0.5, 1, 0.5));
        startHolo.appendTextLine("§a" + name);
        startHolo.appendTextLine("§a" + lang.getMessage(LobbyMessage.START));
        startHolo.getVisibilityManager().setVisibleByDefault(false);
        startHolo.getVisibilityManager().showTo(player);
        startHolos.put(player, startHolo);

        checkpointsHolos.put(player, new ArrayList<>());
        int i = 1;
        for (Location checkPoint : checkPoints) {
            Hologram checkPointHolo = HologramsAPI.createHologram(Main.getInstance(), checkPoint.clone().add(0.5, 1, 0.5));
            checkPointHolo.appendTextLine("§e" + name);
            checkPointHolo.appendTextLine("§e" + lang.getMessage(LobbyMessage.CHECKPOINT).replace("{id}", String.valueOf(i)));
            checkPointHolo.getVisibilityManager().setVisibleByDefault(false);
            checkPointHolo.getVisibilityManager().showTo(player);
            checkpointsHolos.get(player).add(checkPointHolo);
            i++;
        }

        Hologram endHolo = HologramsAPI.createHologram(Main.getInstance(), end.clone().add(0.5, 1, 0.5));
        endHolo.appendTextLine("§a" + name);
        endHolo.appendTextLine("§a" + lang.getMessage(LobbyMessage.END));
        endHolo.getVisibilityManager().setVisibleByDefault(false);
        endHolo.getVisibilityManager().showTo(player);
        endHolos.put(player, endHolo);
    }

    public void clearHolo(Player player) {
        if (startHolos.containsKey(player)) {
            startHolos.get(player).delete();
            startHolos.remove(player);
        }
        if (checkpointsHolos.containsKey(player)) {
            for (Hologram holo : checkpointsHolos.get(player)) {
                holo.delete();
            }
            checkpointsHolos.remove(player);
        }
        if (endHolos.containsKey(player)) {
            endHolos.get(player).delete();
            endHolos.remove(player);
        }
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
        i = 1;
        for (int j = 2; j < lines.length; j++) {
            if (lines[j] == null) lines[j] = "§f#" + (i) + " §7■ §a" + "..." + " §7■ §e" + TimeUtils.getTime(0);
            i++;
        }
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            hologram.setLines(lines);
            hologram.update();
        });
    }
}
