package com.cabAggregator.Service;

import com.cabAggregator.DTO.RideDetailsDTO;
import com.cabAggregator.DTO.UserDetailsDTO;
import com.cabAggregator.Model.Ride;
import com.cabAggregator.Model.User;
import com.cabAggregator.Repo.IRideRepository;
import com.cabAggregator.Repo.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RideService  implements  IRideService{

    @Autowired
    private  JWTservice jwTservice;
    @Autowired
    private IRideRepository rideRepository;
    @Autowired
    private IUserRepository userRepository;

    public RideService(JWTservice jwTservice, IRideRepository rideRepository, IUserRepository userRepository) {
        this.jwTservice = jwTservice;
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Ride createRide(RideDetailsDTO rideDetailsDTO) {

        Ride ride=new Ride();
        ride.setPickUpLocation(new GeoJsonPoint(rideDetailsDTO.pickUplatitude(),rideDetailsDTO.pickupLongitude()));
        ride.setDropOffLocation(new GeoJsonPoint(rideDetailsDTO.dropLatitude(),rideDetailsDTO.dropLongitude()));
        ride.setSource(rideDetailsDTO.PickUPString());
        ride.setDestination(rideDetailsDTO.DropString());
        ride.setDistance(rideDetailsDTO.distance());
        ride.setRideStatus(Ride.STATUS.SEARCHING_FOR_RIDERS_NEARBY);
//        UserDetailsDTO userdto=jwTservice.extractUserDetails(request.getHeader("Authorization").substring(7));
        UserDetailsDTO userDto = (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDto.id();

        Optional<User> user= userRepository.findById(userId);
        if (user.isPresent()) {
            User Loguser = user.get();
            Loguser.setPassword(null);

            ride.setUser(Loguser);
        }


        rideRepository.save(ride);


        return ride;
    }

    @Override
    public Boolean cancelRide(String rideId) {
        Optional<Ride> ride=rideRepository.findById("68efc5088ad6895718fe8317");
        if(!ride.isPresent()){
             throw new RuntimeException("No ride found");
        }

        Ride ride1=ride.get();
        if(ride1.getRideStatus()!= Ride.STATUS.COMPLETED)
        ride1.setRideStatus(Ride.STATUS.CANCELLED);

        rideRepository.save(ride1);
        return true;
    }
}
