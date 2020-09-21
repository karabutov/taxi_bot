package com.example.taxi_service.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ride {

    @Id
    @GeneratedValue
    private int id;

    private String pickupPoint;
    private String endPoint;

    private LocalDate dateDepart;

    @OneToMany(mappedBy = "ride", fetch = FetchType.EAGER)
    private List<RidePrice> ridePrices;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private User user;

}