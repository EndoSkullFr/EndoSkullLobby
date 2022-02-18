package fr.bebedlastreat.endoskullnpc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cVous devez être un joueur pour exécuter cette commande");
            return false;
        }
        Player player = (Player) sender;
        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage("§c§lEndoSkull §8» §fVous avez désactivé le fly");
        } else {
            player.setAllowFlight(true);
            player.setFlying(true);
            player.sendMessage("§c§lEndoSkull §8» §fVous avez activé le fly");
        }
        return false;
    }
}
