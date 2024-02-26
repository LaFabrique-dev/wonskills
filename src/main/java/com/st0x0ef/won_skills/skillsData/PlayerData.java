package com.st0x0ef.won_skills.skillsData;

import com.st0x0ef.won_skills.SkillsManager;
import com.st0x0ef.won_skills.Utils;
import com.st0x0ef.won_skills.config.EngineerConfig;
import com.st0x0ef.won_skills.config.FarmerConfig;
import com.st0x0ef.won_skills.config.MinerConfig;
import com.st0x0ef.won_skills.config.WoodcutterConfig;
import com.st0x0ef.won_skills.database.CompetenciesDB;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    public final String uuid;
    public final Player player;
    public final String name;

    public double minerXp;
    public double farmerXp;
    public double woodcutterXp;
    public double engineerXp;

    public int minerPoints;
    public int farmerPoints;
    public int woodcutterPoints;
    public int engineerPoints;

    /** Competencies */
    public final List<Boolean> is_unlocked_miner_competencies;
    public final List<Boolean> is_unlocked_farmer_competencies;
    public final List<Boolean> is_unlocked_woodcutter_competencies;
    public final List<Boolean> is_unlocked_engineer_competencies;

    /** Other */
    private double tmp_amount = 0d;
    private int message_time_left = 0;

    public PlayerData(Player player) {
        this.player = player;
        this.uuid = player.getStringUUID();
        this.name = player.getScoreboardName();

        this.minerXp = 0;
        this.farmerXp = 0;
        this.woodcutterXp = 0;
        this.engineerXp = 0;

        this.minerPoints = 0;
        this.farmerPoints = 0;
        this.woodcutterPoints = 0;
        this.engineerPoints = 0;

        this.is_unlocked_miner_competencies = new ArrayList<>();
        this.is_unlocked_farmer_competencies = new ArrayList<>();
        this.is_unlocked_woodcutter_competencies = new ArrayList<>();
        this.is_unlocked_engineer_competencies = new ArrayList<>();

        SkillsManager.registerPlayer(this);
    }

    public void giveEffects() {
        for (Boolean isUnlockedMinerCompetency : is_unlocked_miner_competencies) {
            if (isUnlockedMinerCompetency) {
                for (MobEffect effect : MinerSkillData.getInstance().competency.mob_effects) {
                    player.addEffect(new MobEffectInstance(effect));
                }
            }
        }

        for (Boolean isUnlockedFarmerCompetency : is_unlocked_farmer_competencies) {
            if (isUnlockedFarmerCompetency) {
                for (MobEffect effect : FarmerSkillData.getInstance().competency.mob_effects) {
                    player.addEffect(new MobEffectInstance(effect));
                }
            }
        }

        for (Boolean isUnlockedEngineerCompetency : is_unlocked_engineer_competencies) {
            if (isUnlockedEngineerCompetency) {
                for (MobEffect effect : EngineerSkillData.getInstance().competency.mob_effects) {
                    player.addEffect(new MobEffectInstance(effect));
                }
            }
        }

        for (int i = 0; i < is_unlocked_woodcutter_competencies.size(); i++) {
            for (MobEffect effect : WoodcutterSkillData.getInstance().competency.mob_effects) {
                player.addEffect(new MobEffectInstance(effect));
            }
        }
    }

    public void verifyForPoint(String skill) {
        if (MinerSkillData.getInstance().is(skill) && minerXp >= MinerConfig.MINER_XP_NEEDED.get()) {
            SkillsManager.addSkillXp(uuid, skill, -MinerConfig.MINER_XP_NEEDED.get());
            SkillsManager.addSkillPoints(uuid, skill, 1);
        }

        else if (FarmerSkillData.getInstance().is(skill) && farmerXp >= FarmerConfig.FARMER_XP_NEEDED.get()) {
            SkillsManager.addSkillXp(uuid, skill, -FarmerConfig.FARMER_XP_NEEDED.get());
            SkillsManager.addSkillPoints(uuid, skill, 1);
        }

        else if (WoodcutterSkillData.getInstance().is(skill) && woodcutterXp >= WoodcutterConfig.WOODCUTTER_XP_NEEDED.get()) {
            SkillsManager.addSkillXp(uuid, skill, -WoodcutterConfig.WOODCUTTER_XP_NEEDED.get());
            SkillsManager.addSkillPoints(uuid, skill, 1);
        }

        else if (EngineerSkillData.getInstance().is(skill) && engineerXp >= EngineerConfig.ENGINEER_XP_NEEDED.get()) {
            SkillsManager.addSkillXp(uuid, skill, -EngineerConfig.ENGINEER_XP_NEEDED.get());
            SkillsManager.addSkillPoints(uuid, skill, 1);
        }
    }

    public void addMinerXp(double amount) {
        amount = Utils.round(amount, 2);
        minerXp += amount;
        verifyForPoint(MinerSkillData.getInstance().name);
    }

    public void addFarmerXp(double amount) {
        amount = Utils.round(amount, 2);
        farmerXp += amount;
        verifyForPoint(FarmerSkillData.getInstance().name);
    }

    public void addWoodcutterXp(double amount) {
        amount = Utils.round(amount, 2);
        woodcutterXp += amount;
        verifyForPoint(WoodcutterSkillData.getInstance().name);
    }

    public void addEngineerXp(double amount) {
        amount = Utils.round(amount, 2);
        engineerXp += amount;
        verifyForPoint(EngineerSkillData.getInstance().name);
    }

    public void writeXpGained(String skill, double amount) {
        tmp_amount += amount;
        player.displayClientMessage(new TextComponent("You have get " + tmp_amount + " xp for the " + skill + " skill, you now have " + CompetenciesDB.getSkillXp(uuid, skill)), true);
        message_time_left = 80;
    }

    public void writePointGained(String skill, double amount) {
        tmp_amount += amount;
        player.displayClientMessage(new TextComponent("You have get " + tmp_amount + " point(s) for the " + skill + " skill, you now have " + CompetenciesDB.getSkillPoints(uuid, skill)), true);
        message_time_left = 80;
    }

    public void tick() {
        if (message_time_left > 0) {
            message_time_left --;
        } else {
            tmp_amount = 0;
        }
    }

    public void syncWithDatabase() {
        minerXp = CompetenciesDB.getSkillXp(uuid, MinerSkillData.getInstance().name);
        farmerXp = CompetenciesDB.getSkillXp(uuid, FarmerSkillData.getInstance().name);
        woodcutterXp = CompetenciesDB.getSkillXp(uuid, WoodcutterSkillData.getInstance().name);
        engineerXp = CompetenciesDB.getSkillXp(uuid, EngineerSkillData.getInstance().name);

        minerPoints = CompetenciesDB.getSkillPoints(uuid, MinerSkillData.getInstance().name);
        farmerPoints = CompetenciesDB.getSkillPoints(uuid, FarmerSkillData.getInstance().name);
        woodcutterPoints = CompetenciesDB.getSkillPoints(uuid, WoodcutterSkillData.getInstance().name);
        engineerPoints = CompetenciesDB.getSkillPoints(uuid, EngineerSkillData.getInstance().name);

        is_unlocked_miner_competencies.clear();
        for (String competency : MinerSkillData.getInstance().competency.name) {
            is_unlocked_miner_competencies.add(CompetenciesDB.getCompetency(uuid, MinerSkillData.getInstance().name, competency));
        }

        is_unlocked_farmer_competencies.clear();
        for (String competency : FarmerSkillData.getInstance().competency.name) {
            is_unlocked_farmer_competencies.add(CompetenciesDB.getCompetency(uuid, FarmerSkillData.getInstance().name, competency));
        }

        is_unlocked_engineer_competencies.clear();
        for (String competency : EngineerSkillData.getInstance().competency.name) {
            is_unlocked_engineer_competencies.add(CompetenciesDB.getCompetency(uuid, EngineerSkillData.getInstance().name, competency));
        }

        is_unlocked_woodcutter_competencies.clear();
        for (String competency : WoodcutterSkillData.getInstance().competency.name) {
            is_unlocked_woodcutter_competencies.add(CompetenciesDB.getCompetency(uuid, WoodcutterSkillData.getInstance().name, competency));
        }
    }
}
