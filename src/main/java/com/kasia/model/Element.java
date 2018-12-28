package com.kasia.model;

public class Element implements Model{
    private String name;//unique budget scope
    private Price defaultPrice;
    private ElementType type;
    private ElementProvider provider;

    public Element() {
    }

    public Element(String name, Price defaultPrice, ElementType type, ElementProvider provider) {
        this.name = name;
        this.defaultPrice = defaultPrice;
        this.type = type;
        this.provider = provider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        if (name != null ? !name.equals(element.name) : element.name != null) return false;
        if (defaultPrice != null ? !defaultPrice.equals(element.defaultPrice) : element.defaultPrice != null)
            return false;
        if (type != element.type) return false;
        return provider != null ? provider.equals(element.provider) : element.provider == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (defaultPrice != null ? defaultPrice.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(Price defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public ElementProvider getProvider() {
        return provider;
    }

    public void setProvider(ElementProvider provider) {
        this.provider = provider;
    }
}
