package com.st0x0ef.won_skills.config;

import com.st0x0ef.won_skills.CompetenciesManager;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class WoodcutterConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Integer> WOODCUTTER_XP_NEEDED;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> WOODCUTTER_ITEM_BREAKED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Double>> WOODCUTTER_XP_BREAKED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> WOODCUTTER_ITEM_CRAFTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Double>> WOODCUTTER_XP_CRAFTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> WOODCUTTER_ITEM_SMELTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Double>> WOODCUTTER_XP_SMELTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> WOODCUTTER_COMPETENCIES;

    static {

        BUILDER.push("Woodcutter");

            WOODCUTTER_XP_NEEDED = BUILDER.comment("Woodcutter XP needed to get one point").defineInRange("woodcutter xp", CompetenciesManager.DEFAULT_COMPETENCIES_XP_POINT, 0, 999999999);

        BUILDER.pop();

        BUILDER.push("Woodcutter Event");

        WOODCUTTER_ITEM_BREAKED = BUILDER.defineList("breaked item", List.of(Items.ACACIA_LOG.getRegistryName().toString(), Items.BIRCH_LOG.getRegistryName().toString()), entry -> true);
        WOODCUTTER_XP_BREAKED = BUILDER.defineList("breaked xp", List.of(0.5d, 0.5d), entry -> true);
        WOODCUTTER_ITEM_CRAFTED = BUILDER.defineList("crafted item", List.of(Items.WOODEN_AXE.getRegistryName().toString(), Items.DIAMOND_AXE.getRegistryName().toString()), entry -> true);
        WOODCUTTER_XP_CRAFTED = BUILDER.defineList("crafted xp", List.of(1d, 10d), entry -> true);
        WOODCUTTER_ITEM_SMELTED = BUILDER.defineList("smelted item", List.of(), entry -> true);
        WOODCUTTER_XP_SMELTED = BUILDER.defineList("smelted xp", List.of(), entry -> true);

        BUILDER.pop();

        BUILDER.push("Woodcutter Skills");

        WOODCUTTER_COMPETENCIES = BUILDER.defineList("Competencies", List.of("name: test; price: 1; banned-items-commands: manuadd $p <perm>, manuadd $p <perm2>; effects: haste;", "name: test2; require: test; price: 2; banned-items-commands: manuadd $p <perm>, manuadd $p <perm2>; effect: haste;"), entry -> true);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
