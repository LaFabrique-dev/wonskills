package com.st0x0ef.won_skills.gui;

import com.st0x0ef.won_skills.SkillsManager;
import com.st0x0ef.won_skills.skillsData.PlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class SkillsMenu extends AbstractContainerMenu {
    public PlayerData data;

    public SkillsMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(MenuTypeRegistry.SKILLS_MENU.get(), id);
        data = SkillsManager.getPlayerSkills(inv.player.getStringUUID());
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
