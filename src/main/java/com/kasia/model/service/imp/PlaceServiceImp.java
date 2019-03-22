package com.kasia.model.service.imp;

import com.kasia.controller.dto.PlaceAdd;
import com.kasia.model.Place;
import com.kasia.model.repository.PlaceRepository;
import com.kasia.model.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlaceServiceImp implements PlaceService {
    @Autowired
    private PlaceRepository placeR;

    @Override
    public Place save(Place place) {
        return placeR.save(place);
    }

    @Override
    public Place findById(long placeId) {
        return placeR.findById(placeId).orElse(null);
    }

    @Override
    public Place convert(PlaceAdd dto) {
        return new Place(dto.getName(), dto.getDescription());
    }
}
