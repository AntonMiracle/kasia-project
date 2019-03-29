package com.kasia.controller;

import com.kasia.controller.dto.OperationDTO;
import com.kasia.model.Element;
import com.kasia.model.Operation;
import com.kasia.model.Place;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.ElementService;
import com.kasia.model.service.OperationService;
import com.kasia.model.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Set;
import java.util.TreeSet;

import static com.kasia.controller.ViewAndURLController.*;

@Controller
public class OperationController {
    @Autowired
    private MySessionController sessionC;
    @Autowired
    private BudgetService budgetS;
    @Autowired
    private PlaceService placeS;
    @Autowired
    private ElementService elementS;
    @Autowired
    private OperationService operationS;

    @ModelAttribute("elements")
    public Set<Element> getElements() {
        if (sessionC.isBudgetOpen()) return budgetS.findAllElements(sessionC.getBudget().getId());
        return new TreeSet<>();
    }

    @ModelAttribute("places")
    public Set<Place> getPlaces() {
        if (sessionC.isBudgetOpen()) return budgetS.findAllPlaces(sessionC.getBudget().getId());
        return new TreeSet<>();
    }

    @GetMapping(U_OPERATION)
    public String openOperation() {
        if (sessionC.isBudgetOpen()) return V_OPERATION;
        return redirect(U_BUDGET);
    }

    @GetMapping(U_OPERATION_INCOME)
    public String startIncome(Model model) {
        if (!sessionC.isBudgetOpen()) return openOperation();
        OperationDTO dto = new OperationDTO();
        dto.setStarted(true);
        dto.setIncome(true);
        sessionC.setOperationAdd(dto);
        model.addAttribute("operationAdd", dto);
        return openOperation();
    }

    @GetMapping(U_OPERATION_PICK_PLACE + "/{id}")
    public String pickPlace(Model model, @PathVariable long id) {
        if (!sessionC.isBudgetOpen()) return openOperation();
        sessionC.getOperationAdd().setPlaceId(id);
        sessionC.getOperationAdd().setPlaceName(placeS.findById(id).getName());
        sessionC.getOperationAdd().setPlaceDescription(placeS.findById(id).getDescription());
        model.addAttribute("operationAdd", sessionC.getOperationAdd());
        return openOperation();
    }

    @GetMapping(U_OPERATION_PICK_ELEMENT + "/{id}")
    public String pickElement(Model model, @PathVariable long id) {
        if (!sessionC.isBudgetOpen()) return openOperation();
        sessionC.getOperationAdd().setElementId(id);
        sessionC.getOperationAdd().setElementName(elementS.findById(id).getName());
        sessionC.getOperationAdd().setPrice(elementS.findById(id).getPrice().toString());
        sessionC.getOperationAdd().setCurrency(sessionC.getBudget().getBalance().getCurrency());
        model.addAttribute("operationAdd", sessionC.getOperationAdd());
        return openOperation();
    }

    @PostMapping(U_OPERATION_ADD)
    public String addOperation(Model model, @Valid @ModelAttribute("operationAdd") OperationDTO dto, BindingResult bResult) {
        dto.setPlaceName(sessionC.getOperationAdd().getPlaceName());
        dto.setPlaceId(sessionC.getOperationAdd().getPlaceId());
        dto.setPlaceDescription(sessionC.getOperationAdd().getPlaceDescription());
        dto.setElementName(sessionC.getOperationAdd().getElementName());
        dto.setElementId(sessionC.getOperationAdd().getElementId());
        dto.setCurrency(sessionC.getOperationAdd().getCurrency());
        dto.setIncome(sessionC.getOperationAdd().isIncome());
        dto.setConsumption(sessionC.getOperationAdd().isConsumption());
        dto.setUserId(sessionC.getUser().getId());
        dto.setBudgetId(sessionC.getBudget().getId());
        if (bResult.hasErrors()) return openOperation();

        Operation operation = operationS.convert(dto);
        operationS.save(operation);
        budgetS.addOperation(dto.getBudgetId(), operation.getId());
        sessionC.setBudget(budgetS.findById(sessionC.getBudget().getId()));

        sessionC.getOperationAdd().setStarted(false);
        return redirect(U_BUDGET);
    }

    @GetMapping(U_OPERATION_CONSUMPTION)
    public String startConsumption(Model model) {
        if (!sessionC.isBudgetOpen()) return openOperation();
        OperationDTO dto = new OperationDTO();
        dto.setStarted(true);
        dto.setConsumption(true);
        sessionC.setOperationAdd(dto);
        model.addAttribute("operationAdd", dto);
        return openOperation();
    }

    @GetMapping(U_OPERATION_EDIT + "/{id}")
    public String editOperation(Model model, @PathVariable long id) {
        if (!sessionC.isBudgetOpen()) return redirect(U_BUDGET);
        model.addAttribute("operationEdit", operationS.findById(id));
        return V_OPERATION_EDIT;
    }

    @PostMapping(U_OPERATION_DELETE)
    public String editOperation(Model model, @ModelAttribute("operationEdit") Operation operation) {
        if (!sessionC.isBudgetOpen()) return redirect(U_BUDGET);
        budgetS.removeOperation(sessionC.getBudget().getId(), operation.getId());
        sessionC.setBudget(budgetS.findById(sessionC.getBudget().getId()));
        return redirect(U_BUDGET);
    }
}
