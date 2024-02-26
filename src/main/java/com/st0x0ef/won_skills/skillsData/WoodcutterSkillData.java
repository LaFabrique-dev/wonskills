package com.st0x0ef.won_skills.skillsData;

import com.st0x0ef.won_skills.Competency;
import com.st0x0ef.won_skills.config.WoodcutterConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class WoodcutterSkillData extends SkillData {
    private static WoodcutterSkillData INSTANCE;

    public WoodcutterSkillData() {
        super.init("woodcutter");

        xp_breaked = WoodcutterConfig.WOODCUTTER_XP_BREAKED.get();
        xp_crafted = WoodcutterConfig.WOODCUTTER_XP_CRAFTED.get();
        xp_smelted = WoodcutterConfig.WOODCUTTER_XP_SMELTED.get();

        for (String item : WoodcutterConfig.WOODCUTTER_ITEM_BREAKED.get()) {
            items_breaked.add(Registry.ITEM.get(ResourceLocation.tryParse(item)));
        }

        for (String item : WoodcutterConfig.WOODCUTTER_ITEM_CRAFTED.get()) {
            items_crafted.add(Registry.ITEM.get(ResourceLocation.tryParse(item)));
        }

        for (String item : WoodcutterConfig.WOODCUTTER_ITEM_SMELTED.get()) {
            items_smelted.add(Registry.ITEM.get(ResourceLocation.tryParse(item)));
        }

        competency = new Competency(WoodcutterConfig.WOODCUTTER_COMPETENCIES.get());

        INSTANCE = this;
    }
    
    public static WoodcutterSkillData getInstance() {
        return INSTANCE;
    }
}
