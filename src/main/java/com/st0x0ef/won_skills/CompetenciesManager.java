package com.st0x0ef.won_skills;

import com.st0x0ef.won_skills.database.CompetenciesDB;
import com.st0x0ef.won_skills.skillsData.*;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CompetenciesManager {
    public static final Integer DEFAULT_COMPETENCIES_XP_POINT = 1000;

    @OnlyIn(Dist.DEDICATED_SERVER)
    public static void setCompetency(String uuid, String skill, String competency, Boolean unlocked) {
        PlayerData player = SkillsManager.getPlayerSkills(uuid);
        if (player != null) {
            if (MinerSkillData.getInstance().is(skill)) {
                player.is_unlocked_miner_competencies.set(MinerSkillData.getInstance().competency.name.indexOf(competency), unlocked);
            }

            else if (FarmerSkillData.getInstance().is(skill)) {
                player.is_unlocked_farmer_competencies.set(FarmerSkillData.getInstance().competency.name.indexOf(competency), unlocked);
            }

            else if (EngineerSkillData.getInstance().is(skill)) {
                player.is_unlocked_engineer_competencies.set(EngineerSkillData.getInstance().competency.name.indexOf(competency), unlocked);
            }

            else if (WoodcutterSkillData.getInstance().is(skill)) {
                player.is_unlocked_woodcutter_competencies.set(WoodcutterSkillData.getInstance().competency.name.indexOf(competency), unlocked);
            }

            CompetenciesDB.setCompetency(uuid, skill, competency, unlocked);

            MinecraftServer server = player.player.getServer();
            if (server != null) {
                for (String command : MinerSkillData.getInstance().competency.banned_item_command) {
                    command = command.replace("$p", player.name);
                    server.getCommands().performCommand(server.createCommandSourceStack(), command);
                }

                for (String command : FarmerSkillData.getInstance().competency.banned_item_command) {
                    command = command.replace("$p", player.name);
                    server.getCommands().performCommand(server.createCommandSourceStack(), command);
                }

                for (String command : EngineerSkillData.getInstance().competency.banned_item_command) {
                    command = command.replace("$p", player.name);
                    server.getCommands().performCommand(server.createCommandSourceStack(), command);
                }

                for (String command : WoodcutterSkillData.getInstance().competency.banned_item_command) {
                    command = command.replace("$p", player.name);
                    server.getCommands().performCommand(server.createCommandSourceStack(), command);
                }
            }
        }
    }
}