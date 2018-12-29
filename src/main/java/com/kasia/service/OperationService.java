package com.kasia.service;

import com.kasia.model.*;

import java.time.LocalDateTime;
import java.util.Set;

public interface OperationService extends CRUDService<Operation> {
    Operation create(Element element, Price price, ElementProvider elementProvider);

    Set<Operation> getByElement(Budget budget, Element element);

    Set<Operation> getByElementProvider(Budget budget, ElementProvider elementProvider);

    Set<Operation> getBetweenDate(Budget budget, LocalDateTime from, LocalDateTime to);

    Set<Operation> getBetweenPrice(Budget budget, Price from, Price to);

}
