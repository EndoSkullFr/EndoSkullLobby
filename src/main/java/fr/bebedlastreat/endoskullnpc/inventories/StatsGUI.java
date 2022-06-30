package fr.bebedlastreat.endoskullnpc.inventories;

import fr.bebedlastreat.endoskullnpc.utils.LobbyMessage;
import fr.endoskull.api.commons.account.Account;
import fr.endoskull.api.commons.account.AccountProvider;
import fr.endoskull.api.commons.lang.MessageUtils;
import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import fr.endoskull.api.spigot.utils.Languages;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StatsGUI extends CustomGui {
    public StatsGUI(Player player) {
        super(3, Languages.getLang(player).getMessage(LobbyMessage.GUI_STATS), player);
        Languages lang = Languages.getLang(player);
        player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
        Account account = AccountProvider.getAccount(player.getUniqueId());

        setItem(10, new CustomItemStack(Material.NAME_TAG).setName(lang.getMessage(LobbyMessage.IDENTITY)).setLore(lang.getMessage(LobbyMessage.IDENTITY_DESC).replace("{name}", player.getName()).replace("{uuid}", String.valueOf(player.getUniqueId()))));

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy kk:mm:ss", Locale.FRANCE);
        long firstJoin = account.getFirstJoin();
        long lastLogin = Long.parseLong(account.getProperty("lastLogin", "0"));
        long lastLogout = Long.parseLong(account.getProperty("lastLogout", "0"));
        setItem(12, new CustomItemStack(Material.WATCH).setName(lang.getMessage(LobbyMessage.DATE)).setLore(lang.getMessage(LobbyMessage.DATE_DESC).replace("{firstLogin}", sdf.format(new Date(firstJoin))).replace("{lastLogin}", sdf.format(new Date(lastLogin))).replace("{lastLogout}", sdf.format(new Date(lastLogout)))));

        int pvpkitKill = account.getStatistic("pvpkit/kill");
        int pvpkitDeath = account.getStatistic("pvpkit/death");
        setItem(14, CustomItemStack.getSkull("http://textures.minecraft.net/texture/941466199b72cbc8889bb8a52e0f4ba13bcdd5fab97eeb948214187b62f0640d")
                .setName("§aPvpKit").setLore(lang.getMessage(LobbyMessage.STAT_PVPKIT).replace("{kills}", String.valueOf(pvpkitKill)).replace("{deaths}", String.valueOf(pvpkitDeath))));

        int bwKill = account.getStatistic("bedwars/kill");
        int bwFinalKill = account.getStatistic("bedwars/finalkill");
        int bwDeath = account.getStatistic("bedwars/death");
        int bwBedBroken = account.getStatistic("bedwars/bedbroken");
        int bwGamePlayed = account.getStatistic("bedwars/gameplayed");
        int bwWins = account.getStatistic("bedwars/win");
        int bwGoulagWins = account.getStatistic("bedwars/goulagwin");
        setItem(16, CustomItemStack.getSkull("http://textures.minecraft.net/texture/fe6978cec1eaf5b90ae1531c30cd9cdc778d18ae25ed5296d224d9afdc089d2a").setName("§aBedwars")
                .setLore(lang.getMessage(LobbyMessage.STAT_BEDWARS).replace("{kills}", String.valueOf(bwKill)).replace("{finalKills}", String.valueOf(bwFinalKill)).replace("{deaths}", String.valueOf(bwDeath))
                        .replace("{bedBroken}", String.valueOf(bwBedBroken)).replace("{gamePlayed}", String.valueOf(bwGamePlayed)).replace("{wins}", String.valueOf(bwWins)).replace("{goulagWins}", String.valueOf(bwGoulagWins))));
    }
}
