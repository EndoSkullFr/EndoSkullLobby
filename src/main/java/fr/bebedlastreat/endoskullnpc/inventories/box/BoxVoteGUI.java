package fr.bebedlastreat.endoskullnpc.inventories.box;

import fr.bebedlastreat.endoskullnpc.Main;
import fr.bebedlastreat.endoskullnpc.box.Vote;
import fr.endoskull.api.commons.account.Account;
import fr.endoskull.api.commons.account.AccountProvider;
import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
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
        super((Vote.values().length-1)/9 + 3, "Box Vote");
        int lines = (Vote.values().length-1)/9 + 3;
        p.playSound(p.getLocation(), Sound.CAT_MEOW, 50, 50);
        Account account = new AccountProvider(p.getUniqueId()).getAccount();
        int i = 0;
        for (Vote value : Vote.values()) {
            setItem(i, new CustomItemStack(value.getItem()).setName(value.getName()).setLore("\n§7Probabilité: " + value.getName().substring(0, 2) + value.getPourcent() + "%"));
            i++;
        }
        for (int j = lines * 9 - 18; j < lines * 9 - 9; j++) {
            setItem(j, new CustomItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14).setName("§r"));
        }
        boolean s = account.getStatistic("key/vote") > 1;
        setItem(lines * 9 - 5, new CustomItemStack(Material.ANVIL).setName("§d§lOUVRIR").setLore("\n§7Vous avez §d" + account.getStatistic("key/vote") + " §7Clé" + (s ? "s" : "" ) +" Vote" + (s ? "s" : "" )), player -> {
            player.closeInventory();
            Account account1 = new AccountProvider(player.getUniqueId()).getAccount();
            if (account1.getStatistic("key/vote") < 1) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 50, 50);
                player.sendMessage("§cVous devez posséder une §lClé Vote §cpour effectuer cette action");
                return;
            } else {
                account1.incrementStatistic("key/vote", -1);
                new OpeningInventory(p).open(p);
            }
        });

    }

    private static class OpeningInventory extends CustomGui {
        public OpeningInventory(Player p) {
            super(3, "§dEndoSkull §8» §dBOX VOTE");
            List<ItemStack> items = new ArrayList<>();
            for (Vote value : Vote.values()) {
                for (int i = 0; i < value.getProbability() * 2; i++) {
                    items.add(new CustomItemStack(value.getItem()).setName(value.getName()));
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
            scheduleVote(items, p, 1);
        }

        public void scheduleVote(final List<ItemStack> items, Player player, final int fIndex) {
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                int index = fIndex;
                for (int i = 0; i < 9; i++) {
                    setItem(9 + i, items.get(index + i));
                }

                index++;
                if (index > 50) {
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 50, 50);
                    System.out.println("Clé vote " + player.getName() + " " + Vote.getByName(items.get(54).getItemMeta().getDisplayName()).getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Vote.getByName(items.get(54).getItemMeta().getDisplayName()).getCommand().replace("%player%", player.getName()));
                    player.sendMessage("");
                    player.sendMessage("§cEndoSkull §8» §fTu as gagné " + items.get(54).getItemMeta().getDisplayName() + " §fdans ta Clé Vote");
                    player.sendMessage("");
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                        Main.getInstance().getOpeningKeys().remove(player);
                        player.closeInventory();
                    }, 40);
                    return;
                } else {
                    player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1.0f, 0.5f);
                    scheduleVote(items, player, index);
                }
            }, Math.round(Math.pow(Math.pow(Math.pow(1.000018, fIndex), fIndex), fIndex)));
        }
    }
}