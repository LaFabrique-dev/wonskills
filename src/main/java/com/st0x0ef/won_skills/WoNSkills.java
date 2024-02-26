package com.st0x0ef.won_skills;

import com.mojang.logging.LogUtils;
import com.st0x0ef.won_skills.config.*;
import com.st0x0ef.won_skills.database.CompetenciesDB;
import com.st0x0ef.won_skills.gui.MenuTypeRegistry;
import com.st0x0ef.won_skills.gui.SkillsScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod(WoNSkills.MODID)
public class WoNSkills
{
    public static final String MODID = "won_skills";
    private static final Logger LOGGER = LogUtils.getLogger();

    public WoNSkills() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        MenuTypeRegistry.MENUS.register(bus);

        bus.addListener(this::clientSetup);
        bus.addListener(this::serverSetup);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC, "won-skills-database.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MinerConfig.SPEC, "won-skills-miner.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, FarmerConfig.SPEC, "won-skills-farmer.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, WoodcutterConfig.SPEC, "won-skills-woodcutter.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, EngineerConfig.SPEC, "won-skills-engineer.toml");
    }

    private void clientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(MenuTypeRegistry.SKILLS_MENU.get(), SkillsScreen::new);
    }

    private void serverSetup(FMLDedicatedServerSetupEvent event) {
        SkillsManager.registerSkills();
        CompetenciesDB.loadDatabase();
        LOGGER.info("Successfully loaded WoN Skills");
    }

    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        CompetenciesDB.closeDatabase();
        LOGGER.info("Successfully closed WoN Skills");
    }
}
