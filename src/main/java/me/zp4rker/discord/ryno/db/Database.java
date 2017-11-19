package me.zp4rker.discord.ryno.db;

import me.zp4rker.discord.core.exception.ExceptionHandler;
import me.zp4rker.discord.core.logger.ZLogger;
import me.zp4rker.discord.ryno.Ryno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static Connection con = null;
    private static List<PreparedStatement> statements = new ArrayList<>();

    public static boolean status() {
        boolean connection;
        try {
            openConnection();
            connection = true;
        } catch (Exception e) {
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
            if (!statements.isEmpty()) {
                for (PreparedStatement statement : statements) {
                    statement.close();
                }
                statements.clear();
            }
        } catch (SQLException e) {
            ExceptionHandler.handleException("Database connection close", e);
        }
    }

    public static ResultSet query(String query, Object... args) throws SQLException {
        PreparedStatement statement = con.prepareStatement(query);

        int i = 1;
        for (Object arg : args) {
            statement.setObject(i, arg);
            i++;
        }

        ZLogger.debug("Statement null: " + (statement == null));

        ResultSet resultSet = statement.executeQuery();
        statements.add(statement);

        return resultSet;
    }

    public static void update(String query, Object... args) throws SQLException {
        PreparedStatement statement = con.prepareStatement(query);

        int i = 1;
        for (Object arg : args) {
            statement.setObject(i, arg);
            i++;
        }

        statement.executeUpdate();
        statements.add(statement);
    }

    public static ResultSet query(String query) throws SQLException {
        PreparedStatement statement = con.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();
        statements.add(statement);

        return resultSet;
    }

}
