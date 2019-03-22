package com.kasia.controller;

import com.kasia.controller.dto.ElementDTO;
import com.kasia.model.Element;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

import static com.kasia.controller.ViewAndURLController.*;

@Controller
public class ElementController {
    @Autowired
    private MySessionController sessionC;
    @Autowired
    private BudgetService budgetS;
    @Autowired
    private ElementService elementS;

    @ModelAttribute("elements")
    public Set<Element> getElements() {
        if (sessionC.isBudgetOpen()) {
            return budgetS.findAllElements(sessionC.getBudget().getId());
        }
        return new HashSet<>();
    }

    @ModelAttribute("elementAdd")
    public ElementDTO getElementAdd() {
        ElementDTO dto = new ElementDTO();
        if (sessionC.isBudgetOpen()) {
            dto.setCurrency(sessionC.getBudget().getBalance().getCurrency());
            dto.setBudgetId(sessionC.getBudget().getId());
            dto.setUserId(sessionC.getUser().getId());
        }
        return dto;
    }

    @GetMapping(U_ELEMENT)
    public String openElement() {
        return sessionC.isBudgetOpen() ? V_ELEMENT : redirect(U_MAIN);
    }

    @PostMapping(U_ELEMENT_ADD)
    public String addNewElement(@Valid @ModelAttribute("elementAdd") ElementDTO dto, BindingResult bResult) {
        if (bResult.hasErrors()) return openElement();
        Element element = elementS.save(elementS.convert(dto));
        budgetS.addElement(dto.getBudgetId(), element.getId());
        return redirect(U_ELEMENT);
    }
}
