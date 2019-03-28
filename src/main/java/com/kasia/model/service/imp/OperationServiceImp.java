package com.kasia.model.service.imp;

import com.kasia.controller.dto.OperationDTO;
import com.kasia.model.Operation;
import com.kasia.model.OperationType;
import com.kasia.model.repository.OperationRepository;
import com.kasia.model.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class OperationServiceImp implements OperationService {
    @Autowired
    private OperationRepository operationR;
    @Autowired
    private UserService userS;
    @Autowired
    private PlaceService placeS;
    @Autowired
    private ElementService elementS;

    @Override
    public Operation save(Operation operation) {
        return operationR.save(operation);
    }

    @Override
    public Operation findById(long operationId) {
        return operationR.findById(operationId).orElse(null);
    }

    @Override
    public Operation convert(OperationDTO dto) {
        Operation operation = new Operation();
        operation.setUser(userS.findById(dto.getUserId()));
        operation.setPlace(placeS.findById(dto.getPlaceId()));
        if (dto.isIncome()) operation.setType(OperationType.INCOME);
        else if (dto.isConsumption()) operation.setType(OperationType.CONSUMPTION);
        operation.setElement(elementS.findById(dto.getElementId()));
        operation.setCreateOn(LocalDateTime.now());
        operation.setDescription(dto.getDescription());
        operation.setPrice(new BigDecimal(dto.getPrice()));
        return operation;
    }
}
