package com.kasia.controller;

import com.kasia.controller.dto.PlaceDTO;
import com.kasia.model.Place;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static com.kasia.controller.ViewAndURLController.*;

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PlaceController {
    @Autowired
    private MySessionController sessionC;
    @Autowired
    private BudgetService budgetS;
    @Autowired
    private PlaceService placeS;

    @ModelAttribute("placeAdd")
    public PlaceDTO getPlaceAdd() {
        PlaceDTO dto = new PlaceDTO();
        if (sessionC.isBudgetOpen()) {
            dto.setBudgetId(sessionC.getBudget().getId());
        }
        return dto;
    }

    private PlaceDTO getPlaceEdit(long placeId) {
        PlaceDTO dto = new PlaceDTO();
        Place place = placeS.findById(placeId);
        if (sessionC.isBudgetOpen() && place != null) {
            dto.setName(place.getName());
            dto.setDescription(place.getDescription());
            dto.setBudgetId(sessionC.getBudget().getId());
            dto.setUserId(sessionC.getUser().getId());
            dto.setCanBeDeleted(budgetS.isPlaceCanDeleted(sessionC.getBudget().getId(), placeId));
            dto.setOldDescription(place.getDescription());
            dto.setOldName(place.getName());
            dto.setPlaceId(placeId);
        }
        return dto;
    }

    @GetMapping(U_PLACE)
    public String openPlace() {
        return sessionC.isBudgetOpen() ? V_PLACE : redirect(U_MAIN);
    }

    @ModelAttribute("places")
    public Set<Place> getPlaces() {
        if (sessionC.isBudgetOpen()) {
            return new TreeSet<>(budgetS.findAllPlaces(sessionC.getBudget().getId()));
        }
        return new HashSet<>();
    }

    @PostMapping(U_PLACE_ADD)
    public String addNewPlace(@Valid @ModelAttribute("placeAdd") PlaceDTO dto, BindingResult bResult) {
        if (!sessionC.isBudgetOpen()) return openPlace();
        if (bResult.hasErrors()) return openPlace();
        Place place = placeS.save(placeS.convert(dto));
        budgetS.addPlace(dto.getBudgetId(), place.getId());
        return redirect(U_PLACE);
    }

    @GetMapping(U_PLACE_EDIT + "/{id}")
    public String openEdit(Model model, @PathVariable long id) {
        if (!sessionC.isBudgetOpen()) return openPlace();
        model.addAttribute("placeEdit", getPlaceEdit(id));
        return V_PLACE_EDIT;
    }

    @PostMapping(U_PLACE_UPDATE)
    public String updatePlace(@Valid @ModelAttribute("placeEdit") PlaceDTO dto, BindingResult bResult) {
        int errorCount = bResult.getErrorCount();
        if (errorCount > 1 || errorCount == 1 && !dto.getName().equals(dto.getOldName()))
            return V_PLACE_EDIT;

        Place place = placeS.findById(dto.getPlaceId());
        if (place != null) {
            place.setName(dto.getName());
            place.setDescription(dto.getDescription());
            placeS.save(place);
        }
        return redirect(U_PLACE);
    }

    @PostMapping(U_PLACE_DELETE)
    public String deletePlace(@ModelAttribute("placeEdit") PlaceDTO dto) {
        if (dto.isCanBeDeleted() && dto.getPlaceId() > 0) {
            budgetS.removePlace(dto.getBudgetId(),dto.getPlaceId());
        }
        return redirect((U_PLACE));
    }
}
