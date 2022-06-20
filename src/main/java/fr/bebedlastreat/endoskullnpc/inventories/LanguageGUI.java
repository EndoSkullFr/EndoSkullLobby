package fr.bebedlastreat.endoskullnpc.inventories;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.bebedlastreat.endoskullnpc.utils.LobbyMessage;
import fr.endoskull.api.Main;
import fr.endoskull.api.commons.account.Account;
import fr.endoskull.api.commons.account.AccountProvider;
import fr.endoskull.api.spigot.utils.CustomGui;
import fr.endoskull.api.spigot.utils.CustomItemStack;
import fr.endoskull.api.spigot.utils.Languages;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LanguageGUI extends CustomGui {
    private static int[] glassSlots = {0, 1, 7, 8, 9, 17, 27, 35, 36, 37, 43, 44};
    public LanguageGUI(Player p) {
        super(5, Languages.getLang(p).getMessage(LobbyMessage.GUI_LANGUAGE), p);
        Languages lang = Languages.getLang(p);
        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
        for (int glassSlot : glassSlots) {
            setItem(glassSlot, CustomItemStack.getPane(3).setName("§r"));
        }
        for (Languages value : Languages.values()) {
            setItem(value.getSlot(), CustomItemStack.getSkull(value.getSkull()).setName((lang == value ? "§a✔ " : "§e") + value.getName()).setLore(value.getLore()), player -> {
                if (value == lang) return;
                Account account = AccountProvider.getAccount(player.getUniqueId());
                account.setLang(value);
                Main.getLangs().put(player, value);
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
                player.sendMessage(value.getMessage(LobbyMessage.LANGUAGE_CHANGE).replace("{lang}", value.getName()));
                //todo plugin message
                ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
                dataOutput.writeUTF("UpdateLanguage");
                player.sendPluginMessage(fr.bebedlastreat.endoskullnpc.Main.getInstance(), Main.MESSAGE_CHANNEL, dataOutput.toByteArray());
            });
        }

    }
}
