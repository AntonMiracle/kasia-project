package com.kasia.controller;

import com.kasia.controller.dto.ElementDTO;
import com.kasia.model.Budget;
import com.kasia.model.Element;
import com.kasia.model.ElementType;
import com.kasia.model.Price;
import com.kasia.model.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.math.BigDecimal;

import static com.kasia.controller.ViewNameAndControllerURL.*;

@Controller
public class ElementController {
    @Autowired
    private BudgetService bService;
    @Autowired
    private MySessionController sessionController;

    @ModelAttribute("elementDTO")
    public ElementDTO getAddBudgetDTO() {
        ElementDTO dto = new ElementDTO();
        if (sessionController.isBudgetOpen()) {
            dto.setBudgetId(sessionController.getBudget().getId());
            dto.setCurrency(sessionController.getBudget().getBalance().getCurrencies().toString());
        }
        return dto;
    }

    @ModelAttribute("types")
    public ElementType getAllTypes() {
        return ElementType.CONSUMPTION;
    }

    @GetMapping(U_ELEMENT)
    public String openElement() {
        return sessionController.isBudgetOpen() ? V_ELEMENT : redirect(U_BUDGET_ALL);
    }

    @PostMapping(U_ELEMENT_ADD)
    public String addNewElement(@Valid @ModelAttribute ElementDTO dto, BindingResult bResult) {
        if (bResult.hasErrors()) return openElement();

        Budget budget = sessionController.getBudget();
        Price price = new Price(new BigDecimal(dto.getDefaultPrice()), budget.getBalance().getCurrencies());
        Element element = new Element(dto.getName(), price, ElementType.valueOf(dto.getType()));
        bService.addElement(budget.getId(), element);

        return redirect(U_BUDGET);
    }
}
