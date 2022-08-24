package com.meshalkina.coffee_machine.model;


public class Good {
    private Long goodId;
    private String name;
    private double price;

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
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

    public Good() {
    }

    public Good(Long goodId, String name, double price) {
        this.goodId = goodId;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Good{" +
                "product_id=" + goodId +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
