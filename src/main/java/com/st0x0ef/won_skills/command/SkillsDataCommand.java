package com.st0x0ef.won_skills.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.st0x0ef.won_skills.CompetenciesManager;
import com.st0x0ef.won_skills.SkillsManager;
import com.st0x0ef.won_skills.database.CompetenciesDB;
import com.st0x0ef.won_skills.gui.SkillsMenu;
import com.st0x0ef.won_skills.skillsData.*;
import io.netty.buffer.Unpooled;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkHooks;

public class SkillsDataCommand {
    public SkillsDataCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("skills")
                .executes(context -> openSkillsScreen(context.getSource()))
                .then(Commands.literal("get")
                        .then(Commands.literal("xp")
                                .then(Commands.literal("miner")
                                        .executes(context -> getXp(context.getSource(), MinerSkillData.getInstance()))
                                        .then(Commands.argument("player", EntityArgument.player()).requires(player -> player.hasPermission(2)).executes(
                                                context -> getXp(context.getSource(), MinerSkillData.getInstance(), EntityArgument.getPlayer(context, "player")))))
                                .then(Commands.literal("woodcutter")
                                        .executes(context -> getXp(context.getSource(), WoodcutterSkillData.getInstance()))
                                        .then(Commands.argument("player", EntityArgument.player()).requires(player -> player.hasPermission(2)).executes(
                                                context -> getXp(context.getSource(), WoodcutterSkillData.getInstance(), EntityArgument.getPlayer(context, "player")))))
                                .then(Commands.literal("engineer")
                                        .executes(context -> getXp(context.getSource(), EngineerSkillData.getInstance()))
                                        .then(Commands.argument("player", EntityArgument.player()).requires(player -> player.hasPermission(2)).executes(
                                                context -> getXp(context.getSource(), EngineerSkillData.getInstance(), EntityArgument.getPlayer(context, "player")))))
                                .then(Commands.literal("farmer")
                                        .executes(context -> getXp(context.getSource(), FarmerSkillData.getInstance()))
                                        .then(Commands.argument("player", EntityArgument.player()).requires(player -> player.hasPermission(2)).executes(
                                                context -> getXp(context.getSource(), FarmerSkillData.getInstance(), EntityArgument.getPlayer(context, "player"))))))

                        .then(Commands.literal("points")
                                .then(Commands.literal("miner")
                                        .executes(context -> getPoints(context.getSource(), MinerSkillData.getInstance()))
                                        .then(Commands.argument("player", EntityArgument.player()).executes(
                                                context -> getPoints(context.getSource(), MinerSkillData.getInstance(), EntityArgument.getPlayer(context, "player")))))
                                .then(Commands.literal("woodcutter")
                                        .executes(context -> getPoints(context.getSource(), WoodcutterSkillData.getInstance()))
                                        .then(Commands.argument("player", EntityArgument.player()).executes(
                                                context -> getPoints(context.getSource(), WoodcutterSkillData.getInstance(), EntityArgument.getPlayer(context, "player")))))
                                .then(Commands.literal("engineer")
                                        .executes(context -> getPoints(context.getSource(), EngineerSkillData.getInstance()))
                                        .then(Commands.argument("player", EntityArgument.player()).executes(
                                                context -> getPoints(context.getSource(), EngineerSkillData.getInstance(), EntityArgument.getPlayer(context, "player")))))
                                .then(Commands.literal("farmer")
                                        .executes(context -> getPoints(context.getSource(), FarmerSkillData.getInstance()))
                                        .then(Commands.argument("player", EntityArgument.player()).executes(
                                                context -> getPoints(context.getSource(), FarmerSkillData.getInstance(), EntityArgument.getPlayer(context, "player")))))))

                .then(Commands.literal("buy")
                        .then(Commands.literal("miner")
                                .then(Commands.argument("competency", StringArgumentType.string()).executes(
                                        context -> buyCompetency(context.getSource(), MinerSkillData.getInstance(), StringArgumentType.getString(context, "competency")))))
                        .then(Commands.literal("woodcutter")
                                .then(Commands.argument("competency", StringArgumentType.string()).executes(
                                        context -> buyCompetency(context.getSource(), WoodcutterSkillData.getInstance(), StringArgumentType.getString(context, "competency")))))
                        .then(Commands.literal("engineer")
                                .then(Commands.argument("competency", StringArgumentType.string()).executes(
                                        context -> buyCompetency(context.getSource(), EngineerSkillData.getInstance(), StringArgumentType.getString(context, "competency")))))
                        .then(Commands.literal("farmer")
                                .then(Commands.argument("competency", StringArgumentType.string()).executes(
                                        context -> buyCompetency(context.getSource(), FarmerSkillData.getInstance(), StringArgumentType.getString(context, "competency"))))))

                .then(Commands.literal("set").requires(player -> player.hasPermission(2))
                        .then(Commands.literal("xp")
                                .then(Commands.literal("miner")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg()).executes(
                                                        context -> setXp(context.getSource(), MinerSkillData.getInstance(), DoubleArgumentType.getDouble(context, "amount"), EntityArgument.getPlayer(context, "player"))))))
                                .then(Commands.literal("woodcutter")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg()).executes(
                                                        context -> setXp(context.getSource(), WoodcutterSkillData.getInstance(), DoubleArgumentType.getDouble(context, "amount"), EntityArgument.getPlayer(context, "player"))))))
                                .then(Commands.literal("engineer")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg()).executes(
                                                        context -> setXp(context.getSource(), EngineerSkillData.getInstance(), DoubleArgumentType.getDouble(context, "amount"), EntityArgument.getPlayer(context, "player"))))))
                                .then(Commands.literal("farmer")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg()).executes(
                                                        context -> setXp(context.getSource(), FarmerSkillData.getInstance(), DoubleArgumentType.getDouble(context, "amount"), EntityArgument.getPlayer(context, "player")))))))


                        .then(Commands.literal("points")
                                .then(Commands.literal("miner")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", IntegerArgumentType.integer()).executes(
                                                        context -> setPoint(context.getSource(), MinerSkillData.getInstance(), IntegerArgumentType.getInteger(context, "amount"), EntityArgument.getPlayer(context, "player"))))))
                                .then(Commands.literal("woodcutter")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", IntegerArgumentType.integer()).executes(
                                                        context -> setPoint(context.getSource(), WoodcutterSkillData.getInstance(), IntegerArgumentType.getInteger(context, "amount"), EntityArgument.getPlayer(context, "player"))))))
                                .then(Commands.literal("engineer")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", IntegerArgumentType.integer()).executes(
                                                        context -> setPoint(context.getSource(), EngineerSkillData.getInstance(), IntegerArgumentType.getInteger(context, "amount"), EntityArgument.getPlayer(context, "player"))))))
                                .then(Commands.literal("farmer")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", IntegerArgumentType.integer()).executes(
                                                        context -> setPoint(context.getSource(), FarmerSkillData.getInstance(), IntegerArgumentType.getInteger(context, "amount"), EntityArgument.getPlayer(context, "player")))))))

                        .then(Commands.literal("competencies")
                                .then(Commands.literal("miner")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("competency", StringArgumentType.string())
                                                        .then(Commands.argument("unlocked", BoolArgumentType.bool()).executes(
                                                                context ->  setCompetency(context.getSource(), EntityArgument.getPlayer(context, "player"), MinerSkillData.getInstance(), StringArgumentType.getString(context, "competency"), BoolArgumentType.getBool(context, "unlocked")))))))
                                .then(Commands.literal("farmer")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("competency", StringArgumentType.string())
                                                        .then(Commands.argument("unlocked", BoolArgumentType.bool()).executes(
                                                                context ->  setCompetency(context.getSource(), EntityArgument.getPlayer(context, "player"), FarmerSkillData.getInstance(), StringArgumentType.getString(context, "competency"), BoolArgumentType.getBool(context, "unlocked")))))))
                                .then(Commands.literal("engineer")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("competency", StringArgumentType.string())
                                                        .then(Commands.argument("unlocked", BoolArgumentType.bool()).executes(
                                                                context ->  setCompetency(context.getSource(), EntityArgument.getPlayer(context, "player"), EngineerSkillData.getInstance(), StringArgumentType.getString(context, "competency"), BoolArgumentType.getBool(context, "unlocked")))))))
                                .then(Commands.literal("woodcutter")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("competency", StringArgumentType.string())
                                                        .then(Commands.argument("unlocked", BoolArgumentType.bool()).executes(
                                                                context ->  setCompetency(context.getSource(), EntityArgument.getPlayer(context, "player"), WoodcutterSkillData.getInstance(), StringArgumentType.getString(context, "competency"), BoolArgumentType.getBool(context, "unlocked")))))))))

                .then(Commands.literal("add").requires(player -> player.hasPermission(2))
                        .then(Commands.literal("xp")
                                .then(Commands.literal("miner")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg()).executes(
                                                        context -> addXp(context.getSource(), MinerSkillData.getInstance(), DoubleArgumentType.getDouble(context, "amount"), EntityArgument.getPlayer(context, "player"))))))
                                .then(Commands.literal("woodcutter")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg()).executes(
                                                        context -> addXp(context.getSource(), WoodcutterSkillData.getInstance(), DoubleArgumentType.getDouble(context, "amount"), EntityArgument.getPlayer(context, "player"))))))
                                .then(Commands.literal("engineer")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg()).executes(
                                                        context -> addXp(context.getSource(), EngineerSkillData.getInstance(), DoubleArgumentType.getDouble(context, "amount"), EntityArgument.getPlayer(context, "player"))))))
                                .then(Commands.literal("farmer")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg()).executes(
                                                        context -> addXp(context.getSource(), FarmerSkillData.getInstance(), DoubleArgumentType.getDouble(context, "amount"), EntityArgument.getPlayer(context, "player")))))))


                        .then(Commands.literal("points")
                                .then(Commands.literal("miner")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", IntegerArgumentType.integer()).executes(
                                                        context -> addPoint(context.getSource(), MinerSkillData.getInstance(), IntegerArgumentType.getInteger(context, "amount"), EntityArgument.getPlayer(context, "player"))))))
                                .then(Commands.literal("woodcutter")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", IntegerArgumentType.integer()).executes(
                                                        context -> addPoint(context.getSource(), WoodcutterSkillData.getInstance(), IntegerArgumentType.getInteger(context, "amount"), EntityArgument.getPlayer(context, "player"))))))
                                .then(Commands.literal("engineer")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", IntegerArgumentType.integer()).executes(
                                                        context -> addPoint(context.getSource(), EngineerSkillData.getInstance(), IntegerArgumentType.getInteger(context, "amount"), EntityArgument.getPlayer(context, "player"))))))
                                .then(Commands.literal("farmer")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("amount", IntegerArgumentType.integer()).executes(
                                                        context -> addPoint(context.getSource(), FarmerSkillData.getInstance(), IntegerArgumentType.getInteger(context, "amount"), EntityArgument.getPlayer(context, "player")))))))));

    }

    private int getXp(CommandSourceStack source, SkillData skill) throws CommandSyntaxException {
        return getXp(source, skill, source.getPlayerOrException());
    }

    private int getXp(CommandSourceStack source, SkillData skill, Player player) {
        String uuid = player.getStringUUID();
        double xp = CompetenciesDB.getSkillXp(uuid, skill.name);

        if (player.isLocalPlayer()) {
            source.sendSuccess(new TextComponent("You have " + xp + " xp for the " + skill.name + " skill"), false);
            return 1;
        } else {
            source.sendSuccess(new TextComponent("The player " + player.getName().getString() + " have " + xp + " xp for the " + skill.name + " skill"), false);
            return 1;
        }
    }

    private int getPoints(CommandSourceStack source, SkillData skill) throws CommandSyntaxException {
        return getPoints(source, skill, source.getPlayerOrException());
    }

    private int getPoints(CommandSourceStack source, SkillData skill, Player player) {
        String uuid = player.getStringUUID();
        int points = CompetenciesDB.getSkillPoints(uuid, skill.name);

        if (player.isLocalPlayer()) {
            if (points > 1) {
                source.sendSuccess(new TextComponent("You have " + points + " points for the " + skill.name + " skill"), false);
            }
            else {
                source.sendSuccess(new TextComponent("You have " + points + " point for the " + skill.name + " skill"), false);
            }
        }
        else {
            if (points > 1) {
                source.sendSuccess(new TextComponent("The player " + player.getName().getString() + " have " + points + " points for the " + skill.name + " skill"), false);
            }
            else {
                source.sendSuccess(new TextComponent("The player " + player.getName().getString() + " have " + points + " point for the " + skill.name + " skill"), false);
            }
        }

        return 1;
    }

    private int setXp(CommandSourceStack source, SkillData skill, double amount, Player player) {
        String uuid = player.getStringUUID();
        SkillsManager.setSkillXp(uuid, skill.name, amount);

        source.sendSuccess(new TextComponent("The player " + player.getName().getString() + " now has " + amount + " xp for the " + skill.name + " skill"), false);

        return 1;
    }

    private int setPoint(CommandSourceStack source, SkillData skill, int amount, Player player) {
        String uuid = player.getStringUUID();
        SkillsManager.setSkillPoints(uuid, skill.name, amount);

        if (amount > 1) {
            source.sendSuccess(new TextComponent("The player " + player.getName().getString() + " now has " + amount + " points for the " + skill.name + " skill"), false);
        }
        else {
            source.sendSuccess(new TextComponent("The player " + player.getName().getString() + " now has " + amount + " point for the " + skill.name + " skill"), false);
        }

        return 1;
    }

    private int setCompetency(CommandSourceStack source, Player player, SkillData skill, String competency, Boolean unlocked) {
        String uuid = player.getStringUUID();
        CompetenciesManager.setCompetency(uuid, skill.name, competency, unlocked);

        if (unlocked) {
            source.sendSuccess(new TextComponent("The player " + player.getName().getString() + " now has the competency " + competency + " for the " + skill.name + " skill"), false);
        }

        return 1;
    }

    private int addXp(CommandSourceStack source, SkillData skill, double amount, Player player) {
        String uuid = player.getStringUUID();
        SkillsManager.addSkillXp(uuid, skill.name, amount);
        double xp = CompetenciesDB.getSkillXp(uuid, skill.name);

        source.sendSuccess(new TextComponent("The player " + player.getName().getString() + " now has " + xp + " xp for the " + skill.name + " skill"), false);

        return 1;
    }

    private int addPoint(CommandSourceStack source, SkillData skill, int amount, Player player) {
        String uuid = player.getStringUUID();
        CompetenciesDB.addSkillPoints(uuid, skill.name, amount);
        int points = CompetenciesDB.getSkillPoints(uuid, skill.name);

        if (amount > 1) {
            source.sendSuccess(new TextComponent("The player " + player.getName().getString() + " now has " + points + " points for the " + skill.name + " skill"), false);
        }
        else {
            source.sendSuccess(new TextComponent("The player " + player.getName().getString() + " now has " + points + " point for the " + skill.name + " skill"), false);
        }

        return 1;
    }

    private int buyCompetency(CommandSourceStack source, SkillData skill, String competency) throws CommandSyntaxException {
        String uuid = source.getPlayerOrException().getStringUUID();
        int price = skill.competency.price.get(skill.competency.name.indexOf(competency));

        if (price >= CompetenciesDB.getSkillPoints(uuid, skill.name)) {
            CompetenciesDB.addSkillPoints(uuid, skill.name, -price);
            CompetenciesDB.setCompetency(uuid, skill.name, competency, true);
            source.sendSuccess(new TextComponent("You have get the " + competency + " competency"), false);
            return 1;
        }

        source.sendSuccess(new TextComponent("You don't have enough point to buy this competency"), false);
        return 1;
    }

    private int openSkillsScreen(CommandSourceStack source) {
        try {
            Player player = source.getPlayerOrException();
            if (player instanceof ServerPlayer serverPlayer) {
                NetworkHooks.openGui(serverPlayer, new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TextComponent("Skills of " + serverPlayer.getDisplayName());
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
                        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
                        return new SkillsMenu(id, inv, buffer);
                    }
                });

                return 1;
            }
            return -1;
        }
        catch (CommandSyntaxException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
