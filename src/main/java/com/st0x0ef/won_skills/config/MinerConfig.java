package com.st0x0ef.won_skills.config;

import com.st0x0ef.won_skills.CompetenciesManager;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class MinerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Integer> MINER_XP_NEEDED;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> MINER_ITEM_BREAKED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Double>> MINER_XP_BREAKED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> MINER_ITEM_CRAFTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Double>> MINER_XP_CRAFTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> MINER_ITEM_SMELTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Double>> MINER_XP_SMELTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> MINER_COMPETENCIES;



    static {

        BUILDER.push("Miner");

        MINER_XP_NEEDED = BUILDER.comment("Miner XP needed to get one point").defineInRange("miner xp", CompetenciesManager.DEFAULT_COMPETENCIES_XP_POINT, 0, 999999999);

        BUILDER.pop();

        BUILDER.push("Miner Event");

        MINER_ITEM_BREAKED = BUILDER.defineList("breaked item", List.of(Items.STONE.getRegistryName().toString(), Items.NETHERITE_BLOCK.getRegistryName().toString()), entry -> true);
        MINER_XP_BREAKED = BUILDER.defineList("breaked xp", List.of(0.1d, 10d), entry -> true);
        MINER_ITEM_CRAFTED = BUILDER.defineList("crafted item", List.of(Items.WOODEN_PICKAXE.getRegistryName().toString(), Items.DIAMOND_PICKAXE.getRegistryName().toString()), entry -> true);
        MINER_XP_CRAFTED = BUILDER.defineList("crafted xp", List.of(1d, 10d), entry -> true);
        MINER_ITEM_SMELTED = BUILDER.defineList("smelted item", List.of(Items.IRON_INGOT.getRegistryName().toString(), Items.GOLD_INGOT.getRegistryName().toString()), entry -> true);
        MINER_XP_SMELTED = BUILDER.defineList("smelted xp", List.of(1d, 10d), entry -> true);

        BUILDER.pop();

        BUILDER.push("Miner Skills");

        MINER_COMPETENCIES = BUILDER.defineList("Competencies", List.of("name: test; price: 1; banned-items-commands: manuadd $p <perm>, manuadd $p <perm2>; effects: haste;", "name: test2; require: test; price: 2; banned-items-commands: manuadd $p <perm>, manuadd $p <perm2>; effect: haste;"), entry -> true);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
