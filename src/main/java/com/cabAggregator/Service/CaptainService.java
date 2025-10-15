package com.cabAggregator.Service;

import com.cabAggregator.DTO.CaptainLoginDTO;
import com.cabAggregator.DTO.CaptainRegistrationDTO;
import com.cabAggregator.DTO.UserDetailsDTO;
import com.cabAggregator.DTO.getLocationDTO;
import com.cabAggregator.Model.Captain;
import com.cabAggregator.Model.Ride;
import com.cabAggregator.Repo.ICaptainRepository;
import com.cabAggregator.Repo.IRideRepository;
import com.cabAggregator.Repo.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CaptainService implements ICaptainService {

    private final MongoTemplate mongoTemplate;
    private final ICaptainRepository captainRepository;
    private final IRideRepository rideRepository;
    private final IUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTservice jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Captain registrerCaptain(CaptainRegistrationDTO captainRegistrationDTO) {
        Captain captain = new Captain();
        captain.setEmail(captainRegistrationDTO.email());
        captain.setPassword(passwordEncoder.encode(captainRegistrationDTO.password()));
        captain.setDLNO(captainRegistrationDTO.DLNO());
        captain.setVehicle(captainRegistrationDTO.vehicle());
        return captainRepository.save(captain);
    }

    @Override
    public String Login(CaptainLoginDTO captainLoginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            captainLoginDTO.identifier(),
                            captainLoginDTO.password()
                    )
            );

            // Store authentication in context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUsers userDetails = (CustomUsers) authentication.getPrincipal();
            return jwtService.GenerateToken(userDetails);

        } catch (Exception e) {
            throw new RuntimeException("Invalid credentials or authentication failed", e);
        }
    }

    @Override
    public Ride acceptRide(String rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getCaptain() != null)
            throw new RuntimeException("Ride is not available");

        // get current captain from security context
        UserDetailsDTO captainDetails =
                (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String captainId = captainDetails.id();

        Captain captain = captainRepository.findById(captainId)
                .orElseThrow(() -> new RuntimeException("Captain not found"));

        Query query = new Query(Criteria.where("id").is(rideId).and("RideStatus").is("REQUESTED"));
        Update update = new Update()
                .set("status", "ACCEPTED")
                .set("captain", captain)
                .set("accepteAt", Instant.now());

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
        return mongoTemplate.findAndModify(query, update, options, Ride.class);
    }

    @Override
    public Captain getCaptainProfile(String captainId) {
        return captainRepository.findById(captainId)
                .orElseThrow(() -> new RuntimeException("Captain not found"));
    }

    @Override
    public Ride endRide(String rideId, getLocationDTO getLocationdto) {
        // implement ride end logic later
        return null;
    }

    @Override
    public Ride cancelRide(String rideId, getLocationDTO getLocationdto) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        String status = String.valueOf(ride.getRideStatus());
        String userId = ride.getUser().getId();

        if (status.equals("COMPLETED") || status.equals("CANCELLED"))
            throw new RuntimeException("Ride is already completed or cancelled");

        ride.setRideStatus(Ride.STATUS.valueOf("CANCELLED"));
        ride.setCancelledBy(userId);
        ride.setFare(null);

        return rideRepository.save(ride);
    }
}
