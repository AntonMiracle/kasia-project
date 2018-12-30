package com.kasia.service;

import com.kasia.model.Element;
import com.kasia.model.ElementProvider;
import com.kasia.model.Operation;
import com.kasia.model.Price;

public interface OperationService extends CRUDService<Operation> {
    Operation create(Element element, Price price, ElementProvider elementProvider);


}
