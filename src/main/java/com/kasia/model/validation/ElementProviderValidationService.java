package com.kasia.model.validation;

import com.kasia.model.ElementProvider;

public interface ElementProviderValidationService extends ValidationService<ElementProvider> {
    boolean isNameValid(String name);
}
