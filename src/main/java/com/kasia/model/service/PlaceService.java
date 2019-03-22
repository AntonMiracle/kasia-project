package com.kasia.model.service;

import com.kasia.controller.dto.PlaceAdd;
import com.kasia.model.Place;

public interface PlaceService {
    Place save(Place place);

    Place findById(long placeId);

    Place convert(PlaceAdd dto);
}
