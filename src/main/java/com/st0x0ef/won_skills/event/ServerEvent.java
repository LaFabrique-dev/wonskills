package com.st0x0ef.won_skills.event;

import com.st0x0ef.won_skills.skillsData.PlayerData;
import com.st0x0ef.won_skills.WoNSkills;
import com.st0x0ef.won_skills.SkillsManager;
import com.st0x0ef.won_skills.database.CompetenciesDB;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WoNSkills.MODID, value = Dist.DEDICATED_SERVER)
public class ServerEvent {
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getStringUUID();

        PlayerData skills = new PlayerData(player);

        if (CompetenciesDB.verifyIfNewPlayer(uuid)) {
            CompetenciesDB.createPlayerSkills(uuid);
        }

        skills.syncWithDatabase();
    }

    @SubscribeEvent
    public static void onPlayerLeaves(PlayerEvent.PlayerLoggedOutEvent event) {
        String uuid = event.getPlayer().getStringUUID();
        SkillsManager.playersSkills.remove(SkillsManager.getPlayerSkills(uuid));
    }
}
