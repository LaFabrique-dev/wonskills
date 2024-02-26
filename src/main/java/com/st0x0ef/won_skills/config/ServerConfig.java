package com.st0x0ef.won_skills.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<String> DATABASE_URL;
    public static final ForgeConfigSpec.ConfigValue<String> DATABASE_NAME;
    public static final ForgeConfigSpec.ConfigValue<String> DATABASE_PASSWORD;

    static {
        BUILDER.push("Database");

            DATABASE_URL = BUILDER.comment("URL of the competencies database").define("Database URL", "167.114.173.191:3306");
            DATABASE_NAME = BUILDER.comment("Name of the competencies database").define("Database Name", "mc144757");
            DATABASE_PASSWORD = BUILDER.comment("Password of the competencies database").define("Database Password", "a8d65d5888");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
