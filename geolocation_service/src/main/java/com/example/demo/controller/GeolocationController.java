package com.example.demo.controller;


import com.example.demo.model.Address;
import com.example.demo.model.Coordinates;
import com.example.demo.services.GeoPositionService;
import com.example.demo.services.YandexGeoPositionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeolocationController {

    private final GeoPositionService yandexGeoPositionService;

    public GeolocationController(GeoPositionService yandexGeoPositionService) {
        this.yandexGeoPositionService = yandexGeoPositionService;
    }

    @RequestMapping(value = "/getCoordinates", produces = "application/json")
    public Coordinates getCoordinates(@RequestBody Address address) {
        return yandexGeoPositionService.getCoordinates(address.getAddress());
    }
}


