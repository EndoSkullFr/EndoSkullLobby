package fr.bebedlastreat.endoskullnpc.commands;

import fr.bebedlastreat.endoskullnpc.utils.PlayerManager;
import fr.endoskull.api.commons.lang.MessageUtils;
import fr.endoskull.api.spigot.utils.Languages;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Languages.getLang(sender).getMessage(MessageUtils.Global.CONSOLE));
            return false;
        }
        Player player = (Player) sender;
        PlayerManager.teleportToSpawn(player);
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
        return false;
    }
}
