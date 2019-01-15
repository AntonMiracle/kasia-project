package com.kasia.model.service.imp;

import com.kasia.model.ElementProvider;
import com.kasia.model.service.ElementProviderService;
import com.kasia.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ElementProviderServiceImp implements ElementProviderService, ValidationService<ElementProvider> {
    @Override
    public ElementProvider save(ElementProvider model) {
        return null;
    }

    @Override
    public boolean delete(ElementProvider model) {
        return false;
    }

    @Override
    public ElementProvider findById(long id) {
        return null;
    }

    @Override
    public Set<ElementProvider> findAll() {
        return null;
    }

    @Override
    public ElementProvider create(String name, String description) {
        return null;
    }
}
