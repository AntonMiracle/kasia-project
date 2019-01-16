package com.kasia.model.service;

import com.kasia.model.ElementProvider;

public interface ElementProviderService extends Service<ElementProvider> {
    ElementProvider create(String name, String description);
}