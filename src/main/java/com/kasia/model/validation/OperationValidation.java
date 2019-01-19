package com.kasia.model.validation;

import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.model.Operation;

public interface OperationValidation extends Validation<Operation> {
    void verifyPositiveIdInside(Operation operation) throws IdInvalidRuntimeException, NullPointerException;
}
