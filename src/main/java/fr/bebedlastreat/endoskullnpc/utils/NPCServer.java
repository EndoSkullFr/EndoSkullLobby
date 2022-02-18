package fr.bebedlastreat.endoskullnpc.utils;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.github.juliarn.npc.NPC;
import com.github.juliarn.npc.modifier.MetadataModifier;
import com.github.juliarn.npc.profile.Profile;
import java.util.Random;
import java.util.UUID;

import fr.bebedlastreat.endoskullnpc.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NPCServer {
    private final Location location;

    private UUID uuid;

    private String infoLine;

    private String target;

    private Material itemInHand;

    private boolean lookAtPlayer;

    private boolean imitatePlayer;

    private NPC npc;

    public NPCServer(UUID uuid, Location location, String target, Material itemInHand, boolean lookAtPlayer, boolean imitatePlayer) {
        this.uuid = uuid;
        this.location = location;
        this.target = target;
        this.itemInHand = itemInHand;
        this.lookAtPlayer = lookAtPlayer;
        this.imitatePlayer = imitatePlayer;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getInfoLine() {
        return this.infoLine;
    }

    public void setInfoLine(String infoLine) {
        this.infoLine = infoLine;
    }

    public Location getLocation() {
        return this.location;
    }

    public String getTargetGroup() {
        return this.target;
    }

    public void setTargetGroup(String targetGroup) {
        this.target = targetGroup;
    }

    public Material getItemInHand() {
        return this.itemInHand;
    }

    public void setItemInHand(Material itemInHand) {
        this.itemInHand = itemInHand;
    }

    public boolean isLookAtPlayer() {
        return this.lookAtPlayer;
    }

    public void setLookAtPlayer(boolean lookAtPlayer) {
        this.lookAtPlayer = lookAtPlayer;
    }

    public boolean isImitatePlayer() {
        return this.imitatePlayer;
    }

    public void setImitatePlayer(boolean imitatePlayer) {
        this.imitatePlayer = imitatePlayer;
    }

    public void build() {
        NPC npc = NPC.builder().profile(createProfile(this.uuid)).location(getLocation()).lookAtPlayer(isLookAtPlayer()).imitatePlayer(isImitatePlayer()).spawnCustomizer((spawnedNPC, player) -> {
            spawnedNPC.rotation().queueRotate(getLocation().getYaw(), getLocation().getPitch()).send(new Player[] { player });
            spawnedNPC.metadata().queue(MetadataModifier.EntityMetadata.SKIN_LAYERS, Boolean.valueOf(true)).queue(MetadataModifier.EntityMetadata.SNEAKING, Boolean.valueOf(false)).send(new Player[] { player });
            if (this.itemInHand != null)
                spawnedNPC.equipment().queue(EnumWrappers.ItemSlot.MAINHAND, new ItemStack(this.itemInHand)).send(new Player[] { player });
        }).build(Main.getNpcPool());
        NPCSpawnManager.getNPC().put(Integer.valueOf(npc.getEntityId()), this);
    }

    public Profile createProfile(UUID uuid) {
        Profile profile = new Profile(uuid);
        profile.complete();
        profile.setName("");
        profile.setUniqueId(new UUID((new Random()).nextLong(), 0L));
        return profile;
    }
}
