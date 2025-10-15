package com.cabAggregator.Repo;

import com.cabAggregator.Model.Ride;
import com.cabAggregator.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IRideRepository extends MongoRepository<Ride,String> {
}
