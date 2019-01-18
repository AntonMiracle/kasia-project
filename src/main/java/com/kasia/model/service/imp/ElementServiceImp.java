package com.kasia.model.service.imp;

import com.kasia.model.Element;
import com.kasia.model.ElementType;
import com.kasia.model.Price;
import com.kasia.model.repository.ElementRepository;
import com.kasia.model.service.ElementService;
import com.kasia.model.validation.ElementValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ElementServiceImp implements ElementService {
    @Autowired
    private ElementRepository eRepository;
    @Autowired
    private ElementValidation eValidation;

    @Override
    public Element save(Element model) {
        eValidation.verifyValidation(model);
        return eRepository.save(model);
    }

    @Override
    public boolean delete(Element model) {
        eValidation.verifyValidation(model);
        eValidation.verifyPositiveId(model.getId());
        eRepository.delete(model);
        return true;
    }

    @Override
    public Element findById(long id) {
        eValidation.verifyPositiveId(id);
        Optional<Element> element = eRepository.findById(id);
        return element.isPresent() ? element.get() : null;
    }

    @Override
    public Set<Element> findAll() {
        Set<Element> elements = new HashSet<>();
        eRepository.findAll().forEach(elements::add);
        return elements;
    }

    @Override
    public Element create(String name, ElementType type, Price defaultPrice) {
        Element model = new Element(name, defaultPrice, type);
        eValidation.verifyValidation(model);
        return model;
    }
}
