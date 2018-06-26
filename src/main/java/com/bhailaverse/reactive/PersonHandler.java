package com.bhailaverse.reactive;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component

public class PersonHandler {
	private PersonService personService;

	public PersonHandler(PersonService personService) {
		this.personService = personService;
	}

	public Mono<ServerResponse> findPerson(ServerRequest request) {
		return personService
				.findPerson(Mono.just(request.pathVariable("id")))
						.flatMap(person -> ServerResponse.ok()
								.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromObject(person)))
						.switchIfEmpty(ServerResponse.notFound().build());
						

	}

	public Mono<ServerResponse> insertPerson(ServerRequest request) {
		return personService.insertPerson(request.bodyToMono(Person.class))
				.flatMap(person -> ServerResponse.ok().build());
	}

	public Mono<ServerResponse> deletePerson(ServerRequest request) {
		return personService.deletePerson(Mono.just(request.pathVariable("id")))
				.flatMap(response -> ServerResponse.ok().build());
	}
}
