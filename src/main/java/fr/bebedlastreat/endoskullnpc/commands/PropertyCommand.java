package fr.bebedlastreat.endoskullnpc.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.bebedlastreat.endoskullnpc.utils.HologramManager;
import fr.endoskull.api.spigot.utils.Hologram;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PropertyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        GameProfile profile = ((CraftPlayer) player).getHandle().getProfile();
        Property property = profile.getProperties().get("textures").stream().findFirst().orElse(null);
        if(property != null) {
            System.out.println(property.getName());
            System.out.println(property.getSignature());
            System.out.println(property.getValue());
        }
        return false;
    }
}
