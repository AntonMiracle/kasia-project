package com.kasia.model.service.imp;

import com.kasia.exception.IdRuntimeException;
import com.kasia.model.Element;
import com.kasia.model.ElementType;
import com.kasia.model.Price;
import com.kasia.model.repository.ElementRepository;
import com.kasia.model.service.ElementService;
import com.kasia.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ElementServiceImp implements ElementService, ValidationService<Element> {
    @Autowired
    private ElementRepository elementRepository;
    @Autowired
    private ValidationService<Element> elementValidationService;

    @Override
    public Element save(Element model) {
        elementValidationService.verifyValidation(model);
        return elementRepository.save(model);
    }

    @Override
    public boolean delete(Element model) {
        elementValidationService.verifyValidation(model);
        if (model.getId() <= 0) throw new IdRuntimeException();
        elementRepository.delete(model);
        return true;
    }

    @Override
    public Element findById(long id) {
        if (id <= 0) throw new IdRuntimeException();
        Optional<Element> element = elementRepository.findById(id);
        return element.isPresent() ? element.get() : null;
    }

    @Override
    public Set<Element> findAll() {
        Set<Element> elements = new HashSet<>();
        elementRepository.findAll().forEach(elements::add);
        return elements;
    }

    @Override
    public Element create(String name, ElementType type, Price defaultPrice) {
        Element model = new Element(name, defaultPrice, type);
        elementValidationService.verifyValidation(model);
        return model;
    }
}
