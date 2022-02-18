package fr.bebedlastreat.endoskullnpc.utils;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class NPCSpawnManager {
    private static final HashMap<Integer, NPCServer> npc = new HashMap<>();

    public static HashMap<Integer, NPCServer> getNPC() {
        return npc;
    }

    public NPCSpawnManager() {
        World world = Bukkit.getWorld("world");
        (new NPCServer(
                UUID.fromString("0ce3e02e-4e82-47b1-92bb-3cb7e188602b"), new Location(world, -238.5, 63, -261.5, 135, 0), "BedWarsSolo", null, true, true))
                .build();
        (new NPCServer(
                UUID.fromString("d2b7b4e0-d3ed-4230-ac83-1aa32dc97676"), new Location(world, -246.5, 63, -253.5, 135, 0), "PvpKit", Material.WOOD_SWORD, true, true))
                .build();
    }
}
