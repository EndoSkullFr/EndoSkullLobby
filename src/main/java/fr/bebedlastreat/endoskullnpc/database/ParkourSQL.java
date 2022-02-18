package fr.bebedlastreat.endoskullnpc.database;

import fr.bebedlastreat.endoskullnpc.Main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ParkourSQL {
    private static String TABLE = "parkours";

    public static boolean hasTime(UUID uuid, String parkour) {
        return (boolean) Main.getInstance().getMysql().query("SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "' AND parkour_name='" + parkour + "'", rs -> {
            try {
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    public static void insertTime(UUID uuid, String name, String parkour, long time) {
        Main.getInstance().getMysql().update("INSERT INTO " + TABLE + " (uuid, name, parkour_name, time) VALUES ('" + uuid + "', '" + name + "', '" + parkour + "', '" + time + "')");
    }

    public static void updateTime(UUID uuid, String name, String parkour, long time) {
        Main.getInstance().getMysql().update("UPDATE " + TABLE + " SET name='" + name + "', time=" + time + " WHERE uuid='" + uuid + "' AND parkour_name='" + parkour + "'");
    }

    public static long getTime(UUID uuid, String parkour) {
        return (long) Main.getInstance().getMysql().query("SELECT time FROM " + TABLE + " WHERE uuid='" + uuid + "' AND parkour_name='" + parkour + "'", rs -> {
            try {
                if (rs.next()) {
                    return rs.getLong("time");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Long.MAX_VALUE;
        });
    }

    public static String getName(UUID uuid, String parkour) {
        return (String) Main.getInstance().getMysql().query("SELECT name FROM " + TABLE + " WHERE uuid='" + uuid + "' AND parkour_name='" + parkour + "'", rs -> {
            try {
                if (rs.next()) {
                    return rs.getString("name");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Long.MAX_VALUE;
        });
    }

    public static List<UUID> getLeaderboard(String parkour) {
        List<UUID> result = new ArrayList<>();
        Main.getInstance().getMysql().query("SELECT uuid FROM " + TABLE + " WHERE parkour_name='" + parkour + "' ORDER BY time", rs -> {
            try {
                while (rs.next()) {
                    result.add(UUID.fromString(rs.getString("uuid")));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return;
        });
        return result;
    }
}
