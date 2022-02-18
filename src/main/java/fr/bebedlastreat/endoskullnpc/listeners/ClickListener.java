package fr.bebedlastreat.endoskullnpc.listeners;

import fr.bebedlastreat.endoskullnpc.inventories.GameMenuGUI;
import fr.bebedlastreat.endoskullnpc.utils.PlayerManager;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class ClickListener implements Listener {

    private HashMap<UUID, Long> pearlCooldown = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction() == Action.PHYSICAL) return;
        ItemStack current = e.getItem();
        if (current == null) return;
        if (getName(current).equalsIgnoreCase(getName(PlayerManager.getBoutique()))) {
            player.performCommand("boutique");
            e.setCancelled(true);
        }
        if (getName(current).equalsIgnoreCase(getName(PlayerManager.getProfil(player)))) {
            player.performCommand("profile");
            e.setCancelled(true);
        }
        if (getName(current).equalsIgnoreCase(getName(PlayerManager.getMenu()))) {
            new GameMenuGUI(player).open(player);
            e.setCancelled(true);
        }
        if (getName(current).equalsIgnoreCase(getName(PlayerManager.getCosmetics()))) {
            player.performCommand("cosmetics");
            e.setCancelled(true);
        }
        if (getName(current).equalsIgnoreCase(getName(PlayerManager.getPearl(player)))) {
            e.setCancelled(true);
            player.updateInventory();
            if (pearlCooldown.containsKey(player.getUniqueId()) && pearlCooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10f, 0.3f);
                return;
            }
            if (player.getVehicle() != null && player.getVehicle().getType() == EntityType.ENDER_PEARL) player.getVehicle().remove();
            EnderPearl pearl = player.launchProjectile(EnderPearl.class);
            pearlCooldown.put(player.getUniqueId(), System.currentTimeMillis() + 1000);
            pearl.setPassenger(player);
            player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 50, 50);
        }
    }

    public String getName(ItemStack it) {
        if (it.hasItemMeta() && it.getItemMeta().hasDisplayName()) return it.getItemMeta().getDisplayName();
        return it.getType().toString();
    }
}
