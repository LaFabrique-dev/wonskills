package com.st0x0ef.won_skills.skillsData;

import com.st0x0ef.won_skills.Competency;
import com.st0x0ef.won_skills.config.EngineerConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class EngineerSkillData extends SkillData {
    private static EngineerSkillData INSTANCE;

    public EngineerSkillData() {
        super.init("engineer");

        xp_breaked = EngineerConfig.ENGINEER_XP_BREAKED.get();
        xp_crafted = EngineerConfig.ENGINEER_XP_CRAFTED.get();
        xp_smelted = EngineerConfig.ENGINEER_XP_SMELTED.get();

        for (String item : EngineerConfig.ENGINEER_ITEM_BREAKED.get()) {
            items_breaked.add(Registry.ITEM.get(ResourceLocation.tryParse(item)));
        }

        for (String item : EngineerConfig.ENGINEER_ITEM_CRAFTED.get()) {
            items_crafted.add(Registry.ITEM.get(ResourceLocation.tryParse(item)));
        }

        for (String item : EngineerConfig.ENGINEER_ITEM_SMELTED.get()) {
            items_smelted.add(Registry.ITEM.get(ResourceLocation.tryParse(item)));
        }

        competency = new Competency(EngineerConfig.ENGINEER_COMPETENCIES.get());

        INSTANCE = this;
    }

    public static EngineerSkillData getInstance() {
        return INSTANCE;
    }
}
