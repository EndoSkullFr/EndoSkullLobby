package fr.bebedlastreat.endoskullnpc.listeners;

import com.github.juliarn.npc.event.PlayerNPCInteractEvent;
import fr.bebedlastreat.endoskullnpc.utils.NPCServer;
import fr.bebedlastreat.endoskullnpc.utils.NPCSpawnManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NpcListener implements Listener {

    @EventHandler
    public void onClickNpc(PlayerNPCInteractEvent e) {
        if (e.getUseAction() == PlayerNPCInteractEvent.EntityUseAction.INTERACT_AT) return;
        int entityId = e.getNPC().getEntityId();
        NPCServer npc = NPCSpawnManager.getNPC().get(Integer.valueOf(entityId));
        Player player = e.getPlayer();
        if (npc == null || npc.getTargetGroup() == null) return;
        if (e.getUseAction() == PlayerNPCInteractEvent.EntityUseAction.ATTACK) {
            player.performCommand("join " + npc.getTargetGroup());
        } else {
            player.performCommand("join " + npc.getTargetGroup() + " gui");
        }
    }
}
