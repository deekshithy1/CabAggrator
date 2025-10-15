package com.cabAggregator.Service;

import java.util.List;

import com.cabAggregator.DTO.CaptainLoginDTO;
import com.cabAggregator.DTO.CaptainRegistrationDTO;
import com.cabAggregator.Model.Captain;
import org.springframework.stereotype.Service;


@Service
public interface ICaptainService {


    Captain registrerCaptain(CaptainRegistrationDTO captainRegistrationDTO);
    public Captain Login(CaptainLoginDTO captainLoginDTO);
//    Captain registerCaptain(CaptainRegistrationDTO captainRegistrationDTO);
//
//    Captain getCaptainProfile(String captainId);
//
//    Ride acceptRide(String rideId, String captainId);
//
//    List<Ride> findNearByRides(Location location);
//
//    Ride endRide(String rideId, GetLocationDTO getLocationDTO);
//
//    Ride cancelRide(String rideId, CancelRideDTO cancelRideDTO);

}