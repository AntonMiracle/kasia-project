package com.kasia.core.model;


import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import static com.kasia.core.model.Util.throwIAE;

public class Money {
    public static final long MAX_BANKNOTES = Long.MAX_VALUE / 100;
    public static final long MIN_BANKNOTES = Long.MIN_VALUE / 100;
    public static final int MAX_PENNY = 99;
    public static final int MIN_PENNY = -99;
    private Type type;
    private long amount;

    protected Money() {
    }

    protected Money(Type type) {
        setType(type);
    }

    protected Money(long banknotes, int penny, Type type) {
        setType(type);
        setAmount(banknotes, penny);
    }

    protected Money(long amount, Type type) {
        setAmount(amount);
        setType(type);
    }

    protected void setAmount(long banknotes, int penny) {
        validation(banknotes, penny);
        long amount = Math.abs(banknotes) * 100 + Math.abs(penny);
        if (banknotes < 0 || penny < 0) {
            amount *= -1;
        }
        setAmount(amount);
    }

    protected void setAmount(long amount) {
        this.amount = amount;
    }

    protected void setType(Type type) {
        throwIAE(type == null, "Type is NULL");
        this.type = type;
    }

    public long getBanknotes() {
        return amount / 100;
    }

    public int getPenny() {
        return (int) (this.amount - getBanknotes() * 100);
    }

    protected long getAmount() {
        return amount;
    }

    public Type getType() {
        throwIAE(type == null, "Type is Null");
        return type;
    }

    protected double getDoubleAmount() {
        return getAmount() / 100.0;
    }

    private void validation(long banknotes, int penny) {
        throwIAE(banknotes < MIN_BANKNOTES, banknotes + " < " + MIN_BANKNOTES);
        throwIAE(banknotes > MAX_BANKNOTES, banknotes + " > " + MAX_BANKNOTES);
        throwIAE(penny < MIN_PENNY, penny + " < " + MIN_PENNY);
        throwIAE(penny > MAX_PENNY, penny + " > " + MAX_PENNY);
        throwIAE((penny < 0 && banknotes != 0), "Banknotes != 0 and Penny < 0");
    }

    public static Money of(Type type) {
        return new Money(type);
    }

    public static Money of(long banknotes, int penny, Type type) {
        return new Money(banknotes, penny, type);
    }

    public static Money of(Money money) {
        throwIAE(money == null, "Money is NULL");
        return new Money(money.getAmount(), money.getType());
    }

    @Override
    public String toString() {
        String sign = getAmount() < 0 ? "" : "+";
        return sign + String.format("%.2f %s", getDoubleAmount(), getType());
    }

    public String toString(Locale locale) {
        throwIAE(locale == null, "Local is NULL");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        nf.setCurrency(getType().getCurrency());
        return addSign(nf.format(Math.abs(getDoubleAmount())));
    }

    protected String addSign(String amount) {
        String firstNumber = String.valueOf(Math.abs(getDoubleAmount())).substring(0, 1);
        return amount.replaceFirst("[0-9]", (getAmount() < 0 ? "-" : "+") + firstNumber);
    }

    public Money discount(double discount) {
        throwIAE(discount < 0 || discount > 100, "Discount must be 0-100");
        discount = (1 - discount / 100);
        long amount = Math.round(getAmount() * discount);
        return new Money(amount, getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (amount != money.amount) return false;
        return type == money.type;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (int) (amount ^ (amount >>> 32));
        return result;
    }

    public Money plus(Money money) {
        plusMinusValidation(money);
        long amount = this.getAmount() + money.getAmount();
        return new Money(amount, this.getType());
    }

    public Money minus(Money money) {
        plusMinusValidation(money);
        long amount = this.getAmount() - money.getAmount();
        return new Money(amount, this.getType());
    }

    private void plusMinusValidation(Money money) {
        throwIAE(money == null, "Money is NULL");
        throwIAE(this.getType() != money.getType(), "Different types");
    }

    public enum Type {
        PLN("PLN"), EUR("EUR"), USD("USD"), UAH("UAH"), RUB("RUB");
        private Currency currency;

        Type(String currencyCode) {
            this.currency = Currency.getInstance(currencyCode);
        }

        @Override
        public String toString() {
            return currency.getCurrencyCode();
        }

        public Currency getCurrency() {
            return currency;
        }

        public static Type of(String code) {
            throwIAE(code == null, "Code is NULL");
            code = code.trim().toUpperCase();
            return Type.valueOf(code);
        }
    }
}
