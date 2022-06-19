package fr.bebedlastreat.endoskullnpc.utils;

import fr.endoskull.api.commons.lang.MessageUtils;

public enum LobbyMessage implements MessageUtils {
    RED, GREEN, BLUE, CLICK_SKIP, PEARL_COLOR, BOOSTER9, BOOSTER8, BOOSTER7,
    BOOSTER6, BOOSTER5, BOOSTER4, BOOSTER3, BOOSTER2, BOOSTER1, BOOSTER0, DOUBLE, VIP, HERO,
    ULTIME, BOOST3, BOOST2, BOOST1, COINS5, COINS4, COINS3, COINS2, COINS1, FLY_ENABLE, FLY_DISABLE;

    private final static String path = "lobby";

    @Override
    public String getPath() {
        return path + "." + this.toString().toLowerCase().replace("_", "-");
    }
}
