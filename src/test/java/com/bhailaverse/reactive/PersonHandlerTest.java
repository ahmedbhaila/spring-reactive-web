package com.bhailaverse.reactive;

import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bhailaverse.reactive.Person;
import com.bhailaverse.reactive.PersonHandler;
import com.bhailaverse.reactive.PersonService;

import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
public class PersonHandlerTest {

	PersonHandler personHandler = null;
	PersonService personService = null;
	Person person = null;

	@Before
	public void init() {
		person = new Person();
		person.setId("test");
		person.setName("homer");
		personService = mock(PersonService.class);
		given(personService.findPerson(any())).willReturn(Mono.just(person));
		personHandler = new PersonHandler(personService);
	}

	@Test
	public void testForFindPerson() {
		ServerRequest request = mock(ServerRequest.class);
		given(request.pathVariable(any())).willReturn("test");
		ServerResponse response = personHandler.findPerson(request).block();
		assertTrue(response.statusCode().equals(HttpStatus.OK));
	}

	@Test
	public void testForPersonNotFound() {
		ServerRequest request = mock(ServerRequest.class);
		given(request.pathVariable(any())).willReturn("test");
		given(personService.findPerson(any())).willReturn(Mono.empty());
		ServerResponse response = personHandler.findPerson(request).block();
		assertTrue(response.statusCode().equals(HttpStatus.NOT_FOUND));
	}
	
//	@Test
//	public void testForInsertPerson() {
//		ServerRequest request = mock(ServerRequest.class);
//		given(request.bodyToMono(Person.class)).willReturn(Mono.just(person));
//		ServerResponse response = personHandler.insertPerson(request).block();
//		assertTrue(response.statusCode().equals(HttpStatus.CREATED));
//	}
}
