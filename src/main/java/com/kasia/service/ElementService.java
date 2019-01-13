package com.kasia.service;

import com.kasia.model.Element;
import com.kasia.model.ElementType;
import com.kasia.model.Price;

public interface ElementService extends Service<Element> {
    Element create(String name, ElementType type, Price defaultPrice);
}
