package fr.bebedlastreat.endoskullnpc.listeners;

import fr.bebedlastreat.endoskullnpc.utils.PlayerManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location loc = e.getTo();
        if (loc.getY() < 46 || loc.getY() > 256 || loc.getX() < -440 || loc .getX() > -14 || loc.getZ() < -447 || loc.getZ() > -21) {
            PlayerManager.teleportToSpawn(player);
            return;
        }
    }
}
