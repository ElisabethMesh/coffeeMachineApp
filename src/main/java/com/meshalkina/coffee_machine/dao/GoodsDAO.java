package com.meshalkina.coffee_machine.dao;

import com.meshalkina.coffee_machine.model.Good;
import com.meshalkina.coffee_machine.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsDAO implements CoffeeMachineDAO<Good> {

    private static Connection connection = ConnectionManager.open();

    @Override
    public List<Good> findAll() {
        List<Good> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cashtest.goods ORDER BY good_id;");

            while (resultSet.next()) {
                Good good = getGoodFromResultSet(resultSet);
                result.add(good);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Good findById(Long id) {
        Good good = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM cashtest.goods WHERE good_id=?")) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            good = getGoodFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return good;
    }

    @Override
    public void save(Good good) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO cashtest.goods (name, price) VALUES (?, ?)")) {
            preparedStatement.setString(1, good.getName());
            preparedStatement.setDouble(2, good.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Good good) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE cashtest.goods SET name=?, price=? WHERE good_id=?")) {
            preparedStatement.setString(1, good.getName());
            preparedStatement.setDouble(2, good.getPrice());
            preparedStatement.setLong(3, good.getGoodId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM cashtest.goods WHERE good_id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Good getGoodFromResultSet(ResultSet resultSet) throws SQLException {
        Good good = new Good();

        good.setGoodId(resultSet.getLong("good_id"));
        good.setName(resultSet.getString("name"));
        good.setPrice(resultSet.getDouble("price"));

        return good;
    }
}
