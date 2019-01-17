package com.kasia.model.service;

import com.kasia.model.*;

public interface OperationService extends Service<Operation> {
    Operation create(User user, Element element, Price price, ElementProvider elementProvider);


}
