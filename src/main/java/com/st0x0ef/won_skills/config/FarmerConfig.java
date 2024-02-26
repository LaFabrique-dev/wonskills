package com.st0x0ef.won_skills.config;

import com.st0x0ef.won_skills.CompetenciesManager;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class FarmerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Integer> FARMER_XP_NEEDED;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> FARMER_ITEM_BREAKED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Double>> FARMER_XP_BREAKED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> FARMER_ITEM_CRAFTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Double>> FARMER_XP_CRAFTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> FARMER_ITEM_SMELTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Double>> FARMER_XP_SMELTED;
    public static final ForgeConfigSpec.ConfigValue<Double> FARMER_XP_BONEMEAL;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> FARMER_COMPETENCIES;


    static {

        BUILDER.push("Farmer");

            FARMER_XP_NEEDED = BUILDER.comment("Farmer XP needed to get one point").defineInRange("farmer xp", CompetenciesManager.DEFAULT_COMPETENCIES_XP_POINT, 0, 999999999);

        BUILDER.pop();

        BUILDER.push("Farmer Event");

        FARMER_ITEM_BREAKED = BUILDER.defineList("breaked item", List.of(Items.BIRCH_LEAVES.getRegistryName().toString(), Items.OAK_LEAVES.getRegistryName().toString()), entry -> true);
        FARMER_XP_BREAKED = BUILDER.defineList("breaked xp", List.of(0.5d, 0.5d), entry -> true);
        FARMER_ITEM_CRAFTED = BUILDER.defineList("crafted item", List.of(Items.WOODEN_HOE.getRegistryName().toString(), Items.OAK_PLANKS.getRegistryName().toString()), entry -> true);
        FARMER_XP_CRAFTED = BUILDER.defineList("crafted xp", List.of(1d, 1d), entry -> true);
        FARMER_ITEM_SMELTED = BUILDER.defineList("smelted item", List.of(), entry -> true);
        FARMER_XP_SMELTED = BUILDER.defineList("smelted xp", List.of(), entry -> true);
        FARMER_XP_BONEMEAL = BUILDER.define("bonemeal xp", 1d, entry -> true);

        BUILDER.pop();

        BUILDER.push("Farmer Skills");

        FARMER_COMPETENCIES = BUILDER.defineList("Competencies", List.of("name: test; price: 1; banned-items-commands: manuadd $p <perm>, manuadd $p <perm2>; effects: haste;", "name: test2; require: test; price: 2; banned-items-commands: manuadd $p <perm>, manuadd $p <perm2>; effect: haste;"), entry -> true);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
