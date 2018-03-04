package com.kasia.core.model;


import java.util.Currency;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Money {
    private long banknotes;
    private int penny;
    private Type type;

    private Money(long banknotes, int penny, Type type) {
        setBanknotes(banknotes);
        setPenny(penny);
        setType(type);
    }

    private Money(Money money) {
        setBanknotes(money.getBanknotes());
        setPenny(money.getPenny());
        setType(money.getType());
    }

    private void setBanknotes(long banknotes) {
        this.banknotes = banknotes;
    }

    private void setPenny(int penny) {
        Util.isThrowIAEWithMsg(penny < 0 || penny > 99, "Penny must be 0-99");
        this.penny = penny;
    }

    private void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    public long getBanknotes() {
        return this.banknotes;
    }

    public int getPenny() {
        return this.penny;
    }

    @Override
    public String toString() {
        return String.format("%d,%02d %s", getBanknotes(), getPenny(), getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (banknotes != money.banknotes) return false;
        if (penny != money.penny) return false;
        return type == money.type;
    }

    @Override
    public int hashCode() {
        int result = (int) (banknotes ^ (banknotes >>> 32));
        result = 31 * result + penny;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public Money getDiscount(double discount) {
        Util.isThrowIAEWithMsg(discount < 0 || discount > 100, "discount must be 0-100");
        double money = getPenny() / 100.0 + getBanknotes();
        discount = (1 - discount / 100);
        money = money * discount;
        long banknotes = (long) money;
        int penny = (int) Math.round((money % banknotes) * 100);
        return valueOf(banknotes, penny, this.getType());
    }

    public Money plus(Money moneyToAdd) {
        Util.isThrowIAEWithMsg(getType() != moneyToAdd.getType(), "Different type of currency");
        long banknotes = this.getBanknotes() + moneyToAdd.getBanknotes();
        int penny = this.getPenny() + moneyToAdd.getPenny();
        if (penny >= 100) {
            banknotes++;
            penny = penny % 100;
        }
        return valueOf(banknotes, penny, this.getType());
    }

    public static Money valueOf(long banknotes, int penny, Type type) {
        return new Money(banknotes, penny, type);
    }

    public static Money valueOf(Money money) {
        return new Money(money);
    }

    public static Money valueOf(String money) {
        money = money.trim().replaceAll(" {2,}", " ");
        Util.isThrowIAEWithMsg(!getMatcher(money).find(), "Wrong string representation");
        StringTokenizer tokens = new StringTokenizer(money);
        String value = tokens.nextToken();
        long banknotes = Long.valueOf(value.split(",")[0]);
        int penny = Integer.valueOf(value.split(",")[1]);
        Type type = Type.typeOf(tokens.nextToken());
        return valueOf(banknotes, penny, type);
    }

    public static Matcher getMatcher(String text) {
        String regex = "^[-+]?\\d+,\\d{1,2} " + Type.getRegex();
        return Pattern.compile(regex).matcher(text);
    }

    public enum Type {
        PLN("PLN"), EUR("EUR"), USD("USD"), UAH("UAH"), RUB("RUB");
        private Currency currency;

        Type(String currencyCode) {
            setCurrency(currencyCode);
        }

        private void setCurrency(String code) {
            this.currency = Currency.getInstance(code);
        }

        @Override
        public String toString() {
            return currency.getCurrencyCode();
        }


        public Currency getCurrency() {
            return this.currency;
        }

        static protected String getRegex() {
            StringBuilder regex = new StringBuilder();
            for (Type type : Type.values()) {
                regex.append(regex.length() == 0 ? type : "|" + type);
            }
            return regex.toString();
        }

        public static Type typeOf(String type) {
            Util.isThrowIAEWithMsg(!type.matches(getRegex()), "Need to be : " + getRegex());
            return Type.valueOf(type);

        }
    }

}
