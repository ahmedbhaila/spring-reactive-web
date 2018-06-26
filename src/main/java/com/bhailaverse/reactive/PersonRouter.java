package com.bhailaverse.reactive;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class PersonRouter {

	private PersonHandler personHandler;

	public PersonRouter(PersonHandler personHandler) {
		this.personHandler = personHandler;
	}

	@Bean
	public RouterFunction<?> routes(PersonRepository personRepository) {
		return RouterFunctions.nest(RequestPredicates.path("/person"),

				RouterFunctions
					.route(RequestPredicates.GET("/{id}"), personHandler::findPerson)
					.andRoute(RequestPredicates.method(HttpMethod.POST), personHandler::insertPerson)
					.andRoute(RequestPredicates.DELETE("/{id}"), personHandler::deletePerson)

		);

	}
}
