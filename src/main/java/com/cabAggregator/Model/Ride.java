package com.cabAggregator.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;



@Document(value = "Ride")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ride {

    @Id
    private String id;
    private String Source;
    private String Destination;
    private BigDecimal fare;
    private STATUS RideStatus;
    private GeoJsonPoint pickUpLocation;
    private GeoJsonPoint dropOffLocation;
    private Instant endTime;
    private String Otp;
    private Double Distance;
    private Instant accepteAt;
    @DBRef
    private User user;
    @DBRef
    private Captain captain;
    private String cancelledBy;
    private String cancellationReason;

    public enum STATUS{
        SEARCHING_FOR_RIDERS_NEARBY,NOT_STARTED,ACCEPTED,CANCELLED,ON_GOING,COMPLETED
    }

}
