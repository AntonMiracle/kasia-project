package com.kasia.controller;

import com.kasia.controller.dto.OperationDTO;
import com.kasia.model.*;
import com.kasia.model.service.BalanceService;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.OperationService;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.math.BigDecimal;

import static com.kasia.controller.ViewAndURLController.*;

@Controller
public class OperationController {
    @Autowired
    private MySessionController sessionController;
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private OperationService oService;
    @Autowired
    private UserService uService;
    @Autowired
    private BalanceService balanceService;

    @ModelAttribute("operationDTO")
    public OperationDTO getOperationDTO() {
        OperationDTO session = sessionController.getOperationDTO();
        OperationDTO dto = new OperationDTO(
                session.getElementId(), session.getProviderId()
                , session.getProviderId(), session.getBudgetId()
                , session.getPrice(), session.isStarted());
        return dto;
    }

    @ModelAttribute("operationE")
    public Element getOperationE() {
        if (sessionController.isOperationStarted() && sessionController.getOperationDTO().getElementId() > 0) {
            return budgetService.findElementById(sessionController.getOperationDTO().getBudgetId()
                    , sessionController.getOperationDTO().getElementId());
        } else return new Element();
    }

    @ModelAttribute("operationP")
    public Provider getOperationP() {
        if (sessionController.isOperationStarted() && sessionController.getOperationDTO().getProviderId() > 0) {
            return budgetService.findProviderById(sessionController.getOperationDTO().getBudgetId()
                    , sessionController.getOperationDTO().getProviderId());
        } else return new Provider();
    }

    @GetMapping(U_OPERATION)
    public String openOperation() {
        if (sessionController.isOperationStarted()) {
            sessionController.stopOperation();
            return redirect(U_OPERATION);
        }
        return sessionController.isBudgetOpen() ? V_OPERATION : redirect(U_BUDGET_ALL);
    }

    @GetMapping(U_OPERATION_NEXT)
    public String nextOperationField() {
        return sessionController.isOperationStarted() ? V_OPERATION : redirect(U_OPERATION);
    }

    @GetMapping(U_OPERATION_PICK_PROVIDER + "/{id}")
    public String pickProvider(@PathVariable long id) {
        if (sessionController.isOperationStarted()) sessionController.stopOperation();
        if (sessionController.isBudgetOpen()
                && budgetService.findProviderById(sessionController.getBudget().getId(), id) != null) {
            sessionController.getOperationDTO().setStarted(true);
            sessionController.getOperationDTO().setBudgetId(sessionController.getBudget().getId());
            sessionController.getOperationDTO().setUserId(sessionController.getUser().getId());
            sessionController.getOperationDTO().setProviderId(id);
            return redirect(U_OPERATION_NEXT);
        } else {
            return redirect(U_OPERATION);
        }
    }

    @GetMapping(U_OPERATION_PICK_ELEMENT + "/{id}")
    public String pickElement(@PathVariable long id) {
        OperationDTO dto = sessionController.getOperationDTO();
        if (sessionController.isOperationStarted()
                && dto.getProviderId() > 0
                && dto.getBudgetId() > 0
                && dto.getUserId() > 0
                && budgetService.findElementById(dto.getBudgetId(), id) != null) {
            sessionController.getOperationDTO().setElementId(id);
            Element element = budgetService.findElementById(dto.getBudgetId(), id);
            sessionController.getOperationDTO().setPrice(element.getDefaultPrice().getAmount().toString());
            return redirect(U_OPERATION_NEXT);
        } else {
            return redirect(U_OPERATION);
        }
    }

    @GetMapping(U_OPERATION_WEEK_PREVIOUS)
    public String previousWeek() {
        if (sessionController.isBudgetOpen()) {
            sessionController.getWeekOperationHistory().previous();
            return V_BUDGET;
        } else return redirect(V_BUDGET_ALL);
    }

    @GetMapping(U_OPERATION_WEEK_NEXT)
    public String nextWeek() {
        if (sessionController.isBudgetOpen()) {
            sessionController.getWeekOperationHistory().next();
            return V_BUDGET;
        } else return redirect(V_BUDGET_ALL);
    }

    @PostMapping(U_OPERATION_ADD)
    public String addOperation(@Valid @ModelAttribute OperationDTO dto, BindingResult bResult) {
        if (bResult.hasErrors()) return nextOperationField();

        Provider p = budgetService.findProviderById(dto.getBudgetId(), dto.getProviderId());
        Element e = budgetService.findElementById(dto.getBudgetId(), dto.getElementId());
        User u = uService.findUserById(dto.getUserId());
        Budget b = budgetService.findBudgetById(dto.getBudgetId());

        Price price = balanceService.createPrice(new BigDecimal(dto.getPrice()), b.getBalance().getCurrencies());
        Operation o = oService.createOperation(u, e, p, price);
        o.setDescription(dto.getDescription());

        oService.addOperation(b.getId(), o);
        sessionController.setBudget(budgetService.findBudgetById(b.getId()));
        return redirect(U_BUDGET);
    }

}
