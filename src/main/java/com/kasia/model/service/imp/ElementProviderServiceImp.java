package com.kasia.model.service.imp;

import com.kasia.model.ElementProvider;
import com.kasia.model.repository.ElementProviderRepository;
import com.kasia.model.service.ElementProviderService;
import com.kasia.model.validation.ElementProviderValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ElementProviderServiceImp implements ElementProviderService {
    @Autowired
    private ElementProviderRepository epRepository;
    @Autowired
    private ElementProviderValidation epValidation;

    @Override
    public ElementProvider save(ElementProvider model) {
        epValidation.verifyValidation(model);
        return epRepository.save(model);
    }

    @Override
    public boolean delete(ElementProvider model) {
        epValidation.verifyValidation(model);
        epValidation.verifyPositiveId(model.getId());
        epRepository.delete(model);
        return true;
    }

    @Override
    public ElementProvider findById(long id) {
        epValidation.verifyPositiveId(id);
        Optional<ElementProvider> elementProvider = epRepository.findById(id);
        return elementProvider.isPresent() ? elementProvider.get() : null;
    }

    @Override
    public Set<ElementProvider> findAll() {
        Set<ElementProvider> providers = new HashSet<>();
        epRepository.findAll().forEach(providers::add);
        return providers;
    }

    @Override
    public ElementProvider create(String name, String description) {
        ElementProvider model = new ElementProvider(name, description);
        epValidation.verifyValidation(model);
        return model;
    }
}
