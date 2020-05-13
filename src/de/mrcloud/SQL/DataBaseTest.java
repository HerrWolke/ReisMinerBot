package de.mrcloud.SQL;

import de.mrcloud.main.STATIC;

import java.sql.*;

public class DataBaseTest {

    public static Connection mariaDB() {
        Connection conn = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(STATIC.DB_CONNECT_URL_RASP, "root", STATIC.DB_PW);
            return conn;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("Error while connecting to DB");
            return null;

        }
    }

    public static void main(String[] args) {
        {
            mariaDB();
        }
    }
}
