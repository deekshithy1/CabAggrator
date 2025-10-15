package com.cabAggregator.Service;

import com.cabAggregator.DTO.CaptainLoginDTO;
import com.cabAggregator.DTO.CaptainRegistrationDTO;
import com.cabAggregator.DTO.UserDetailsDTO;
import com.cabAggregator.DTO.getLocationDTO;
import com.cabAggregator.Model.Captain;
import com.cabAggregator.Model.Ride;
import com.cabAggregator.Model.User;
import com.cabAggregator.Repo.ICaptainRepository;
import com.cabAggregator.Repo.IRideRepository;
import com.cabAggregator.Repo.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class CaptainService implements  ICaptainService  {

    private final MongoTemplate mongoTemplate;
    @Autowired
    private final ICaptainRepository captainRepository;
    @Autowired
    private final ICaptainService iCaptainService;
    @Autowired
    private  final IUserRepository userRepository;
    @Autowired
    private  final IRideRepository rideRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    UserDetailsDTO Captaiandetails=(UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String captainId=Captaiandetails.id();
    public CaptainService(MongoTemplate mongoTemplate, ICaptainRepository captainRepository, ICaptainService iCaptainService, IUserRepository userRepository, IRideRepository rideRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.mongoTemplate = mongoTemplate;
        this.captainRepository = captainRepository;
        this.iCaptainService = iCaptainService;
        this.userRepository = userRepository;
        this.rideRepository = rideRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Captain registrerCaptain(CaptainRegistrationDTO captainRegistrationDTO) {

        Captain captain=new Captain();
        captain.setEmail(captainRegistrationDTO.email());
        captain.setPassword(passwordEncoder.encode(captainRegistrationDTO.password()));
        captain.setDLNO(captainRegistrationDTO.DLNO());
        captain.setVehicle(captainRegistrationDTO.vehicle());


        return captainRepository.save(captain);
    }

    public Captain Login(CaptainLoginDTO captainLoginDTO){

        try{

            Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(captainLoginDTO.identifier(),captainLoginDTO.password()));



        }
        catch (Exception e){

        }
        return null;
    }

    @Override
    public Ride acceptRide(String rideId) {

        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RuntimeException("Ride not found"));

        if(ride.getCaptain()!=null)
            throw new RuntimeException("Ride is not available");

        Query query = new Query(Criteria.where("id").is(rideId).and("RideStatus").is("REQUESTED"));
        UserDetailsDTO Captaiandetails=(UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String CaptainID=Captaiandetails.id();
        Captain captain = captainRepository.findById(CaptainID).orElseThrow(() -> new RuntimeException("Captain not found"));

        Update update = new Update()
                .set("status", "ACCEPTED")
                .set("captain", captain)
                .set("accepteAt", Instant.now());

        FindAndModifyOptions options = new FindAndModifyOptions()
                .returnNew(true);

        return mongoTemplate.findAndModify(query, update, options, Ride.class);

    }

    @Override
    public Captain getCaptainProfile(String captainId) {

     return captainRepository.findById(captainId).orElseThrow(() -> new RuntimeException("User not found"));

    }

    @Override
    public Ride endRide(String rideId, getLocationDTO getLocationdto) {
        return null;
    }

    @Override
    public Ride cancelRide(String rideId, getLocationDTO getLocationdto) {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RuntimeException("Ride not found"));
        String status = String.valueOf(ride.getRideStatus());
      String userId=ride.getUser().getId();

        String cancelledBy = userId;

        if(status.equals("COMPLETED") || status.equals("CANCELLED"))
            throw new RuntimeException("Ride is already completed or cancelled");

        if(cancelledBy.equals("User")) {

            ride.setRideStatus(Ride.STATUS.valueOf("CANCELLED"));
            ride.setCancelledBy(cancelledBy);

            ride.setFare(null);

            return rideRepository.save(ride);

        }

        ride.setRideStatus(Ride.STATUS.valueOf("PROCESSING"));
        ride.setCaptain(null);
        ride.setAccepteAt(null);

        return rideRepository.save(ride);

    }


}
