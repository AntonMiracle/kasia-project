package com.kasia.service.validation.field;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ModelFieldTestHelper {
    default int countSameElement(List<String> fields, Set<String> clazz) {
        clazz = new HashSet<>(clazz);
        int checked = 0;
        for (String e : fields) {
            for (String c : clazz) {
                if (c.equals(e)) {
                    clazz.remove(c);
                    checked++;
                    break;
                }
            }
        }
        return checked;
    }
}
