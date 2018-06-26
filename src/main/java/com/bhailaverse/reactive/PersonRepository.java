package com.bhailaverse.reactive;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface PersonRepository extends ReactiveMongoRepository<Person, String> {

	@Cacheable
	Flux<Person> findByName(String name);
}
