package com.st0x0ef.won_skills;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Competency {
    public final List<String> name;
    public final List<Integer> price;
    public List<String> banned_item_command;
    public final List<MobEffect> mob_effects;
    
    public Competency(List<? extends String> competency) {
        name = new ArrayList<>();
        price = new ArrayList<>();
        banned_item_command = new ArrayList<>();
        mob_effects = new ArrayList<>();

        for (String string : competency) {
            String[] competencies = string.split(";", 0);

            for (String s : competencies) {
                if (s.contains("banned-items-commands:")) {
                    banned_item_command = Arrays.stream(string.split(", ")).toList();
                }

                String[] words = s.split(" ", 0);

                for (int n = 0; n < words.length; n++) {
                    if (Objects.equals(words[n], "name:")) {
                        name.add(words[n + 1]);
                    } else if (Objects.equals(words[n], "price:")) {
                        price.add(Integer.parseInt(words[n + 1]));
                    } else if (Objects.equals(s, "effects:")) {
                        for (int x = 1; x < words.length; x++) {
                            mob_effects.add(Registry.MOB_EFFECT.get(ResourceLocation.tryParse(words[x])));
                        }
                    }
                }
            }
        }
    }
}
