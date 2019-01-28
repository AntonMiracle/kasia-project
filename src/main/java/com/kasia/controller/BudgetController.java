package com.kasia.controller;

import com.kasia.controller.dto.AddBudgetDTO;
import com.kasia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

import static com.kasia.controller.ControllerUrl.BUDGET;
import static com.kasia.controller.ControllerUrl.HOME;

@Controller
@RequestMapping(BUDGET)
public class BudgetController {
    @Autowired
    private AppControllerAdvice appCA;

    @GetMapping
    public String openAddBudget(Model model) {
        model.addAttribute("openAddBudget", "openAddBudget");
        return HOME;
    }

    @ModelAttribute("addBudgetDTO")
    public AddBudgetDTO getAddBudgetDTO() {
        return new AddBudgetDTO();
    }

    @PostMapping("addNew")
    public String addNewBudget(Model model, Principal principal, @Valid @ModelAttribute AddBudgetDTO dto, BindingResult bResult){
        System.out.println(dto);

        if (bResult.hasErrors()) return openAddBudget(model);

        User user = appCA.getAuthenticationUser(principal);


        return HOME;
    }
}
