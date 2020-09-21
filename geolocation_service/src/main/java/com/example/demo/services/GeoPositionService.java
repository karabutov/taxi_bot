package com.example.demo.services;

import com.example.demo.model.Coordinates;

public interface GeoPositionService {
    Coordinates getCoordinates(String address);

}
