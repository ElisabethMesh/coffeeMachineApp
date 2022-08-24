package com.meshalkina.coffee_machine.model;

public class GoodsInBasket {

    private int id;
    private String name;
    private double price;
    private int amount;
    private double sum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public GoodsInBasket() {
    }

    public GoodsInBasket(int id, String name, double price, int amount, double sum) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "GoodsInBasket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", sum=" + sum +
                '}';
    }
}
