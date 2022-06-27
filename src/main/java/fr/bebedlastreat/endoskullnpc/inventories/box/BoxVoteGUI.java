package fr.bebedlastreat.endoskullnpc.inventories.box;

import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.box.Vote;
import fr.bebedlastreat.endoskullnpc.utils.LobbyMessage;
import fr.endoskull.api.commons.account.Account;
import fr.endoskull.api.commons.account.AccountProvider;
import fr.endoskull.api.commons.lang.MessageUtils;
import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import fr.endoskull.api.spigot.utils.Languages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoxVoteGUI extends CustomGui {

    public BoxVoteGUI(Player p) {
        super((Vote.values().length-1)/9 + 3, Languages.getLang(p).getMessage(LobbyMessage.GUI_BOX_VOTE), p);
        Languages lang = Languages.getLang(p);
        int lines = (Vote.values().length-1)/9 + 3;
        p.playSound(p.getLocation(), Sound.CAT_MEOW, 50, 50);
        Account account = new AccountProvider(p.getUniqueId()).getAccount();
        int i = 0;
        for (Vote value : Vote.values()) {
            setItem(i, new CustomItemStack(value.getItem()).setName(lang.getMessage(value.getName())).setLore("\n" + lang.getMessage(LobbyMessage.PROBABILITY) + " " + lang.getMessage(value.getName()).substring(0, 2) + value.getPourcent() + "%"));
            i++;
        }
        for (int j = lines * 9 - 18; j < lines * 9 - 9; j++) {
            setItem(j, new CustomItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14).setName("§r"));
        }
        boolean s = account.getStatistic("key/vote") > 1;
        setItem(lines * 9 - 5, new CustomItemStack(Material.ANVIL).setName("§d§l" + lang.getMessage(LobbyMessage.BOX_OPEN)).setLore(lang.getMessage(LobbyMessage.VOTE_AMOUNT).replace("{amount}", String.valueOf(account.getStatistic("key/vote"))).replace("{s}", (s ? "s" : "" ))), player -> {
            player.closeInventory();
            Account account1 = new AccountProvider(player.getUniqueId()).getAccount();
            if (account1.getStatistic("key/vote") < 1) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 50, 50);
                player.sendMessage(lang.getMessage(LobbyMessage.NEED_VOTE_KEY));
                return;
            } else {
                account1.incrementStatistic("key/vote", -1);
                new OpeningInventory(p, lang).open(p);
            }
        });

    }

    private static class OpeningInventory extends CustomGui {
        public OpeningInventory(Player p, Languages lang) {
            super(3, lang.getMessage(LobbyMessage.GUI_BOX_VOTE), p);
            List<ItemStack> items = new ArrayList<>();
            for (Vote value : Vote.values()) {
                for (int i = 0; i < value.getProbability() * 2; i++) {
                    items.add(new CustomItemStack(value.getItem()).setName(lang.getMessage(value.getName())));
                }
            }
            Collections.shuffle(items);
            for (int i = 0; i < 27; i++) {
                setItem(i, CustomItemStack.getPane(14).setName("§7").setGlow(i == 4 || i == 22));
            }

            for (int i = 0; i < 9; i++) {
                setItem(9 + i, items.get(i));
            }

            if (!Main.getInstance().getOpeningKeys().containsKey(p)) Main.getInstance().getOpeningKeys().put(p, this);
            scheduleVote(items, p, 1, lang);
        }

        public void scheduleVote(final List<ItemStack> items, Player player, final int fIndex, Languages lang) {
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                int index = fIndex;
                for (int i = 0; i < 9; i++) {
                    setItem(9 + i, items.get(index + i));
                }

                index++;
                if (index > 50) {
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 50, 50);
                    System.out.println("Clé vote " + player.getName() + " " + Vote.getByName(lang, items.get(54).getItemMeta().getDisplayName()).getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Vote.getByName(lang, items.get(54).getItemMeta().getDisplayName()).getCommand().replace("%player%", player.getName()));
                    player.sendMessage("");
                    player.sendMessage(lang.getMessage(LobbyMessage.VOTE_REWARD).replace("{reward}", items.get(54).getItemMeta().getDisplayName()));
                    player.sendMessage("");
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                        Main.getInstance().getOpeningKeys().remove(player);
                        player.closeInventory();
                    }, 40);
                    return;
                } else {
                    player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1.0f, 0.5f);
                    scheduleVote(items, player, index, lang);
                }
            }, Math.round(Math.pow(Math.pow(Math.pow(1.000018, fIndex), fIndex), fIndex)));
        }
    }
}