package com.kasia.model.service.imp;

import com.kasia.exception.IdRuntimeException;
import com.kasia.model.ElementProvider;
import com.kasia.model.repository.ElementProviderRepository;
import com.kasia.model.service.ElementProviderService;
import com.kasia.model.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ElementProviderServiceImp implements ElementProviderService, ValidationService<ElementProvider> {
    @Autowired
    private ElementProviderRepository elementProviderRepository;
    @Autowired
    private ValidationService<ElementProvider> elementProviderValidationService;

    @Override
    public ElementProvider save(ElementProvider model) {
        elementProviderValidationService.verifyValidation(model);
        return elementProviderRepository.save(model);
    }

    @Override
    public boolean delete(ElementProvider model) {
        elementProviderValidationService.verifyValidation(model);
        if (model.getId() <= 0) throw new IdRuntimeException();
        elementProviderRepository.delete(model);
        return true;
    }

    @Override
    public ElementProvider findById(long id) {
        if (id <= 0) throw new IdRuntimeException();
        Optional<ElementProvider> elementProvider = elementProviderRepository.findById(id);
        return elementProvider.isPresent() ? elementProvider.get() : null;
    }

    @Override
    public Set<ElementProvider> findAll() {
        Set<ElementProvider> providers = new HashSet<>();
        elementProviderRepository.findAll().forEach(providers::add);
        return providers;
    }

    @Override
    public ElementProvider create(String name, String description) {
        ElementProvider model = new ElementProvider(name, description);
        elementProviderValidationService.verifyValidation(model);
        return model;
    }
}
