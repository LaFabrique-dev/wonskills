package com.st0x0ef.won_skills.skillsData;

import com.st0x0ef.won_skills.Competency;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SkillData {
    public String name;
    public List<Item> items_breaked;
    public List<? extends Double> xp_breaked;
    public List<Item> items_crafted;
    public List<? extends Double> xp_crafted;
    public List<Item> items_smelted;
    public List<? extends Double> xp_smelted;
    public Competency competency;

    protected void init(String name) {
        this.name = name;

        items_breaked = new ArrayList<>();
        items_crafted = new ArrayList<>();
        items_smelted = new ArrayList<>();
    }

    public Boolean is(String _name) {
        return Objects.equals(_name, name);
    }
}
