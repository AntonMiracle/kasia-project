package com.kasia.model.service;

import com.kasia.model.Element;
import com.kasia.model.ElementType;
import com.kasia.model.Price;

import java.util.Set;

public interface ElementService  {
    Element save(Element model);

    boolean delete(Element model);

    Element findById(long id);

    Set<Element> findAll();

    Element create(String name, ElementType type, Price defaultPrice);
}
