package com.st0x0ef.won_skills;

import com.mojang.logging.LogUtils;
import com.st0x0ef.won_skills.database.CompetenciesDB;
import com.st0x0ef.won_skills.skillsData.*;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SkillsManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final List<SkillData> skills = new ArrayList<>();
    public static final List<PlayerData> playersSkills = new ArrayList<>();
    
    public static void registerSkills() {
        skills.add(new MinerSkillData());
        skills.add(new FarmerSkillData());
        skills.add(new WoodcutterSkillData());
        skills.add(new EngineerSkillData());
    }

    public static void registerPlayer(PlayerData playerData) {
        playersSkills.add(playerData);
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    public static void addSkillXp(String uuid, String skill, double amount) {
        PlayerData playerSkills = getPlayerSkills(uuid);
        CompetenciesDB.addSkillXp(uuid, skill, amount);


        if (playerSkills != null) {
            playerSkills.writeXpGained(skill, amount);

            switch (skill) {
                case "miner":
                    playerSkills.addMinerXp(amount);
                    break;
                case "farmer":
                    playerSkills.addFarmerXp(amount);
                    break;
                case "woodcutter":
                    playerSkills.addWoodcutterXp(amount);
                    break;
                case "engineer":
                    playerSkills.addEngineerXp(amount);
                    break;
            }
        }
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    public static void addSkillPoints(String uuid, String skill, int amount) {
        PlayerData playerSkills = getPlayerSkills(uuid);
        CompetenciesDB.addSkillPoints(uuid, skill, amount);

        if (playerSkills != null) {
            int newAmount = CompetenciesDB.getSkillPoints(uuid, skill);

            playerSkills.writePointGained(skill, amount);

            if (MinerSkillData.getInstance().is(skill)) {
                playerSkills.minerPoints = newAmount;
            }

            else if (FarmerSkillData.getInstance().is(skill)) {
                playerSkills.farmerPoints = newAmount;
            }

            else if (WoodcutterSkillData.getInstance().is(skill)) {
                playerSkills.woodcutterPoints = newAmount;
            }

            else if (EngineerSkillData.getInstance().is(skill)) {
                playerSkills.engineerPoints = newAmount;
            }
        }
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    public static void setSkillXp(String uuid, String skill, double amount) {
        CompetenciesDB.setSkillXp(uuid, skill, amount);

        PlayerData playerSkills = getPlayerSkills(uuid);
        if (playerSkills != null) {
            if (MinerSkillData.getInstance().is(skill)) {
                playerSkills.minerXp = amount;
            }

            else if (FarmerSkillData.getInstance().is(skill)) {
                playerSkills.farmerXp = amount;
            }

            else if (WoodcutterSkillData.getInstance().is(skill)) {
                playerSkills.woodcutterXp = amount;
            }

            else if (EngineerSkillData.getInstance().is(skill)) {
                playerSkills.engineerXp = amount;
            }
        }
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    public static void setSkillPoints(String uuid, String skill, int amount) {
        CompetenciesDB.setSkillPoints(uuid, skill, amount);

        PlayerData playerSkills = getPlayerSkills(uuid);
        if (playerSkills != null) {
            if (MinerSkillData.getInstance().is(skill)) {
                playerSkills.minerPoints = amount;
            }

            else if (FarmerSkillData.getInstance().is(skill)) {
                playerSkills.farmerPoints = amount;
            }

            else if (WoodcutterSkillData.getInstance().is(skill)) {
                playerSkills.woodcutterPoints = amount;
            }

            else if (EngineerSkillData.getInstance().is(skill)) {
                playerSkills.engineerPoints = amount;
            }
        }
    }

    public static PlayerData getPlayerSkills(Player player) {
        if (player == null) {
            LOGGER.error("player can't be null");
            return null;
        }

        return getPlayerSkills(player.getStringUUID());
    }

    public static PlayerData getPlayerSkills(String uuid) {
        for (int i = 0; i < SkillsManager.playersSkills.size(); i++) {
            PlayerData playerSkills = SkillsManager.playersSkills.get(i);
            if (Objects.equals(playerSkills.uuid, uuid)) {
                return playerSkills;
            }
        }

        LOGGER.warn("There is no player with uuid " + uuid + " in the playersSkills list");
        return null;
    }
}
