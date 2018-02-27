package com.kasia.core.model;

import java.util.Currency;

public class Price {
    public final String PLN = "PLN";
    public final String UAH = "UAH";
    public final String USD = "USD";
    public final String EUR = "EUR";
    public final String RUB = "RUB";
    private Integer banknotes;
    private Currency currency;
    private Integer penny;

    public Price() {
    }

    public Price(Integer banknotes, Integer pennys, Currency currency) {
        this.banknotes = banknotes;
        this.penny = pennys;
        this.currency = currency;
    }


    public void setBanknotes(Integer banknotes) {
        this.banknotes = banknotes;
    }

    public Integer getBanknotes() {
        return this.banknotes;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setPenny(Integer penny) {
        this.penny = penny;
    }

    public Integer getPenny() {
        return this.penny;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (banknotes != null ? !banknotes.equals(price.banknotes) : price.banknotes != null) return false;
        if (currency != null ? !currency.equals(price.currency) : price.currency != null) return false;
        return penny != null ? penny.equals(price.penny) : price.penny == null;
    }

    @Override
    public int hashCode() {
        int result = banknotes != null ? banknotes.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (penny != null ? penny.hashCode() : 0);
        return result;
    }
}
