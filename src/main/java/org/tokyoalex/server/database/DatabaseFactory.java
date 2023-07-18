package org.tokyoalex.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.sql.DriverManager.println;

public class DatabaseFactory {

    private static Connection conn;

    public static void init()  {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'src/main/resources/hotel_schema.sql';",
                    "sa",
                    "");
        }
        catch (Exception e) {
                println(e.toString());
        }
    }

    public static Connection getConnection()   {
        return conn;
    }
}
