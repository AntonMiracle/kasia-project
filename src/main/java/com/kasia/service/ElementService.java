package com.kasia.service;

import com.kasia.model.*;

import java.util.Set;

public interface ElementService extends CRUDService<Element> {
    boolean isNameUnique(Budget budget, String name);

    Element create(String name, ElementType type, Price defaultPrice);

    Element getByName(Budget budget, String name);

    Set<Element> getByElementType(Budget budget, ElementType type);
}
