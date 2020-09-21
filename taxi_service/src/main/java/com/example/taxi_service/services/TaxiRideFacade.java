package com.example.taxi_service.services;


import com.example.taxi_service.model.Coordinates;
import com.example.taxi_service.model.Prices;
import com.example.taxi_service.model.RidePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TaxiRideFacade {

    private final List<TaxiService> taxiServices;

    public TaxiRideFacade(List<TaxiService> taxiServices) {
        this.taxiServices = taxiServices;
    }

    public Prices aggregateRidePrice(Coordinates startPoint, Coordinates endPoint){
        Map<String, List<RidePrice>> ridePricesMap = new HashMap<>();
        for (TaxiService taxiService : taxiServices) {
            List<RidePrice> ridePrices = taxiService.getRideInfo(startPoint, endPoint);
            if(!ridePrices.isEmpty() && ridePrices.get(0).getAggregator() != null) {
                ridePricesMap.put(ridePrices.get(0).getAggregator().name(), ridePrices);
            }
        }
        return Prices.builder().prices(ridePricesMap).build();
    }

    private String ridePricesMapToString(Map<String, List<RidePrice>> ridePriceMap) {
        StringBuilder res = new StringBuilder();
        for (String agr : ridePriceMap.keySet()) {
            List<RidePrice> ridePrices = ridePriceMap.get(agr);
            res.append("\n").append(agr).append("\n");
            for (RidePrice ridePrice : ridePrices) {
                res.append(ridePrice.getClassTaxi()).append(" ").append(ridePrice.getPrice()).append("\n");
            }
        }
        return String.valueOf(res);
    }

}
