package com.st0x0ef.won_skills.event;

import com.st0x0ef.won_skills.SkillsManager;
import com.st0x0ef.won_skills.WoNSkills;
import com.st0x0ef.won_skills.command.SkillsDataCommand;
import com.st0x0ef.won_skills.skillsData.PlayerData;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = WoNSkills.MODID)
public class CommonEvent {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        new SkillsDataCommand(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onTick(TickEvent event) {
        for (PlayerData player : SkillsManager.playersSkills) {
            player.tick();
        }
    }
}
