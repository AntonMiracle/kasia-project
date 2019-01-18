package com.kasia.model.validation;

import com.kasia.model.ElementProvider;

public interface ElementProviderValidation extends Validation<ElementProvider> {
    boolean isNameValid(String name);
}
