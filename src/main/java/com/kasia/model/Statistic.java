package com.kasia.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Statistic {

    private Set<Element> elements = new HashSet<>();
    private Set<Place> places = new HashSet<>();
    private Set<Operation> operations = new HashSet<>();
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate from;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate to;
    private final String nameOfAll = "-";
    private String ofElement;
    private String ofPlace;

    private Operation maxIncome;
    private Operation maxConsumption;
    private BigDecimal deltaByElement;
    private BigDecimal deltaByPlace;
    private BigDecimal deltaIncomePerOperation;
    private BigDecimal deltaConsumptionPerOperation;
    private BigDecimal sumIncome;
    private BigDecimal sumConsumption;
    private BigDecimal delta;

    private void setStatisticNumbers() {
        long countIncomeOperation = 0;
        long countConsumptionOperation = 0;
        long countOfElementOperation = 0;
        long countOfPlaceOperation = 0;
        sumIncome = BigDecimal.ZERO;
        sumConsumption = BigDecimal.ZERO;
        deltaByElement = BigDecimal.ZERO;
        deltaByPlace = BigDecimal.ZERO;

        for (Operation o : operations) {
            if (o.getType() == OperationType.INCOME) {
                ++countIncomeOperation;
                sumIncome = sumIncome.add(o.getPrice());
                if (o.getElement().getName().equals(ofElement) && isElementPicked()) {
                    ++countOfElementOperation;
                    deltaByElement = deltaByElement.add(o.getPrice());
                }
                if (o.getPlace().getName().equals(ofPlace) && isPlacePicked()) {
                    ++countOfPlaceOperation;
                    deltaByPlace = deltaByPlace.add(o.getPrice());
                }
            } else {
                ++countConsumptionOperation;
                sumConsumption = sumConsumption.add(o.getPrice());
                if (o.getElement().getName().equals(ofElement) && isElementPicked()) {
                    ++countOfElementOperation;
                    deltaByElement = deltaByElement.add(o.getPrice());
                }
                if (o.getPlace().getName().equals(ofPlace) && isPlacePicked()) {
                    ++countOfPlaceOperation;
                    deltaByPlace = deltaByPlace.add(o.getPrice());
                }
            }

            if (o.getType() == OperationType.INCOME) {
                if (maxIncome == null) maxIncome = o;
                maxIncome = o.getPrice().compareTo(maxIncome.getPrice()) > 0 ? o : maxIncome;
            } else {

                if (maxConsumption == null) maxConsumption = o;
                maxConsumption = o.getPrice().compareTo(maxConsumption.getPrice()) > 0 ? o : maxConsumption;
            }
        }
        if (countIncomeOperation != 0) {
            deltaIncomePerOperation = sumIncome.divide(BigDecimal.valueOf(countIncomeOperation), 2, RoundingMode.HALF_UP);
        }
        if (countConsumptionOperation != 0) {
            deltaConsumptionPerOperation = sumConsumption.divide(BigDecimal.valueOf(countConsumptionOperation), 2, RoundingMode.HALF_UP);
        }
        if (countOfElementOperation != 0) {
            deltaByElement = deltaByElement.divide(BigDecimal.valueOf(countOfElementOperation), 2, RoundingMode.HALF_UP);
        }
        if (countOfPlaceOperation != 0) {
            deltaByPlace = deltaByPlace.divide(BigDecimal.valueOf(countOfPlaceOperation), 2, RoundingMode.HALF_UP);
        }
        delta = sumIncome.subtract(sumConsumption);
    }

    public Statistic(Set<Operation> operations, Set<Element> elements, Set<Place> places) {
        this.operations = new TreeSet<>(operations);
        this.elements = new TreeSet<>(elements);
        this.places = new TreeSet<>(places);
        to = LocalDate.now();
        from = to.minusDays(10);
        ofElement = nameOfAll;
        ofPlace = nameOfAll;
    }

    public Set<Element> getElements() {
        elements.add(new Element(nameOfAll, null));
        return elements;
    }

    public Set<Place> getPlaces() {
        places.add(new Place(nameOfAll, null));
        return places;
    }

    private Statistic byElement() {
        if (ofElement.equals(nameOfAll)) return this;
        Set<Operation> tmp = new TreeSet<>();
        for (Operation o : operations) {
            if (o.getElement().getName().equals(ofElement)) tmp.add(o);
        }
        operations = tmp;
        return this;
    }

    private Statistic byPlace() {
        if (ofPlace.equals(nameOfAll)) return this;
        Set<Operation> tmp = new TreeSet<>();
        for (Operation o : operations) {
            if (o.getPlace().getName().equals(ofPlace)) tmp.add(o);
        }
        operations = tmp;
        return this;
    }

    private Statistic byDate(ZoneId zoneId) {
        Set<Operation> tmp = new TreeSet<>();
        LocalDateTime fromLdt = from.atStartOfDay(zoneId).toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime toLdt = to.atStartOfDay(zoneId).toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(1);
        for (Operation o : operations) {
            if (fromLdt.compareTo(o.getCreateOn()) <= 0 && toLdt.compareTo(o.getCreateOn()) >= 0) {
                tmp.add(o);
            }

        }
        operations = tmp;
        return this;
    }

    public Statistic calculate(ZoneId zoneId) {
        byDate(zoneId);
        byPlace();
        byElement();
        setStatisticNumbers();
        return this;
    }

    public boolean isElementPicked() {
        return !ofElement.equals(nameOfAll);
    }

    public boolean isPlacePicked() {
        return !ofPlace.equals(nameOfAll);
    }

    public boolean isIncomeExist() {
        return sumIncome.compareTo(BigDecimal.ZERO) != 0;
    }

    public boolean isConsumptionExist() {
        return sumConsumption.compareTo(BigDecimal.ZERO) != 0;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public String getOfElement() {
        return ofElement;
    }

    public void setOfElement(String ofElement) {
        this.ofElement = ofElement;
    }

    public String getOfPlace() {
        return ofPlace;
    }

    public void setOfPlace(String ofPlace) {
        this.ofPlace = ofPlace;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public Operation getMaxIncome() {
        return maxIncome;
    }

    public void setMaxIncome(Operation maxIncome) {
        this.maxIncome = maxIncome;
    }

    public Operation getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(Operation maxConsumption) {
        this.maxConsumption = maxConsumption;
    }

    public BigDecimal getDeltaByElement() {
        return deltaByElement;
    }

    public void setDeltaByElement(BigDecimal deltaByElement) {
        this.deltaByElement = deltaByElement;
    }

    public BigDecimal getDeltaByPlace() {
        return deltaByPlace;
    }

    public void setDeltaByPlace(BigDecimal deltaByPlace) {
        this.deltaByPlace = deltaByPlace;
    }

    public BigDecimal getDeltaIncomePerOperation() {
        return deltaIncomePerOperation;
    }

    public void setDeltaIncomePerOperation(BigDecimal deltaIncomePerOperation) {
        this.deltaIncomePerOperation = deltaIncomePerOperation;
    }

    public BigDecimal getDeltaConsumptionPerOperation() {
        return deltaConsumptionPerOperation;
    }

    public void setDeltaConsumptionPerOperation(BigDecimal deltaConsumptionPerOperation) {
        this.deltaConsumptionPerOperation = deltaConsumptionPerOperation;
    }

    public BigDecimal getSumIncome() {
        return sumIncome;
    }

    public void setSumIncome(BigDecimal sumIncome) {
        this.sumIncome = sumIncome;
    }

    public BigDecimal getSumConsumption() {
        return sumConsumption;
    }

    public void setSumConsumption(BigDecimal sumConsumption) {
        this.sumConsumption = sumConsumption;
    }

    public BigDecimal getDelta() {
        return delta;
    }

    public void setDelta(BigDecimal delta) {
        this.delta = delta;
    }
}
