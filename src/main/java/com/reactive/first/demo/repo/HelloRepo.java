package com.reactive.first.demo.repo;

import com.reactive.first.demo.mdoc.Hello;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelloRepo extends ReactiveMongoRepository<Hello, String> {

}
