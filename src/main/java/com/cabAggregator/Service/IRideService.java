package com.cabAggregator.Service;

import com.cabAggregator.DTO.RideDetailsDTO;
import com.cabAggregator.Model.Ride;

public interface IRideService {
  public   Ride createRide(RideDetailsDTO rideDetailsDTO);

    Boolean cancelRide(String rideId);
}
