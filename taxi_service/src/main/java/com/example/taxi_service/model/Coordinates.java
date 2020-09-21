package com.example.taxi_service.model;

import lombok.Builder;
import lombok.Getter;

import javax.swing.*;

@Builder
@Getter
public class Coordinates {

    private final String latitude;

    private final String longitude;


}
