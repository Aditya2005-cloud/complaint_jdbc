package com.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String DEFAULT_URL =
            "jdbc:oracle:thin:@localhost:1521/XE";

    private static final String DEFAULT_USER =
            "system";

    private static final String DEFAULT_PASSWORD =
            "Oracle12345";

    public static Connection
    getConnection() throws Exception {

        String url =
                readConfig(
                        "DB_URL",
                        DEFAULT_URL);

        String user =
                readConfig(
                        "DB_USER",
                        DEFAULT_USER);

        String password =
                readConfig(
                        "DB_PASSWORD",
                        DEFAULT_PASSWORD);

        try {

            return DriverManager.getConnection(
                    url,
                    user,
                    password);

        } catch (SQLException e) {

            throw new SQLException(
                    buildHelpfulMessage(
                            e,
                            url,
                            user),
                    e.getSQLState(),
                    e.getErrorCode());
        }
    }

    public static String handleNull(
            String value) {

        return value == null
                ? "N/A"
                : value;
    }

    private static String readConfig(
            String key,
            String defaultValue) {

        String value =
                System.getenv(key);

        if (value == null ||
                value.isBlank()) {

            return defaultValue;
        }

        return value;
    }

    private static String buildHelpfulMessage(
            SQLException e,
            String url,
            String user) {

        String baseMessage =
                "Oracle connection failed for user '"
                        + user
                        + "' at "
                        + url
                        + ".";

        return switch (e.getErrorCode()) {

            case 28000 ->
                    baseMessage
                            + " The account is locked. Unlock it in Oracle or use DB_USER/DB_PASSWORD for a different account.";

            case 1017 ->
                    baseMessage
                            + " Username or password is incorrect. Check the Oracle password or update DB_USER/DB_PASSWORD.";

            default ->
                    baseMessage
                            + " "
                            + e.getMessage();
        };
    }
}
