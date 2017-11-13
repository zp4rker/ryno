package me.zp4rker.discord.ryno.db;

import me.zp4rker.discord.core.exception.ExceptionHandler;
import me.zp4rker.discord.core.logger.ZLogger;
import me.zp4rker.discord.ryno.Ryno;

import java.sql.*;

public class Database {

    private static Connection con = null;
    private static PreparedStatement statement = null;

    public static boolean status() {
        boolean connection;
        try {
            openConnection();
            connection = true;
        } catch (Exception e) {
            e.printStackTrace();
            connection = false;
        } finally {
            closeConnection();
        }
        return connection;
    }

    public static void openConnection() throws SQLException {
        if (con != null) return;
        String host = Ryno.Config.val("db-host");
        String name = Ryno.Config.val("db-name");
        String user = Ryno.Config.val("db-username");
        String pass = Ryno.Config.val("db-password");
        con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + name, user, pass);
    }

    public static void closeConnection() {
        try {
            if (con != null) {
                con.close();
                con = null;
            }
            if (statement != null) {
                statement.close();
                statement = null;
            }
        } catch (SQLException e) {
            ExceptionHandler.handleException("Database connection close", e);
        }
    }

    public static ResultSet query(String query, Object... args) throws SQLException {
        statement = con.prepareStatement(query);

        int i = 1;
        for (Object arg : args) {
            statement.setObject(i, arg);
            i++;
        }

        ZLogger.debug("Statement null: " + (statement == null));

        return statement.executeQuery();
    }

    public static void update(String query, Object... args) throws SQLException {
        statement = con.prepareStatement(query);

        int i = 1;
        for (Object arg : args) {
            statement.setObject(i, arg);
            i++;
        }

        statement.executeUpdate();
    }

    public static ResultSet query(String query) throws SQLException {
        statement = con.prepareStatement(query);

        return statement.executeQuery();
    }

}
