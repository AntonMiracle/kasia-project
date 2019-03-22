package com.kasia.controller;

import com.kasia.controller.dto.PlaceAdd;
import com.kasia.model.Place;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static com.kasia.controller.ViewAndURLController.*;

@Controller
public class PlaceController {
    @Autowired
    private MySessionController sessionC;
    @Autowired
    private BudgetService budgetS;
    @Autowired
    private PlaceService placeS;

    @ModelAttribute("placeAdd")
    public PlaceAdd getPlaceAdd() {
        PlaceAdd dto = new PlaceAdd();
        if (sessionC.isBudgetOpen()) {
            dto.setBudgetId(sessionC.getBudget().getId());
            dto.setUserId(sessionC.getUser().getId());
        }
        return dto;
    }

    @ModelAttribute("places")
    public Set<Place> getPlaces() {
        if (sessionC.isBudgetOpen()) {
            return new TreeSet<>(budgetS.findAllPlaces(sessionC.getBudget().getId()));
        }
        return new HashSet<>();
    }

    @GetMapping(U_PLACE)
    public String openPlace() {
        return sessionC.isBudgetOpen() ? V_PLACE : redirect(U_MAIN);
    }

    @PostMapping(U_PLACE_ADD)
    public String addNewPlace(@Valid @ModelAttribute PlaceAdd dto, BindingResult bResult) {
        if (!sessionC.isBudgetOpen()) return openPlace();
        if (bResult.hasErrors()) return openPlace();
        Place place = placeS.save(placeS.convert(dto));
        budgetS.addPlace(dto.getBudgetId(), place.getId());
        return redirect(U_PLACE);
    }


}
