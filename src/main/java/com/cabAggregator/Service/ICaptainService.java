package com.cabAggregator.Service;

import java.util.List;

import com.cabAggregator.DTO.CaptainLoginDTO;
import com.cabAggregator.DTO.CaptainRegistrationDTO;
import com.cabAggregator.DTO.getLocationDTO;
import com.cabAggregator.Model.Captain;
import com.cabAggregator.Model.Ride;
import org.springframework.stereotype.Service;


@Service
public interface ICaptainService {


    Captain registrerCaptain(CaptainRegistrationDTO captainRegistrationDTO);
     String Login(CaptainLoginDTO captainLoginDTO);

    Ride acceptRide(String rideId);
    Captain getCaptainProfile(String captainId);
    Ride endRide(String rideId,getLocationDTO getLocationdto);
    Ride cancelRide(String rideId,getLocationDTO getLocationdto);


    List<Captain> getallCaptains();
}