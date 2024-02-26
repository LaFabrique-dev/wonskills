package com.st0x0ef.won_skills.database;

import com.st0x0ef.won_skills.config.ServerConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@OnlyIn(Dist.DEDICATED_SERVER)
public class Database {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection != null) return connection;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String databaseUrl = ServerConfig.DATABASE_URL.get();
        String databaseName = ServerConfig.DATABASE_NAME.get();
        String databasePasword = ServerConfig.DATABASE_PASSWORD.get();

        connection = DriverManager.getConnection("jdbc:mysql://" + databaseUrl + "/" + databaseName, databaseName, databasePasword);

        return connection;
    }
}
