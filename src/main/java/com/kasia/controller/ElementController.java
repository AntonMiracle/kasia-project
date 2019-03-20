package com.kasia.controller;

import com.kasia.controller.dto.ElementDTO;
import com.kasia.model.*;
import com.kasia.model.repository.ElementRepository;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.OperationService;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.math.BigDecimal;

import static com.kasia.controller.ViewAndURLController.*;

@Controller
public class ElementController {
    @Autowired
    private BudgetService bService;
    @Autowired
    private MySessionController sessionController;
    @Autowired
    private UserService uService;
    @Autowired
    private OperationService oService;
    @Autowired
    private ElementRepository eRepository;

    @ModelAttribute("elementDTO")
    public ElementDTO getElementDTO() {
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

        return redirect(U_ELEMENT);
    }

    @GetMapping(U_ELEMENT_EDIT + "/{id}")
    public String editProvider(Model model, @PathVariable long id) {
        if (!sessionController.isBudgetOpen()) return redirect(U_BUDGET_ALL);
        ElementDTO dto = getElementDTO();
        Element element = bService.findElementById(dto.getBudgetId(), id);
        dto.setType(element.getType().toString());
        dto.setDefaultPrice(element.getDefaultPrice().getAmount().toString());
        dto.setName(element.getName());
        dto.setOldName(element.getName());
        dto.setId(element.getId());

        User user = sessionController.getUser();
        Budget budget = sessionController.getBudget();
        dto.setCanBeDeleted(uService.isUserOwnBudget(budget.getId(), user.getId())
                && oService.findOperationsByElement(budget.getId(), element.getId()).size() == 0);
        model.addAttribute("elementDTO", dto);
        return V_ELEMENT_EDIT;
    }

    @PostMapping(U_ELEMENT_DELETE)
    public String deleteElement(@ModelAttribute ElementDTO dto) {
        bService.removeElement(dto.getBudgetId(), dto.getId());
        return redirect(U_ELEMENT);
    }

    @PostMapping(U_ELEMENT_UPDATE)
    public String updateElement(@Valid @ModelAttribute ElementDTO dto, BindingResult bResult) {
        if (bResult.hasErrors() && bResult.getErrorCount() > 1) return V_ELEMENT_EDIT;
        else if (bResult.hasErrors() && bResult.getErrorCount() == 1 && !dto.getName().equals(dto.getOldName()))
            return V_ELEMENT_EDIT;

        Budget budget = sessionController.getBudget();
        Element element = bService.findElementById(dto.getBudgetId(), dto.getId());
        element.setName(dto.getName());
        Price price = new Price(new BigDecimal(dto.getDefaultPrice()), budget.getBalance().getCurrencies());
        element.setDefaultPrice(price);
        element.setType(ElementType.valueOf(dto.getType()));
        eRepository.save(element);
        return redirect(U_ELEMENT);
    }
}
