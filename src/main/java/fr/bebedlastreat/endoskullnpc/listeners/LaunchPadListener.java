package fr.bebedlastreat.endoskullnpc.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class LaunchPadListener implements Listener {

    private HashMap<UUID, Long> cooldown = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location location = player.getLocation();

        if (location.getBlock().getType() == Material.GOLD_PLATE && location.subtract(0, 1, 0).getBlock().getType() == Material.DOUBLE_STEP) {
            if (cooldown.containsKey(player.getUniqueId()) && cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) return;
            player.setVelocity(location.getDirection().multiply(4).setY(1));
            player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1f, 1f);
            cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 1000);
        }
    }
}
