package com.kasia.core.model;

import java.util.Currency;

public class Price {
    public final String PLN = "PLN";
    public final String UAH = "UAH";
    public final String USD = "USD";
    public final String EUR = "EUR";
    public final String RUB = "RUB";
    private int banknotes;
    private Currency currency;
    private int penny;

    public Price() {
    }

    public Price(int banknotes, int pennys, Currency currency) {
        setBanknotes(banknotes);
        setPenny(pennys);
        setCurrency(currency);
    }

    public Price(Currency currency) {
        setCurrency(currency);
    }

    public void setBanknotes(int banknotes) {
        if (banknotes < 0) throw new IllegalArgumentException("banknotes must be positive");
        this.banknotes = banknotes;
    }

    public int getBanknotes() {
        return this.banknotes;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setPenny(int penny) {
        if (penny < 0 || penny > 99) throw new IllegalArgumentException("banknotes must be  0-99 ");
        this.penny = penny;
    }

    public int getPenny() {
        return this.penny;
    }

    @Override
    public String toString() {
        return String.format("%d,%02d %s", banknotes, penny, currency.getCurrencyCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (banknotes != price.banknotes) return false;
        if (penny != price.penny) return false;
        return currency != null ? currency.equals(price.currency) : price.currency == null;
    }

    @Override
    public int hashCode() {
        int result = banknotes;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + penny;
        return result;
    }

    public Price add(Price priceToAdd) {
        int banknotes = this.banknotes + priceToAdd.getBanknotes();
        int penny = this.penny + priceToAdd.getPenny();
        if (penny >= 100) {
            banknotes++;
            penny = penny % 100;
        }
        return new Price(banknotes, penny, this.currency);
    }

    public Price getDiscount(double discount) {
        if (discount < 0 || discount > 100) throw new IllegalArgumentException("discount must be 0-100");
        discount = 1 - discount / 100;
        double price = (this.banknotes * 100 + this.penny) / 100.0;
        price = price * discount;
        int banknotes = (int) price;
        int penny = (int) Math.round((price % banknotes) * 100);
        return new Price(banknotes, penny, this.currency);
    }
}
