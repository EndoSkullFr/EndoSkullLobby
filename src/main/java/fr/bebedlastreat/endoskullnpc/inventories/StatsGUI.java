package fr.bebedlastreat.endoskullnpc.inventories;

import fr.endoskull.api.commons.account.Account;
import fr.endoskull.api.commons.account.AccountProvider;
import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StatsGUI extends CustomGui {
    public StatsGUI(Player player) {
        super(3, "§c§lEndoSkull §8» §a§lStatistiques");
        Account account = AccountProvider.getAccount(player.getUniqueId());

        setItem(10, new CustomItemStack(Material.NAME_TAG).setName("§aIdentité").setLore("\n§7Pseudo: §e" + player.getName() + "\n§7UUID: §e" + player.getUniqueId()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy kk:mm:ss", Locale.FRANCE);
        long firstJoin = account.getFirstJoin();
        long lastLogin = Long.parseLong(account.getProperty("lastLogin", "0"));
        long lastLogout = Long.parseLong(account.getProperty("lastLogout", "0"));
        setItem(12, new CustomItemStack(Material.WATCH).setName("§aDates").setLore("\n§7Première connection: §e" + sdf.format(new Date(firstJoin)) + "\n§7Dernière connection: §e" + sdf.format(new Date(lastLogin)) + "\n§7Dernière déconnection: §e" + sdf.format(new Date(lastLogout))));

        int pvpkitKill = account.getStatistic("pvpkit/kill");
        int pvpkitDeath = account.getStatistic("pvpkit/death");
        setItem(14, CustomItemStack.getSkull("http://textures.minecraft.net/texture/941466199b72cbc8889bb8a52e0f4ba13bcdd5fab97eeb948214187b62f0640d")
                .setName("§aPvpKit").setLore("\n§7Kill(s): §e" + pvpkitKill + "\n§7Mort(s): §e" + pvpkitDeath));

        setItem(16, CustomItemStack.getSkull("http://textures.minecraft.net/texture/fe6978cec1eaf5b90ae1531c30cd9cdc778d18ae25ed5296d224d9afdc089d2a").setName("§aBedwars"));
    }
}
