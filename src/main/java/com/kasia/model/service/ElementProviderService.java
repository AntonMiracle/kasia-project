package com.kasia.model.service;

import com.kasia.model.ElementProvider;

import java.util.Set;

public interface ElementProviderService {
    ElementProvider save(ElementProvider model);

    boolean delete(ElementProvider model);

    ElementProvider findById(long id);

    Set<ElementProvider> findAll();

    ElementProvider create(String name, String description);
}
