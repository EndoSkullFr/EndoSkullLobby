package fr.bebedlastreat.endoskullnpc.scoreboard;


import fr.bebedlastreat.endoskullnpc.Main;
import fr.endoskull.api.commons.Account;
import fr.endoskull.api.commons.AccountProvider;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BoardRunnable implements Runnable {
    private Main main;

    public BoardRunnable(Main main) {
        this.main = main;
    }

    @Override
    public void run() {

        for (FastBoard board : main.getBoards()) {
            try {
                Player player = board.getPlayer();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                LuckPerms luckPerms = LuckPermsProvider.get();
                User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
                Account account = AccountProvider.getAccount(player.getUniqueId());
                List<String> lines = Arrays.asList(
                        "§7" + sdf.format(new Date(System.currentTimeMillis())) + " §8" + Bukkit.getServerName(),
                        "§1",
                        "§fGrade: §a" + luckPerms.getGroupManager().getGroup(user.getPrimaryGroup()).getFriendlyName(),
                        "§2",
                        "§fMoney: §a" + account.getStringSolde(),
                        "§3",
                        "§fBoost: §a+" + (int) (account.getBoost().getRealBooster() * 100 - 100) + "%",
                        "§4",
                        "§emc.endoskull.fr");
                int i = 0;
                for (String line : lines) {
                    if (line.length() > 30) {
                        lines.set(i, line.substring(0, 30));
                    }
                    i++;
                }
                board.updateLines(lines);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
