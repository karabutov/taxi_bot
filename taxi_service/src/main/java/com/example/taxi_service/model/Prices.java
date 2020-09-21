package com.example.taxi_service.model;

import lombok.*;

import java.util.List;
import java.util.Map;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Prices {

    private Map<String, List<RidePrice>> prices;
}
