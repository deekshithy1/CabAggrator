package com.cabAggregator.Repo;

import com.cabAggregator.Model.Captain;
import com.cabAggregator.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ICaptainRepository extends MongoRepository<Captain,String> {
    Optional<Captain> findByEmail(String email);
}
