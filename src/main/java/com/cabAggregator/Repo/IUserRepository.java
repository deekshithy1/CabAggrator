package com.cabAggregator.Repo;

import com.cabAggregator.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IUserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmail(String email);
}
