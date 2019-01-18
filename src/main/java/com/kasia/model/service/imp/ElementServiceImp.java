package com.kasia.model.service.imp;

import com.kasia.model.Element;
import com.kasia.model.ElementType;
import com.kasia.model.Price;
import com.kasia.model.repository.ElementRepository;
import com.kasia.model.service.ElementService;
import com.kasia.model.validation.ElementValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ElementServiceImp implements ElementService {
    @Autowired
    private ElementRepository elementRepository;
    @Autowired
    private ElementValidationService elementValidationService;

    @Override
    public Element save(Element model) {
        elementValidationService.verifyValidation(model);
        return elementRepository.save(model);
    }

    @Override
    public boolean delete(Element model) {
        elementValidationService.verifyValidation(model);
        elementValidationService.verifyPositiveId(model.getId());
        elementRepository.delete(model);
        return true;
    }

    @Override
    public Element findById(long id) {
        elementValidationService.verifyPositiveId(id);
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
