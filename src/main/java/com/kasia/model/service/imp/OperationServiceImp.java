package com.kasia.model.service.imp;

import com.kasia.model.Operation;
import com.kasia.model.repository.OperationRepository;
import com.kasia.model.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OperationServiceImp implements OperationService {
    @Autowired
    private OperationRepository operationR;

    @Override
    public Operation save(Operation operation) {
        return operationR.save(operation);
    }

    @Override
    public Operation findById(long operationId) {
        return operationR.findById(operationId).orElse(null);
    }
}
