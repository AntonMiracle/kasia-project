package com.kasia.model.service;

import com.kasia.controller.dto.ElementDTO;
import com.kasia.model.Element;

public interface ElementService {
    Element convert(ElementDTO dto);

    Element save(Element element);

    Element findById(long elementId);
}
