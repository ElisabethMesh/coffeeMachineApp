package com.meshalkina.coffee_machine.dao;

import com.meshalkina.coffee_machine.model.Check;
import com.meshalkina.coffee_machine.model.CheckLine;
import com.meshalkina.coffee_machine.model.Good;
import com.meshalkina.coffee_machine.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckLinesDAO implements CoffeeMachineDAO<CheckLine> {

    private static Connection connection = ConnectionManager.open();

    @Override
    public List<CheckLine> findAll() {
        List<CheckLine> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery
                    ("SELECT * FROM cashtest.check_lines LEFT JOIN cashtest.checks ON check_lines.check_id = checks.check_id " +
                            "LEFT JOIN cashtest.goods ON check_lines.good_id = goods.good_id ORDER BY check_line_id");
            while (resultSet.next()) {
                CheckLine checkLine = getCheckLineFromResultSet(resultSet);
                result.add(checkLine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CheckLine findById(Long id) {
        CheckLine checkLine = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT * FROM cashtest.check_lines LEFT JOIN cashtest.checks ON check_lines.check_id = checks.check_id " +
                        "LEFT JOIN cashtest.goods ON check_lines.good_id = goods.good_id WHERE cashtest.check_lines.check_line_id=?")) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            checkLine = getCheckLineFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checkLine;
    }

    @Override
    public void save(CheckLine checkLine) {
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("INSERT INTO cashtest.check_lines (check_id, good_id, num_string, amount_goods, sum_string) VALUES(?, ?, ?, ?, ?)")) {
            preparedStatement.setLong(1, checkLine.getCheck().getCheckId());
            preparedStatement.setLong(2, checkLine.getGood().getGoodId());
            preparedStatement.setInt(3, checkLine.getNumString());
            preparedStatement.setInt(4, checkLine.getAmountGoods());
            preparedStatement.setDouble(5, checkLine.getSumString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CheckLine checkLine) {
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("UPDATE cashtest.check_lines SET check_id=?, good_id=?, num_string=?, amount_goods=?, sum_string=? WHERE check_line_id=?")) {
            preparedStatement.setLong(1, checkLine.getCheck().getCheckId());
            preparedStatement.setLong(2, checkLine.getGood().getGoodId());
            preparedStatement.setInt(3, checkLine.getNumString());
            preparedStatement.setInt(4, checkLine.getAmountGoods());
            preparedStatement.setDouble(5, checkLine.getSumString());
            preparedStatement.setLong(6, checkLine.getCheckLineId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("DELETE FROM cashtest.check_lines WHERE check_line_id=?")) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private CheckLine getCheckLineFromResultSet(ResultSet resultSet) throws SQLException {
        CheckLine checkLine = new CheckLine();
        Check check = new Check();
        Good good = new Good();

        check.setCheckId(resultSet.getLong("check_id"));
        check.setDateTime(resultSet.getTimestamp("date_time").toLocalDateTime());
        check.setSum(resultSet.getDouble("sum"));

        good.setGoodId(resultSet.getLong("good_id"));
        good.setName(resultSet.getString("name"));
        good.setPrice(resultSet.getDouble("price"));

        checkLine.setCheckLineId(resultSet.getLong("check_line_id"));
        checkLine.setCheck(check);
        checkLine.setGood(good);
        checkLine.setNumString(resultSet.getInt("num_string"));
        checkLine.setAmountGoods(resultSet.getInt("amount_goods"));
        checkLine.setSumString(resultSet.getDouble("sum_string"));

        return checkLine;
    }
}
