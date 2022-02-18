package fr.bebedlastreat.endoskullnpc.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum FriendSettings {
    FRIEND_REQUESTS("Demandes d'amis", 0, "§cDésactivé", "§aActivé", new ItemStack(Material.RAW_FISH, 1, (byte) 2)),
    FRIEND_JUMP("Amis peuvent rejoindre votre serveur", 4, "Activé", "Désactivé", Material.IRON_BOOTS),
    ONLINE_STATUS("Status en ligne", 3, "Activé", "Désactivé", Material.WATCH),
    ONLINE_NOTIFICATIONS("Notifications lors de la connexion d'un ami", 101, "Activé", "Désactivé", Material.BEACON),
    PRIVATE_MESSAGE("Messages privés", 2, "Activé", "Désactivé", Material.BOOK_AND_QUILL),
    PARTY_INVITE("Invitations à des parties", 1, "Activé", "Désactivé", Material.MINECART);

    private String name;
    private int settingId;
    private String setting0;
    private String setting1;
    private ItemStack item;

    FriendSettings(String name, int settingId, String setting0, String setting1, Material type) {
        this.name = name;
        this.settingId = settingId;
        this.setting0 = setting0;
        this.setting1 = setting1;
        this.item = new ItemStack(type);
    }

    FriendSettings(String name, int settingId, String setting0, String setting1, ItemStack item) {
        this.name = name;
        this.settingId = settingId;
        this.setting0 = setting0;
        this.setting1 = setting1;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public int getSettingId() {
        return settingId;
    }

    public String getSetting0() {
        return setting0;
    }

    public String getSetting1() {
        return setting1;
    }

    public ItemStack getItem() {
        return item;
    }
}
