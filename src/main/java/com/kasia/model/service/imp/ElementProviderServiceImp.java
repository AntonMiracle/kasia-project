package com.kasia.model.service.imp;

import com.kasia.model.ElementProvider;
import com.kasia.model.service.ElementProviderService;
import com.kasia.validation.ValidationService;
import org.springframework.stereotype.Service;

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
    public ElementProvider getById(long id) {
        return null;
    }

    @Override
    public ElementProvider create(String name, String description) {
        return null;
    }
}
