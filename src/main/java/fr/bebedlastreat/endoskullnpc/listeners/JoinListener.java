package fr.bebedlastreat.endoskullnpc.listeners;

import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.scoreboard.FastBoard;
import fr.bebedlastreat.endoskullnpc.utils.HologramManager;
import fr.bebedlastreat.endoskullnpc.utils.LobbyMessage;
import fr.bebedlastreat.endoskullnpc.utils.Parkour;
import fr.bebedlastreat.endoskullnpc.utils.PlayerManager;
import fr.endoskull.api.commons.account.Account;
import fr.endoskull.api.commons.account.AccountProvider;
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
        e.setJoinMessage(null);
        PlayerManager.resetPlayer(player);
        PlayerManager.spawnFirework();

        Account account = AccountProvider.getAccount(player.getUniqueId());
        if (account.getProperty("bedwars/playagain", "").equalsIgnoreCase("1")) {
            account.setProperty("bedwars/playagain", "0");
            player.performCommand("join Bedwars");
        } else {
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
        }

        FastBoard board = new FastBoard(player);
        board.updateTitle("§a§lLOBBY");
        Main.getInstance().getBoards().add(board);
        HologramManager.initHolograms(player);
        for (Parkour parkour : Main.getInstance().getParkours()) {
            parkour.initHologram(player);
        }
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
        HologramManager.clear(player);
        for (Parkour parkour : Main.getInstance().getParkours()) {
            parkour.clearHolo(player);
        }
    }
}
