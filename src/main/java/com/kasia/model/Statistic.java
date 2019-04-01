package com.kasia.model;

import org.springframework.format.annotation.DateTimeFormat;

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
        return this;
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
}
