package com.kasia.controller;

import com.kasia.controller.dto.ProviderDTO;
import com.kasia.model.Budget;
import com.kasia.model.Provider;
import com.kasia.model.User;
import com.kasia.model.repository.ProviderRepository;
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

import static com.kasia.controller.ViewNameAndControllerURL.*;

@Controller
public class ProviderController {
    @Autowired
    private BudgetService bService;
    @Autowired
    private UserService uService;
    @Autowired
    private OperationService oService;
    @Autowired
    private ProviderRepository pRepository;
    @Autowired
    private MySessionController sessionController;

    @ModelAttribute("providerDTO")
    public ProviderDTO getProviderDTO() {
        ProviderDTO dto = new ProviderDTO();
        if (sessionController.isBudgetOpen()) dto.setBudgetId(sessionController.getBudget().getId());
        return dto;
    }

    @GetMapping(U_PROVIDER)
    public String openProvider() {
        return sessionController.isBudgetOpen() ? V_PROVIDER : redirect(U_BUDGET_ALL);
    }

    @PostMapping(U_PROVIDER_ADD)
    public String addNewProvider(@Valid @ModelAttribute ProviderDTO dto, BindingResult bResult) {
        if (bResult.hasErrors()) return openProvider();

        Provider provider = new Provider(dto.getName(), dto.getDescription());
        bService.addProvider(dto.getBudgetId(), provider);

        return redirect(U_PROVIDER);
    }

    @PostMapping(U_PROVIDER_UPDATE)
    public String updateProvider(@Valid @ModelAttribute ProviderDTO dto, BindingResult bResult) {
        if (bResult.hasErrors()) return V_PROVIDER_EDIT;
        Provider provider = bService.findProviderByName(dto.getBudgetId(), dto.getOldName());
        provider.setName(dto.getName());
        provider.setDescription(dto.getDescription());
        pRepository.save(provider);
        return redirect(U_PROVIDER);
    }

    @PostMapping(U_PROVIDER_DELETE)
    public String deleteProvider(@ModelAttribute ProviderDTO dto) {
        Provider provider = bService.findProviderByName(dto.getBudgetId(), dto.getOldName());
        bService.removeProvider(dto.getBudgetId(), provider.getId());
        return redirect(U_PROVIDER);
    }

    @GetMapping(U_PROVIDER_EDIT + "/{id}")
    public String editProvider(Model model, @PathVariable long id) {
        if (!sessionController.isBudgetOpen()) return redirect(U_BUDGET_ALL);
        ProviderDTO dto = getProviderDTO();
        Provider provider = bService.findProviderById(dto.getBudgetId(), id);
        dto.setName(provider.getName());
        dto.setOldName(provider.getName());
        dto.setDescription(provider.getDescription());
        User user = sessionController.getUser();
        Budget budget = sessionController.getBudget();
        dto.setCanBeDeleted(uService.isUserOwnBudget(budget.getId(), user.getId())
                && oService.findOperationsByProvider(budget.getId(), provider.getId()).size() == 0);
        model.addAttribute("providerDTO", dto);
        return V_PROVIDER_EDIT;
    }
}
