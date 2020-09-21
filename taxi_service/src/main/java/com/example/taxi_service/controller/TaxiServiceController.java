package com.example.taxi_service.controller;

import com.example.taxi_service.model.Prices;
import com.example.taxi_service.model.RideCoordinates;
import com.example.taxi_service.services.TaxiRideFacade;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.taxi_service.model.Coordinates;

@RestController
public class TaxiServiceController{

    private final TaxiRideFacade taxiRideFacade;

    public TaxiServiceController(TaxiRideFacade taxiRideFacade) {
        this.taxiRideFacade = taxiRideFacade;
    }


    @RequestMapping(value = "/getPrices", produces = "application/json")
    public Prices getPrices(@RequestBody RideCoordinates rideCoordinates) {
        return taxiRideFacade.aggregateRidePrice(rideCoordinates.getStartPoint(), 
                rideCoordinates.getEndPoint());
    }
}


