package fr.bebedlastreat.endoskullnpc.utils;

import fr.endoskull.api.spigot.utils.Languages;
import me.leoko.advancedgui.utils.Layout;
import me.leoko.advancedgui.utils.LayoutExtension;
import me.leoko.advancedgui.utils.events.LayoutLoadEvent;
import org.bukkit.event.EventHandler;

public class BoxLayout implements LayoutExtension {

    public static final String LAYOUT_VOTE = "CleVote";
    public static final String LAYOUT_ULTIME = "Cle_Ultime";
    private static boolean doOnce = false;

    @Override
    @EventHandler
    public void onLayoutLoad(LayoutLoadEvent e) {
        Layout layout = e.getLayout();
        if (layout.getName().equals(LAYOUT_ULTIME)) {
            System.out.println("Layout chargé !");
            layout.registerTextProcessor((player, interaction, text) -> {
                return text.replaceAll("%key_ultime%", Languages.getLang(player).getMessage(LobbyMessage.KEY_ULTIME));
            });
        }
        if (layout.getName().equals(LAYOUT_VOTE)) {
            System.out.println("Layout chargé !");
            layout.registerTextProcessor((player, interaction, text) -> {
                return text.replaceAll("%key_vote%", Languages.getLang(player).getMessage(LobbyMessage.KEY_VOTE));
            });
        }
    }
}
