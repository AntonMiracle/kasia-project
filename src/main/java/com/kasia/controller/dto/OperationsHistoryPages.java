package com.kasia.controller.dto;

import com.kasia.model.Operation;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class OperationsHistoryPages {
    private int page;
    private int max;
    private Map<Integer, Set<Operation>> pages = new TreeMap<>();

    public void setPages(Set<Operation> operations) {
        page = 0;
        pages = new TreeMap<>();
        int count = -1;
        for (Operation o : new TreeSet<>(operations)) {
            if (++count % 10 == 0) {
                ++page;
                pages.put(page, new TreeSet<>());
            }
            pages.get(page).add(o);
        }
        max = pages.size();
        page = 1;
    }

    public Set<Operation> current() {
        if (pages.size() > 0) return pages.get(page);
        return new TreeSet<>();
    }

    public Set<Operation> next() {
        page += page < max ? 1 : 0;
        return pages.get(page);
    }

    public Set<Operation> previous() {
        page -= page > 1 ? 1 : 0;
        return pages.get(page);
    }

    public boolean isExist() {
        return pages != null && pages.size() > 0;
    }

    @Override
    public String toString() {
        return "OperationsHistoryPages{" +
                "page=" + page +
                ", max=" + max +
                ", pages=" + pages +
                '}';
    }

    public boolean isEnd() {
        return page == max;
    }

    public boolean isStart() {
        return page == 1;
    }
}
