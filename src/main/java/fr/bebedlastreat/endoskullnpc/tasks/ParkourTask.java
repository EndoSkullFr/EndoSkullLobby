package fr.bebedlastreat.endoskullnpc.tasks;

import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.utils.LobbyMessage;
import fr.bebedlastreat.endoskullnpc.utils.Parkour;
import fr.bebedlastreat.endoskullnpc.utils.TimeUtils;
import fr.endoskull.api.spigot.utils.Languages;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ParkourTask extends BukkitRunnable {
    private Main main;
    private int i = 0;

    public ParkourTask(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        if (i > 100) {
            Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
                for (Parkour parkour : main.getParkours()) {
                    parkour.updateHolograms();
                }
            });
            i = 0;
        }
        main.getJumping().forEach((player, progress) -> {
            sendActionBar(player, Languages.getLang(player).getMessage(LobbyMessage.PARKOUR_BAR).replace("{parkour}", progress.getParkour().getName())
                    .replace("{checkpoint}", String.valueOf(progress.getStage()))
                    .replace("{time}", TimeUtils.getTime(System.currentTimeMillis() - progress.getStart())));
        });
        i++;
    }

    public void sendActionBar(Player player, String message){
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte)2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
