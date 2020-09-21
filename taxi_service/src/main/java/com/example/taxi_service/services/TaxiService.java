package com.example.taxi_service.services;


import com.example.taxi_service.model.Coordinates;
import com.example.taxi_service.model.RidePrice;

import java.util.List;

public interface TaxiService {
    List<RidePrice> getRideInfo(Coordinates startPoint, Coordinates endPoint);
}
