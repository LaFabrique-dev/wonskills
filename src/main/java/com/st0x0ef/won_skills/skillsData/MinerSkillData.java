package com.st0x0ef.won_skills.skillsData;

import com.st0x0ef.won_skills.Competency;
import com.st0x0ef.won_skills.config.MinerConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class MinerSkillData extends SkillData {
    private static MinerSkillData INSTANCE;

    public MinerSkillData() {
        super.init("miner");

        xp_breaked = MinerConfig.MINER_XP_BREAKED.get();
        xp_crafted = MinerConfig.MINER_XP_CRAFTED.get();
        xp_smelted = MinerConfig.MINER_XP_SMELTED.get();

        for (String item : MinerConfig.MINER_ITEM_BREAKED.get()) {
            items_breaked.add(Registry.ITEM.get(ResourceLocation.tryParse(item)));
        }

        for (String item : MinerConfig.MINER_ITEM_CRAFTED.get()) {
            items_crafted.add(Registry.ITEM.get(ResourceLocation.tryParse(item)));
        }

        for (String item : MinerConfig.MINER_ITEM_SMELTED.get()) {
            items_smelted.add(Registry.ITEM.get(ResourceLocation.tryParse(item)));
        }

        competency = new Competency(MinerConfig.MINER_COMPETENCIES.get());

        INSTANCE = this;
    }

    public static MinerSkillData getInstance() {
        return INSTANCE;
    }
}
