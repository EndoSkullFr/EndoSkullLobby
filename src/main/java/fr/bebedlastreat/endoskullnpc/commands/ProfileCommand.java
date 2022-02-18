package fr.bebedlastreat.endoskullnpc.commands;

import fr.bebedlastreat.endoskullnpc.inventories.ProfileGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProfileCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cVous devez être un joueur pour exécuter cette commande");
            return false;
        }
        Player player = (Player) sender;
        new ProfileGUI(player).open(player.getPlayer());
        return false;
    }
}
