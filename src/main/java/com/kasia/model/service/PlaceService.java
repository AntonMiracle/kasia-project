package com.kasia.model.service;

import com.kasia.controller.dto.PlaceDTO;
import com.kasia.model.Place;

public interface PlaceService {
    Place save(Place place);

    Place findById(long placeId);

    Place convert(PlaceDTO dto);

    boolean delete(long placeId);
}
