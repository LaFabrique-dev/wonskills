package com.st0x0ef.won_skills.skillsData;

import com.st0x0ef.won_skills.Competency;
import com.st0x0ef.won_skills.config.FarmerConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class FarmerSkillData extends SkillData {
    private static FarmerSkillData INSTANCE;

    public FarmerSkillData() {
        super.init("farmer");

        xp_breaked = FarmerConfig.FARMER_XP_BREAKED.get();
        xp_crafted = FarmerConfig.FARMER_XP_CRAFTED.get();
        xp_smelted = FarmerConfig.FARMER_XP_SMELTED.get();

        for (String item : FarmerConfig.FARMER_ITEM_BREAKED.get()) {
            items_breaked.add(Registry.ITEM.get(ResourceLocation.tryParse(item)));
        }

        for (String item : FarmerConfig.FARMER_ITEM_CRAFTED.get()) {
            items_crafted.add(Registry.ITEM.get(ResourceLocation.tryParse(item)));
        }

        for (String item : FarmerConfig.FARMER_ITEM_SMELTED.get()) {
            items_smelted.add(Registry.ITEM.get(ResourceLocation.tryParse(item)));
        }

        competency = new Competency(FarmerConfig.FARMER_COMPETENCIES.get());

        INSTANCE = this;
    }

    public static FarmerSkillData getInstance() {
        return INSTANCE;
    }
}
