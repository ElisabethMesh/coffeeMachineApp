package com.meshalkina.coffee_machine.service;

import com.meshalkina.coffee_machine.dao.CheckLinesDAO;
import com.meshalkina.coffee_machine.dao.ChecksDAO;
import com.meshalkina.coffee_machine.dao.GoodsDAO;
import com.meshalkina.coffee_machine.model.Check;
import com.meshalkina.coffee_machine.model.CheckLine;
import com.meshalkina.coffee_machine.model.Good;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class BasketService {

    private static ChecksDAO checksDAO = new ChecksDAO();
    private static CheckLinesDAO checkLinesDAO = new CheckLinesDAO();
    private static GoodsDAO goodsDAO = new GoodsDAO();
    private Integer sequenceNumber = 0;
    private static Map<Long, Map<Integer, Integer>> basket = new HashMap<>();

    public void putInBasket(Good good) {
        if (basket.containsKey(good.getGoodId())) {
            Integer currentSequenceNumber = basket.get(good.getGoodId()).keySet().iterator().next();
            basket.get(good.getGoodId()).replace(currentSequenceNumber, basket.get(good.getGoodId()).get(currentSequenceNumber) + 1);
        } else {
            Map<Integer, Integer> innerMap = new HashMap<>();
            innerMap.put(++sequenceNumber, 1);
            basket.put(good.getGoodId(), innerMap);
        }
    }

    public void deleteAllFromBasket() {
        basket = new HashMap<>();
        sequenceNumber = 0;
    }

    public void pay() {
        Double sum = getSum();

        LocalDateTime currentDateTime = LocalDateTime.now();

        Check check = new Check();
        check.setDateTime(currentDateTime);
        check.setSum(sum);
        checksDAO.save(check);

        Check checkWithId = checksDAO.findByDateTime(currentDateTime);

        for (Map.Entry<Long, Map<Integer, Integer>> entry : basket.entrySet()) {
            CheckLine checkLine = new CheckLine();
            checkLine.setCheck(checkWithId);
            checkLine.setGood(goodsDAO.findById(entry.getKey()));

            Integer currentSequenceNumber = entry.getValue().keySet().iterator().next();
            Integer amountGoods = entry.getValue().get(currentSequenceNumber);

            checkLine.setNumString(currentSequenceNumber);
            checkLine.setAmountGoods(amountGoods);
            checkLine.setSumString(amountGoods * goodsDAO.findById(entry.getKey()).getPrice());

            checkLinesDAO.save(checkLine);

            basket = new HashMap<>();
        }
    }

    public Double getSum() {
        Double result = 0.0;
        for (Map.Entry<Long, Map<Integer, Integer>> entry : basket.entrySet()) {
            Integer currentSequenceNumber = entry.getValue().keySet().iterator().next();
            Integer amountGoods = entry.getValue().get(currentSequenceNumber);
            result += amountGoods * goodsDAO.findById(entry.getKey()).getPrice();
        }
        return result;
    }

    public Map<Long, Map<Integer, Integer>> getBasket() {
        return basket;
    }
}
