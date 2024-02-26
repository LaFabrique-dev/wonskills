package com.st0x0ef.won_skills.database;

import com.st0x0ef.won_skills.SkillsManager;
import com.st0x0ef.won_skills.Utils;
import com.st0x0ef.won_skills.skillsData.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@OnlyIn(Dist.DEDICATED_SERVER)
public class CompetenciesDB {
    public static void loadDatabase() {
        try {
            Statement statement = Database.getConnection().createStatement();
            StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS player_skills(uuid CHAR(36) primary key, player_name TEXT");

            for (SkillData skill : SkillsManager.skills) {
                sql.append(", ").append(skill.name).append("XP DOUBLE");
            }

            for (SkillData skill : SkillsManager.skills) {
                sql.append(", ").append(skill.name).append("Points INT");
            }

            for (SkillData skill : SkillsManager.skills) {
                for (String competencies : skill.competency.name) {
                    sql.append(", ").append(skill.name).append("_").append(competencies).append(" INT");
                }
            }



            sql.append(")");

            statement.execute(sql.toString());
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean verifyIfNewPlayer(String uuid) {
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM player_skills WHERE uuid = '" + uuid + "'");
            if (!result.next()) {
                statement.close();
                return true;
            }

            statement.close();
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void createPlayerSkills(String uuid) {
        try {
            StringBuilder sql = new StringBuilder("INSERT INTO player_skills(uuid, player_name");
            int parameterNumber = 0;

            for (SkillData skill : SkillsManager.skills) {
                sql.append(", ").append(skill.name).append("XP");
                parameterNumber++;
            }

            for (SkillData skill : SkillsManager.skills) {
                sql.append(", ").append(skill.name).append("Points");
                parameterNumber++;
            }

            for (SkillData skill : SkillsManager.skills) {
                for (String competencies : skill.competency.name) {
                    sql.append(", ").append(skill.name).append("_").append(competencies);
                    parameterNumber++;
                }
            }


            sql.append(") VALUES (?, ?");
            sql.append(", ?".repeat(Math.max(0, parameterNumber)));
            sql.append(")");

            PreparedStatement statement = Database.getConnection().prepareStatement(sql.toString());
            statement.setString(1, uuid);
            statement.setString(2, SkillsManager.getPlayerSkills(uuid).name);

            for (int i = 3; i < SkillsManager.skills.size() + 3; i++) {
                statement.setDouble(i, 0);
            }

            for (int i = SkillsManager.skills.size() + 3; i < parameterNumber + 3; i++) {
                statement.setInt(i, 0);
            }

            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getSkillXp(String uuid, String skill) {
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT " + skill + "XP FROM player_skills WHERE uuid = '" + uuid + "'");
            double xp = 0f;
            if (result.next()) {
                xp = result.getDouble(skill+"XP");
            }
            statement.close();
            return xp;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getSkillPoints(String uuid, String skill) {
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT " + skill + "Points FROM player_skills WHERE uuid = '" + uuid + "'");
            int points = 0;
            if (result.next()) {
                points = result.getInt(skill+"Points");
            }
            statement.close();
            return points;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void setSkillXp(String uuid, String skill, double amount) {
        try {
            amount = Utils.round(amount, 2);
            PreparedStatement statement = Database.getConnection().prepareStatement("UPDATE player_skills SET " + skill + "XP = " + amount + " WHERE uuid = '" + uuid + "'");
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addSkillXp(String uuid, String skill, double amount) {
        setSkillXp(uuid, skill, getSkillXp(uuid, skill) + amount);
    }

    public static void setSkillPoints(String uuid, String skill, int amount) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("UPDATE player_skills SET " + skill + "Points = " + amount + " WHERE uuid = '" + uuid + "'");
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setCompetency(String uuid, String skill, String competency, boolean unlocked) {
        setCompetency(uuid, skill, competency, unlocked ? 1 : 0);
    }

    public static void setCompetency(String uuid, String skill, String competency, int unlocked) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("UPDATE player_skills SET " + skill + "_" + competency + " = " + unlocked + " WHERE uuid = '" + uuid + "'");
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean getCompetency(String uuid, String skill, String competency) {
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT " + skill + "_" + competency + " FROM player_skills WHERE uuid = '" + uuid + "'");
            boolean unlocked = false;
            if (result.next()) {
                unlocked = result.getBoolean(skill + "_" + competency);
            }
            statement.close();
            return unlocked;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void addSkillPoints(String uuid, String skill, int amount) {
        setSkillPoints(uuid, skill, getSkillPoints(uuid, skill) + amount);
    }


    public static void closeDatabase() {
        try { Database.getConnection().close(); }
        catch (SQLException e) { e.printStackTrace(); }
    }
}
