package com.st0x0ef.won_skills.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.st0x0ef.won_skills.WoNSkills;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SkillsScreen extends Screen implements MenuAccess<SkillsMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(WoNSkills.MODID, "textures/background.png");
    private final SkillsMenu menu;

    public SkillsScreen(SkillsMenu menu, Inventory inventory, Component title) {
        super(title);
        this.menu = menu;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBg(poseStack, mouseX, mouseY, partialTick);
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    protected void renderBg(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        this.font.draw(poseStack, menu.data.name, 1800 + (150 - 2 * menu.data.name.length()), 100, -1);
    }

    @Override
    public SkillsMenu getMenu() {
        return menu;
    }
}
