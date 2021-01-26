package ru.geekbrains.java_two.chat.server.core;

import org.sqlite.JDBC;

import java.sql.*;

public class SqlClient {

    private static Connection connection;
    private static Statement statement;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(JDBC.PREFIX + "chat-server/clients.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getNickname(String login, String password) {
        // select nickname from clients where login='p.stora' and password='123'
        String query = String.format("select nickname from clients where login='%s' and password='%s'",
                login, password);
        try (ResultSet set = statement.executeQuery(query)) {
            if (set.next()) {
                return set.getString("nickname");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean changeNickname (String currentNickname, String newNickname) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("UPDATE clients SET nickname = ? WHERE nickname = ?");
        preparedStatement.setString(1, currentNickname);
        preparedStatement.setString(2, newNickname);
        return preparedStatement.executeUpdate() > 0;
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
