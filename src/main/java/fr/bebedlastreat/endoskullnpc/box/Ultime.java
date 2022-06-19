package fr.bebedlastreat.endoskullnpc.box;

import fr.endoskull.api.spigot.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public enum Ultime {
    BOOSTER9("§eBOOSTER §l+300% §7(1 jour)", 10, "boost addtemp %player% 3 1d", new ItemStack(Material.YELLOW_FLOWER), false),
    BOOSTER8("§eBOOSTER §l+250% §7(1 jour)", 15, "boost addtemp %player% 2.5 1d", new ItemStack(Material.RED_ROSE, 1, (byte) 0), false),
    BOOSTER7("§eBOOSTER §l+250% §7(3 jours)", 15, "boost addtemp %player% 2.5 3d", new ItemStack(Material.RED_ROSE, 1, (byte) 1), false),
    BOOSTER6("§eBOOSTER §l+200% §7(3 jours)", 15, "boost addtemp %player% 2 3d", new ItemStack(Material.RED_ROSE, 1, (byte) 2), false),
    BOOSTER5("§eBOOSTER §l+200% §7(7 jours)", 15, "boost addtemp %player% 2 7d", new ItemStack(Material.RED_ROSE, 1, (byte) 3), false),
    BOOSTER4("§eBOOSTER §l+150% §7(7 jours)", 20, "boost addtemp %player% 1.5 7d", new ItemStack(Material.RED_ROSE, 1, (byte) 4), false),
    BOOSTER3("§eBOOSTER §l+150% §7(14 jours)", 20, "boost addtemp %player% 1.5 14d", new ItemStack(Material.RED_ROSE, 1, (byte) 5), false),
    BOOSTER2("§eBOOSTER §l+100% §7(14 jours)", 25, "boost addtemp %player% 1 14d", new ItemStack(Material.RED_ROSE, 1, (byte) 6), false),
    BOOSTER1("§eBOOSTER §l+100% §7(30 jours)", 25, "boost addtemp %player% 1 30d", new ItemStack(Material.RED_ROSE, 1, (byte) 7), false),
    BOOSTER0("§eBOOSTER §l+50% §7(30 jours)", 30, "boost addtemp %player% 0.5 30d", new ItemStack(Material.RED_ROSE, 1, (byte) 8), false),
    DOUBLE("§42 Clés Ultimes", 2, "key add %player% ULTIME 2", new ItemStack(Material.TRIPWIRE_HOOK, 2), false),
    VIP("§eGrade VIP §7(1 mois)", 6, "lp user %player% parent addtemp vip 1mo accumulate", new CustomItemStack(Material.GOLD_INGOT), true),
    HERO("§bGrade Hero §7(1 mois)", 2, "lp user %player% parent addtemp hero 1mo accumulate", new ItemStack(Material.DIAMOND), true);

    //50-100 30j 100-150 14j 150-200 7j 200-250 3j 250-300 1j

    private String name;
    private double probability;
    private String command;
    private ItemStack item;
    private boolean small;

    Ultime(String name, double probability, String command, ItemStack item, boolean small) {
        this.name = name;
        this.probability = probability;
        this.command = command;
        this.item = item;
        this.small = small;
    }

    public String getName() {
        return name;
    }
    public double getProbability() {
        return probability;
    }
    public String getCommand() {
        return command;
    }
    public ItemStack getItem() {
        return item;
    }

    public double getPourcent() {
        double allProbability = 0;
        for (Ultime value : Ultime.values()) {
            allProbability += value.getProbability();
        }

        return  getProbability() / (allProbability / 100);
    }

    public static Ultime getByName(String name) {
        return Arrays.stream(values()).filter(r -> r.getName().equals(name)).findAny().orElse(Ultime.BOOSTER1);
    }

    public boolean isSmall() {
        return small;
    }
}
