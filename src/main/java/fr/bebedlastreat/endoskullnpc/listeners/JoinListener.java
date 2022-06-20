package fr.bebedlastreat.endoskullnpc.listeners;

import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.scoreboard.FastBoard;
import fr.bebedlastreat.endoskullnpc.utils.LobbyMessage;
import fr.bebedlastreat.endoskullnpc.utils.PlayerManager;
import fr.endoskull.api.spigot.utils.Languages;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PlayerManager.resetPlayer(player);
        PlayerManager.spawnFirework();

        if (player.hasPermission("deluxehub.join.message")) {
            LuckPerms luckPerms = LuckPermsProvider.get();
            User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
            //e.setJoinMessage(ChatColor.translateAlternateColorCodes('&',  + " §7vient de rejoindre le lobby"));
            e.setJoinMessage(null);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', Languages.getLang(onlinePlayer).getMessage(LobbyMessage.JOIN_MESSAGE).replace("{player}", user.getCachedData().getMetaData().getPrefix() + player.getName())));
            }
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                if (player == null) return;
                //player.setAllowFlight(true);
                //player.setFlying(true);
            }, 10);
        } else {
            e.setJoinMessage(null);
        }

        FastBoard board = new FastBoard(player);
        board.updateTitle("§a§lLOBBY");
        Main.getInstance().getBoards().add(board);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        e.setQuitMessage(null);
        FastBoard board = Main.getInstance().getBoards().stream().filter(fastBoard -> fastBoard.getPlayer().equals(player)).findFirst().orElse(null);
        if (board != null) {
            board.delete();
            Main.getInstance().getBoards().remove(board);
        }
    }
}
