package com.kasia.model.service;

import com.kasia.model.Operation;

public interface OperationService {
    Operation save(Operation operation);

    Operation findById(long operationId);
}
