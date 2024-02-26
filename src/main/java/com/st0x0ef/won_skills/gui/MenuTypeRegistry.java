package com.st0x0ef.won_skills.gui;

import com.st0x0ef.won_skills.WoNSkills;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class MenuTypeRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, WoNSkills.MODID);

    public static final RegistryObject<MenuType<SkillsMenu>> SKILLS_MENU = MENUS.register("skills_menu", () -> IForgeMenuType.create(SkillsMenu::new));
}
