package com.example.taxi_service.model;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RideCoordinates {

    private Coordinates startPoint;

    private Coordinates endPoint;
    

}