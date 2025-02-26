package model;

public class Discount {
    private String priceId;
    private int discountPrice;
    private int time;

    public Discount(String priceId, int discountPrice, int time) {
        this.priceId = priceId;
        this.discountPrice = discountPrice;
        this.time = time;
    }

    public String getPriceId() {
        return priceId;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public int getTime() {
        return time;
    }
}
