package com.kasia.controller.dto;

import com.kasia.model.Operation;

import java.time.LocalDateTime;
import java.util.*;

public class WeekOperationHistory {
    private int week = 1;
    private int max = 1;
    private Map<Integer, Map<LocalDateTime, Set<Operation>>> weeks;

    public void create(Set<Operation> operations) {
        weeks = new TreeMap<>();
        Map<LocalDateTime, Set<Operation>> dayOperation = sortByDayOperation(operations);
        int i = 0;
        int days = 0;
        for (LocalDateTime day : dayOperation.keySet()) {
            if (days++ % 3 == 0) weeks.put(++i, new TreeMap<>(Collections.reverseOrder()));
            weeks.get(i).put(day, dayOperation.get(day));
        }
        max = weeks.size();
    }

    public void start() {
        week = 1;
    }

    public Map<LocalDateTime, Set<Operation>> current() {
        if (weeks.size() > 0) return weeks.get(week);
        return new TreeMap<>();
    }

    public Map<LocalDateTime, Set<Operation>> next() {
        week += week < max ? 1 : 0;
        return weeks.get(week);
    }

    public Map<LocalDateTime, Set<Operation>> previous() {
        week -= week > 1 ? 1 : 0;
        return weeks.get(week);
    }

    public boolean isExist() {
        return weeks != null && weeks.size() > 0;
    }

    private Map<LocalDateTime, Set<Operation>> sortByDayOperation(Set<Operation> operations) {
        operations = new TreeSet<>(operations);
        Map<LocalDateTime, Set<Operation>> result = new TreeMap<>(Collections.reverseOrder());
        LocalDateTime from = null;
        LocalDateTime to = null;
        Set<Operation> dayOperations = new TreeSet<>();
        for (Operation o : operations) {
            LocalDateTime on = o.getCreateOn();
            if (from == null) {
                from = LocalDateTime.of(on.getYear(), on.getMonth(), on.getDayOfMonth(), 0, 0, 0, 0);
                to = from.minusDays(1);
                result.put(from, dayOperations);
            }
            if (on.compareTo(to) <= 0) {
                from = LocalDateTime.of(on.getYear(), on.getMonth(), on.getDayOfMonth(), 0, 0, 0, 0);
                to = from.minusDays(1);
                dayOperations = new TreeSet<>();
                result.put(from, dayOperations);
            }
            dayOperations.add(o);
        }
        return result;
    }
}
