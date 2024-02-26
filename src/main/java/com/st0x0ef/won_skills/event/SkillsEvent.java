package com.st0x0ef.won_skills.event;

import com.st0x0ef.won_skills.SkillsManager;
import com.st0x0ef.won_skills.WoNSkills;
import com.st0x0ef.won_skills.config.FarmerConfig;
import com.st0x0ef.won_skills.skillsData.EngineerSkillData;
import com.st0x0ef.won_skills.skillsData.FarmerSkillData;
import com.st0x0ef.won_skills.skillsData.MinerSkillData;
import com.st0x0ef.won_skills.skillsData.WoodcutterSkillData;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = WoNSkills.MODID, value = Dist.DEDICATED_SERVER)
public class SkillsEvent {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onBlockDestroyed(BlockEvent.BreakEvent event) {
        if (event.isCanceled()) return;

        String uuid = event.getPlayer().getStringUUID();
        Item item = event.getState().getBlock().asItem();

        Objects.requireNonNull(SkillsManager.getPlayerSkills(uuid)).giveEffects();

        // MINER

        if (MinerSkillData.getInstance().items_breaked.contains(item)) {
            SkillsManager.addSkillXp(uuid, MinerSkillData.getInstance().name, MinerSkillData.getInstance().xp_breaked.get(MinerSkillData.getInstance().items_breaked.indexOf(item)));
        }

        // FARMER

        if (FarmerSkillData.getInstance().items_breaked.contains(item)) {
            SkillsManager.addSkillXp(uuid, FarmerSkillData.getInstance().name, FarmerSkillData.getInstance().xp_breaked.get(FarmerSkillData.getInstance().items_breaked.indexOf(item)));
        }

        // WOODCUTTER

        if (WoodcutterSkillData.getInstance().items_breaked.contains(item)) {
            SkillsManager.addSkillXp(uuid, WoodcutterSkillData.getInstance().name, WoodcutterSkillData.getInstance().xp_breaked.get(WoodcutterSkillData.getInstance().items_breaked.indexOf(item)));
        }

        // ENGINEER

        if (EngineerSkillData.getInstance().items_breaked.contains(item)) {
            SkillsManager.addSkillXp(uuid, EngineerSkillData.getInstance().name, EngineerSkillData.getInstance().xp_breaked.get(EngineerSkillData.getInstance().items_breaked.indexOf(item)));
        }
    }

    @SubscribeEvent
    public static void onSmeltItem(PlayerEvent.ItemSmeltedEvent event) {
        String uuid = event.getPlayer().getStringUUID();
        Item item = event.getSmelting().getItem();

        // MINER

        if (MinerSkillData.getInstance().items_smelted.contains(item)) {
            SkillsManager.addSkillXp(uuid, MinerSkillData.getInstance().name, MinerSkillData.getInstance().xp_smelted.get(MinerSkillData.getInstance().items_smelted.indexOf(item)));
        }

        // FARMER

        if (FarmerSkillData.getInstance().items_smelted.contains(item)) {
            SkillsManager.addSkillXp(uuid, FarmerSkillData.getInstance().name, FarmerSkillData.getInstance().xp_smelted.get(FarmerSkillData.getInstance().items_smelted.indexOf(item)));
        }

        // WOODCUTTER

        if (WoodcutterSkillData.getInstance().items_smelted.contains(item)) {
            SkillsManager.addSkillXp(uuid, WoodcutterSkillData.getInstance().name, WoodcutterSkillData.getInstance().xp_smelted.get(WoodcutterSkillData.getInstance().items_smelted.indexOf(item)));
        }

        // ENGINEER

        if (EngineerSkillData.getInstance().items_smelted.contains(item)) {
            SkillsManager.addSkillXp(uuid, EngineerSkillData.getInstance().name, EngineerSkillData.getInstance().xp_smelted.get(EngineerSkillData.getInstance().items_smelted.indexOf(item)));
        }
    }

    @SubscribeEvent
    public static void onBonemealCrop(BonemealEvent event) {
        String uuid = event.getPlayer().getStringUUID();
        SkillsManager.addSkillXp(uuid, FarmerSkillData.getInstance().name, FarmerConfig.FARMER_XP_BONEMEAL.get());
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        String uuid = event.getPlayer().getStringUUID();
        Item item = event.getCrafting().getItem();

        // MINER

        if (MinerSkillData.getInstance().items_crafted.contains(item)) {
            SkillsManager.addSkillXp(uuid, MinerSkillData.getInstance().name, MinerSkillData.getInstance().xp_crafted.get(MinerSkillData.getInstance().items_crafted.indexOf(item)));
        }

        // FARMER

        if (FarmerSkillData.getInstance().items_crafted.contains(item)) {
            SkillsManager.addSkillXp(uuid, FarmerSkillData.getInstance().name, FarmerSkillData.getInstance().xp_crafted.get(FarmerSkillData.getInstance().items_crafted.indexOf(item)));
        }

        // WOODCUTTER

        if (WoodcutterSkillData.getInstance().items_crafted.contains(item)) {
            SkillsManager.addSkillXp(uuid, WoodcutterSkillData.getInstance().name, WoodcutterSkillData.getInstance().xp_crafted.get(WoodcutterSkillData.getInstance().items_crafted.indexOf(item)));
        }

        // ENGINEER

        if (EngineerSkillData.getInstance().items_crafted.contains(item)) {
            SkillsManager.addSkillXp(uuid, EngineerSkillData.getInstance().name, EngineerSkillData.getInstance().xp_crafted.get(EngineerSkillData.getInstance().items_crafted.indexOf(item)));
        }
    }
}
