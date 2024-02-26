package com.st0x0ef.won_skills.config;

import com.st0x0ef.won_skills.CompetenciesManager;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class EngineerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Integer> ENGINEER_XP_NEEDED;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENGINEER_ITEM_BREAKED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Double>> ENGINEER_XP_BREAKED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENGINEER_ITEM_CRAFTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Double>> ENGINEER_XP_CRAFTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENGINEER_ITEM_SMELTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Double>> ENGINEER_XP_SMELTED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENGINEER_COMPETENCIES;


    static {

        BUILDER.push("Engineer");

            ENGINEER_XP_NEEDED = BUILDER.comment("Engineer XP needed to get one point").defineInRange("engineer xp", CompetenciesManager.DEFAULT_COMPETENCIES_XP_POINT, 0, 999999999);

        BUILDER.pop();

        BUILDER.push("Engineer Event");

        ENGINEER_ITEM_BREAKED = BUILDER.defineList("breaked item", List.of(), entry -> true);
        ENGINEER_XP_BREAKED = BUILDER.defineList("breaked xp", List.of(), entry -> true);
        ENGINEER_ITEM_CRAFTED = BUILDER.defineList("crafted item", List.of(Items.FURNACE.getRegistryName().toString(), Items.CRAFTING_TABLE.getRegistryName().toString()), entry -> true);
        ENGINEER_XP_CRAFTED = BUILDER.defineList("crafted xp", List.of(1d, 1d), entry -> true);
        ENGINEER_ITEM_SMELTED = BUILDER.defineList("smelted item", List.of(), entry -> true);
        ENGINEER_XP_SMELTED = BUILDER.defineList("smelted xp", List.of(), entry -> true);

        BUILDER.pop();

        BUILDER.push("Engineer Skills");

        ENGINEER_COMPETENCIES = BUILDER.defineList("Competencies", List.of("name: test; price: 1; banned-items-commands: manuadd $p <perm>, manuadd $p <perm2>; effects: haste;", "name: test2; require: test; price: 2; banned-items-commands: manuadd $p <perm>, manuadd $p <perm2>; effect: haste;"), entry -> true);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
