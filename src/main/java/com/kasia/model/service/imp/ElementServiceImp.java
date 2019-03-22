package com.kasia.model.service.imp;

import com.kasia.controller.dto.ElementDTO;
import com.kasia.model.Element;
import com.kasia.model.repository.ElementRepository;
import com.kasia.model.service.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class ElementServiceImp implements ElementService {
    @Autowired
    private ElementRepository elementR;

    @Override
    public Element convert(ElementDTO dto) {
        return new Element(dto.getName(), new BigDecimal(dto.getPrice()));
    }

    @Override
    public Element save(Element element) {
        return elementR.save(element);
    }

    @Override
    public boolean delete(long elementId) {
        elementR.delete(findById(elementId));
        return true;
    }

    @Override
    public Element findById(long elementId) {
        return elementR.findById(elementId).orElse(null);
    }
}
