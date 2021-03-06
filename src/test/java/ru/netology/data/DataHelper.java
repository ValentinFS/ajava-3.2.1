package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;

    }


    public static AuthInfo getValidUser() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidUser() {
        Faker faker = new Faker();
        return new AuthInfo("vasya", faker.internet().password());
    }


    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getValidVerificationCode() {
        String codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();
        String code = null;
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3300/app", "user", "pass")
        ) {
            code = runner.query(conn, codeSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new VerificationCode(code);
    }

    public static VerificationCode getInvalidVerificationCode() {
        return new VerificationCode("123");
    }

    @SneakyThrows
    public static void clearAllData() {
        QueryRunner runner = new QueryRunner();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3300/app", "user", "pass");) {
            runner.execute(conn, "DELETE FROM auth_codes;");
            runner.execute(conn, "DELETE FROM card_transactions;");
            runner.execute(conn, "DELETE FROM cards;");
            runner.execute(conn, "DELETE FROM users;");
        }
    }

}