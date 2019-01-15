package com.kasia.model.service.imp;

import com.kasia.model.Element;
import com.kasia.model.ElementType;
import com.kasia.model.Price;
import com.kasia.model.service.ElementService;
import com.kasia.validation.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class ElementServiceImp implements ElementService, ValidationService<Element>{
    @Override
    public Element save(Element model) {
        return null;
    }

    @Override
    public boolean delete(Element model) {
        return false;
    }

    @Override
    public Element getById(long id) {
        return null;
    }

    @Override
    public Element create(String name, ElementType type, Price defaultPrice) {
        return null;
    }
}
