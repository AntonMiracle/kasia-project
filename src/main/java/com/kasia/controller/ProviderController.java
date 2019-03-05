package com.kasia.controller;

import com.kasia.controller.dto.ProviderDTO;
import com.kasia.model.Provider;
import com.kasia.model.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static com.kasia.controller.ViewNameAndControllerURL.*;

@Controller
public class ProviderController {
    @Autowired
    private BudgetService bService;
    @Autowired
    private MySessionController sessionController;

    @ModelAttribute("providerDTO")
    public ProviderDTO getAddBudgetDTO() {
        return new ProviderDTO();
    }

    @GetMapping(U_PROVIDER)
    public String openProvider(Model model) {
        return V_PROVIDER;
    }

    @PostMapping(U_PROVIDER_ADD)
    public String addNewBudget(Model model, @Valid @ModelAttribute ProviderDTO dto, BindingResult bResult) {
        if (bResult.hasErrors()) return openProvider(model);

        Provider provider = new Provider(dto.getName(), dto.getDescription());
        System.out.println(provider);
        System.out.println(dto.getBudgetId());
        bService.addElementProvider(dto.getBudgetId(), provider);

        return redirect(U_PROVIDER);
    }
}
