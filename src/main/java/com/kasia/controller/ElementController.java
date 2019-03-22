package com.kasia.controller;

import com.kasia.controller.dto.ElementDTO;
import com.kasia.model.Element;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.ElementService;
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
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
            return new TreeSet<>(budgetS.findAllElements(sessionC.getBudget().getId()));
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

    private ElementDTO getElementEdit(long elementId) {
        ElementDTO dto = new ElementDTO();
        if (sessionC.isBudgetOpen()) {
            Element element = elementS.findById(elementId);
            dto.setBudgetId(sessionC.getBudget().getId());
            dto.setCurrency(sessionC.getBudget().getBalance().getCurrency());
            dto.setName(element.getName());
            dto.setOldName(dto.getName());
            dto.setElementId(elementId);
            dto.setPrice(element.getPrice().toString());
            dto.setUserId(sessionC.getUser().getId());
            dto.setCanBeDeleted(budgetS.isElementCanDeleted(sessionC.getBudget().getId(), element.getId()));
        }
        return dto;
    }

    @GetMapping(U_ELEMENT_EDIT + "/{id}")
    public String openEdit(Model model, @PathVariable long id) {
        if (!sessionC.isBudgetOpen()) return openElement();
        model.addAttribute("elementEdit", getElementEdit(id));
        return V_ELEMENT_EDIT;
    }

    @PostMapping(U_ELEMENT_UPDATE)
    public String updateElement(@Valid @ModelAttribute("elementEdit") ElementDTO dto, BindingResult bResult) {
        if (bResult.getErrorCount() > 1 || bResult.getErrorCount() == 1 && !dto.getName().equals(dto.getOldName()))
            return V_ELEMENT_EDIT;

        Element element = elementS.findById(dto.getElementId());
        if (element != null) {
            element.setName(dto.getName());
            element.setPrice(new BigDecimal(dto.getPrice()));
            elementS.save(element);
        }
        return redirect(U_ELEMENT);
    }

    @PostMapping(U_ELEMENT_DELETE)
    public String deletePlace(@ModelAttribute("elementEdit") ElementDTO dto) {
        if (dto.isCanBeDeleted() && dto.getElementId() > 0) {
            budgetS.removeElement(dto.getBudgetId(), dto.getElementId());
        }
        return redirect((U_ELEMENT));
    }
}
