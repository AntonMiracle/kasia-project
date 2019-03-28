package com.kasia.controller.dto;

import com.kasia.model.Operation;

import java.time.temporal.IsoFields;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class WeekOperationHistory {
    private int week;
    private int max;
    private Map<Integer, Set<Operation>> weeks = new TreeMap<>();

    public void setWeeks(Set<Operation> operations) {
        week = 0;
        max = 0;
        weeks = new TreeMap<>();
        Set<Operation> sortByDate = new TreeSet<>(operations);

        int lastWeek = Integer.MAX_VALUE;
        for (Operation o : sortByDate) {
            int weekNumber = o.getCreateOn().getYear() * 100 + o.getCreateOn().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            Set<Operation> weekOperations = weeks.get(week);
            if (lastWeek > weekNumber) {
                ++week;
                weekOperations = new TreeSet<>();
                weeks.put(week, weekOperations);
                lastWeek = weekNumber;
            }
            weekOperations.add(o);
        }
        max = weeks.size();
        week = 1;
    }

    public Set<Operation> current() {
        if (weeks.size() > 0) return weeks.get(week);
        return new TreeSet<>();
    }

    public Set<Operation> next() {
        week += week < max ? 1 : 0;
        return weeks.get(week);
    }

    public Set<Operation> previous() {
        week -= week > 1 ? 1 : 0;
        return weeks.get(week);
    }

    public boolean isExist() {
        return weeks != null && weeks.size() > 0;
    }

    @Override
    public String toString() {
        return "WeekOperationHistory{" +
                "week=" + week +
                ", max=" + max +
                ", weeks=" + weeks +
                '}';
    }
}
