package com.cabAggregator.Repo;

import com.cabAggregator.Model.Captain;
import com.cabAggregator.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICaptainRepository extends MongoRepository<Captain,String> {
}
