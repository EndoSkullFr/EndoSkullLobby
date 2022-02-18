package fr.bebedlastreat.endoskullnpc.listeners;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

public class PearlRiderListener implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDismount(EntityDismountEvent e) {
        if (e.getDismounted() instanceof EnderPearl && e.getEntity() instanceof Player) e.getDismounted().remove();
    }
}
