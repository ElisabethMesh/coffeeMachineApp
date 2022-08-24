package com.meshalkina.coffee_machine.dao;

import com.meshalkina.coffee_machine.model.Check;
import com.meshalkina.coffee_machine.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChecksDAO implements CoffeeMachineDAO<Check> {

    private static Connection connection = ConnectionManager.open();

    @Override
    public List<Check> findAll() {
        List<Check> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cashtest.checks ORDER BY check_id;");

            while (resultSet.next()) {
                Check check = getCheckFromResultSet(resultSet);
                result.add(check);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Check findById(Long id) {
        Check check = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM cashtest.checks WHERE check_id=?")) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            check = getCheckFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    public Check findByDateTime(LocalDateTime dateTime) {
        Check check = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM cashtest.checks WHERE date_time=?")) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(dateTime));

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            check = getCheckFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    @Override
    public void save(Check check) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO cashtest.checks (date_time, sum) VALUES (?, ?)")) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(check.getDateTime()));
            preparedStatement.setDouble(2, check.getSum());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Check check) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE cashtest.checks SET date_time=?, sum=? WHERE check_id=?")) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(check.getDateTime()));
            preparedStatement.setDouble(2, check.getSum());
            preparedStatement.setLong(3, check.getCheckId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM cashtest.checks WHERE check_id=?");) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Check getCheckFromResultSet(ResultSet resultSet) throws SQLException {
        Check check = new Check();

        check.setCheckId(resultSet.getLong("check_id"));
        check.setDateTime(resultSet.getTimestamp("date_time").toLocalDateTime());
        check.setSum(resultSet.getDouble("sum"));

        return check;
    }
}
