package ru.netology;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class DbInteraction {
    @BeforeEach
    @SneakyThrows
    void setUp() {
        Faker faker = new Faker();
        String dataSQL = "INSERT INTO users(login, password) VALUES (?, ?);";

        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3300/app", "user", "pass");
                PreparedStatement prepareStatement = conn.prepareStatement(dataSQL);
        ) {
            prepareStatement.setString(1, faker.name().username());
            prepareStatement.setString(2, "password");
            prepareStatement.executeUpdate();


            prepareStatement.setString(1, faker.name().username());
            prepareStatement.setString(2, "password");
            prepareStatement.executeUpdate();
        }
    }

    @Test
    @SneakyThrows
    void stubTest() {
        String countSQL = "SELECT COUNT(*) FROM users;";
        String cardsSQL = "SELECT id, number, balance_in_kopecks FROM cards WHERE user_id = ?;";

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3300/app", "user", "pass"
                );
                Statement statement = conn.createStatement();
                PreparedStatement preparedStatement = conn.prepareStatement(cardsSQL);
        ) {
            try (ResultSet resultSet = statement.executeQuery(countSQL)) {
                if (resultSet.next()) {
                    // выборка значения по индексу столбца (нумерация с 1)
                    int count = resultSet.getInt(1);
                    // TODO: использовать
                    System.out.println(count);
                }
            }

            preparedStatement.setInt(1, 1);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var id = rs.getInt("id");
                    var number = rs.getString("number");
                    var balanceInKopecks = rs.getInt("balance_in_kopecks");
                    // TODO: сложить всё в список
                    System.out.println(id + " " + number + " " + balanceInKopecks);
                }
            }
        }
    }
}