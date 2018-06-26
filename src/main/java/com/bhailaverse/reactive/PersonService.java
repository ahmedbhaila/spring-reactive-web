package com.bhailaverse.reactive;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class PersonService {
	private PersonRepository personRepository;

	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public Mono<Person> findPerson(Mono<String> personId) {
		return personRepository.findById(personId);
	}

	public Mono<Person> insertPerson(Mono<Person> person) {
		return personRepository.insert(person).next();
	}

	public Mono<Void> deletePerson(Mono<String> personId) {
		return personRepository.deleteById(personId);
	}
}
