package com.kasia.core.model;


import java.util.Currency;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import static com.kasia.core.model.Util.throwIAE;

public class Money {
    public static final long MAX_BANKNOTES = Long.MAX_VALUE / 100;
    public static final long MIN_BANKNOTES = Long.MIN_VALUE / 100;
    public static final int MAX_PENNY = 99;
    public static final int MIN_PENNY = -99;
    private long amount;
    private Type type;

    private Money(Type type) {
        setType(type);
    }

    private Money(long banknotes, int penny, Type type) {
        setAmount(banknotes, penny);
        setType(type);
    }

    private Money(long amount, Type type) {
        setAmount(amount);
        setType(type);
    }

    private void setAmount(long amount) {
        this.amount = amount;
    }

    private void setAmount(long banknotes, int penny) {
        validation(banknotes, penny);
        long amount = Math.abs(banknotes) * 100 + Math.abs(penny);
        if (banknotes < 0 || penny < 0) {
            amount *= -1;
        }
        setAmount(amount);
    }

    private void validation(long banknotes, int penny) {
        throwIAE(banknotes < MIN_BANKNOTES, "\nBANKNOTES > " + MIN_BANKNOTES + "\nCURRENT: " + banknotes);
        throwIAE(banknotes > MAX_BANKNOTES, "\nBANKNOTES < " + MAX_BANKNOTES + "\nCURRENT: " + banknotes);
        throwIAE(penny < MIN_PENNY, "\nPENNY > " + MIN_PENNY + "\nCURRENT: " + penny);
        throwIAE(penny > MAX_PENNY, "\nPENNY < " + MAX_PENNY + "\nCURRENT: " + penny);
        throwIAE((penny < 0 && banknotes != 0), "PENNY ain`t be negative if BANKNOTES != 0");
    }

    private void setType(Type type) {
        throwIAE(type == null, "Type ain`t be NULL");
        this.type = type;
    }

    public static Money of(Type type) {
        return new Money(type);
    }

    public Type getType() {
        return this.type;

    }

    public long getBanknotes() {
        return this.amount / 100;
    }

    public int getPenny() {
        return (int) (this.amount - getBanknotes() * 100);
    }

    public static Money of(long banknotes, int penny, Type type) {
        return new Money(banknotes, penny, type);
    }

    @Override
    public String toString() {
        StringBuilder format = new StringBuilder();
        format.append("%").append(amount != 0 ? "+" : "").append(".2f %s");
        return String.format(format.toString(), getAmount() / 100.0, getType());
    }

    private long getAmount() {
        return this.amount;
    }

    public static Money of(Money money) {
        throwIAE(money == null, "Money ain`t NULL");
        return new Money(money.getBanknotes(), money.getPenny(), money.getType());
    }

    public static Money of(String money) {
        throwIAE(money == null, "Money ain`t NULL");
        money = money.trim().replaceAll(" +", " ").replaceAll(",", ".");
        throwIAE(!getPattern().matcher(money).matches(), "\nArgument: " + money + "\nMoney REGEX: " + getPattern());
        StringTokenizer tokens = new StringTokenizer(money);
        double amount = Double.valueOf(tokens.nextToken()).doubleValue() * 100;
        Type type = Type.of(tokens.nextToken());
        return new Money((long) amount, type);
    }

    private static Pattern getPattern() {
        String regex = "^[-+]?[0-9]+[.]?[0-9]{0,2} (" + Type.getPattern().pattern() + ")$";
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
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
        int result = (int) (amount ^ (amount >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public Money discount(double discount) {
        throwIAE(discount < 0 || discount > 100, "discount must be 0-100");
        discount = (1 - discount / 100);
        long amount = Math.round(getAmount() * discount);
        return new Money(amount, this.getType());
    }

    public Money plus(Money money) {
        throwIAE(money == null, "Can`t add NULL");
        throwIAE(this.getType() != money.getType(), "Can add only the same Money.Type");
        long amount = this.getAmount() + money.getAmount();
        return new Money(amount, this.getType());
    }

    public Money minus(Money money) {
        throwIAE(money == null, "Can`t add NULL");
        throwIAE(this.getType() != money.getType(), "Can add only the same Money.Type");
        long amount = this.getAmount() - money.getAmount();
        return new Money(amount, this.getType());
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
            return this.currency;
        }

        public static Type of(String code) {
            throwIAE(code == null, "Type code ain`t NULL");
            code = code.trim().toUpperCase();
            return Type.valueOf(code);
        }

        static protected Pattern getPattern() {
            StringBuilder regex = new StringBuilder();
            for (Type type : Type.values()) {
                regex.append(regex.length() == 0 ? type : "|" + type);
            }
            return Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE);
        }
    }

}
