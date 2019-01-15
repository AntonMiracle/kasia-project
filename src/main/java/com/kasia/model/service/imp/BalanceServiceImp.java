package com.kasia.model.service.imp;

import com.kasia.model.Balance;
import com.kasia.model.service.BalanceService;
import com.kasia.validation.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class BalanceServiceImp implements BalanceService, ValidationService<Balance> {
}
