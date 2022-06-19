package fr.bebedlastreat.endoskullnpc;

import com.github.juliarn.npc.NPCPool;
import fr.bebedlastreat.endoskullnpc.commands.FlyCommand;
import fr.bebedlastreat.endoskullnpc.commands.ProfileCommand;
import fr.bebedlastreat.endoskullnpc.commands.PropertyCommand;
import fr.bebedlastreat.endoskullnpc.commands.SpawnCommand;
import fr.bebedlastreat.endoskullnpc.database.MySQL;
import fr.bebedlastreat.endoskullnpc.listeners.*;
import fr.bebedlastreat.endoskullnpc.scoreboard.BoardRunnable;
import fr.bebedlastreat.endoskullnpc.scoreboard.FastBoard;
import fr.bebedlastreat.endoskullnpc.tasks.HologramTask;
import fr.bebedlastreat.endoskullnpc.tasks.ParkourTask;
import fr.bebedlastreat.endoskullnpc.utils.HologramManager;
import fr.bebedlastreat.endoskullnpc.utils.Parkour;
import fr.bebedlastreat.endoskullnpc.utils.ParkourProgress;
import fr.bebedlastreat.endoskullnpc.utils.NPCSpawnManager;
import fr.endoskull.api.spigot.utils.Languages;
import org.apache.commons.dbcp2.BasicDataSource;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {
    private static Main instance;
    private static NPCPool npcPool;
    private List<FastBoard> boards = new ArrayList<>();
    private List<Parkour> parkours = new ArrayList<>();
    private HashMap<Player, ParkourProgress> jumping = new HashMap<>();

    private BasicDataSource connectionPool;
    private MySQL mysql;

    @Override
    public void onEnable() {
        instance = this;
        initConnection();
        npcPool = NPCPool.builder(this).spawnDistance(60).actionDistance(30).tabListRemoveTicks(20L).build();
        new NPCSpawnManager();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new NpcListener(), this);
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new WeatherListener(), this);
        pm.registerEvents(new ProtectListener(), this);
        pm.registerEvents(new ClickListener(), this);
        pm.registerEvents(new PearlRiderListener(), this);
        pm.registerEvents(new MoveListener(), this);
        pm.registerEvents(new LaunchPadListener(), this);
        pm.registerEvents(new ParkourListener(this), this);

        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("profile").setExecutor(new ProfileCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("profile").setExecutor(new ProfileCommand());
        getCommand("property").setExecutor(new PropertyCommand());

        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new BoardRunnable(this), 60, 20);
        HologramManager.initHolograms();
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new HologramTask(), 60, 20);
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new ParkourTask(this), 60, 2);

        //HologramManager.initHolograms();

        World world = Bukkit.getWorld("Lobby");
        world.setStorm(false);
        world.setThundering(false);
        /**parkours.add(new Parkour("Lobby", new Location(world, -293, 63, -279), new Location(world, -288.5, 62, -278.5, 90, 0),
                Arrays.asList(new Location(world, -351, 75, -283, 90, 0), new Location(world, -354, 84, -309, -135, 0)),
                new Location(world, -296, 95, -360), new Location(world, -293.5, 65, -272.5)));*/
        for (Languages value : Languages.values()) {
            saveResource("languages/" + value.toString().toLowerCase() + ".yml", false);
            YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "languages/" + value.toString().toLowerCase() + ".yml"));
            File langFile = new File(fr.endoskull.api.Main.getInstance().getDataFolder(), "languages/" + value.toString().toLowerCase() + ".yml");
            YamlConfiguration langConfig = YamlConfiguration.loadConfiguration(langFile);
            for (String key : config.getKeys(false)) {
                langConfig.set(key, config.get(key));
            }
            try {
                langConfig.save(langFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void initConnection(){
        connectionPool = new BasicDataSource();
        connectionPool.setDriverClassName("com.mysql.jdbc.Driver");
        connectionPool.setUsername("endoskull"); //w_512203
        connectionPool.setPassword("9zRQ2Cb03DxdPTmG"); //45geFJ445geFJ445geFJ445geFJ4
        connectionPool.setUrl("jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "endoskull" + "?autoReconnect=true");
        connectionPool.setInitialSize(1);
        connectionPool.setMaxTotal(10);
        mysql = new MySQL(connectionPool);
        mysql.createTables();
    }

    public static NPCPool getNpcPool() {
        return npcPool;
    }

    public static Main getInstance() {
        return instance;
    }

    public List<FastBoard> getBoards() {
        return boards;
    }

    public List<Parkour> getParkours() {
        return parkours;
    }

    public HashMap<Player, ParkourProgress> getJumping() {
        return jumping;
    }

    public MySQL getMysql() {
        return mysql;
    }
}
