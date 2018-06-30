package com.bhailaverse.reactive;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RunWith(SpringRunner.class)
@WebFluxTest
@ContextConfiguration(classes = {PersonRouter.class, PersonHandler.class})
public class SpringReactiveApplicationTest {
	
	WebTestClient testClient;
	
	@Autowired
    private ApplicationContext context;
	
	@MockBean
	PersonRepository personRepository;
	
	@SpyBean
	PersonService personService;
	
	@Before
	public void setup() {
		testClient = WebTestClient.bindToApplicationContext(context).build();
	}
	
	@Test
	public void getPersonTest() {
		
		Person person = new Person();
		person.setId("1");
		person.setName("foo");
		
		Mono<String> anyString = any();
		given(personRepository.findById(anyString)).willReturn(Mono.just(person));
				
		testClient.get()
				.uri("/person/{id}", "1")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Person.class)
				.isEqualTo(person);
	}
	
	@Test
	public void getPersonNotFoundTest() {
		
		Mono<String> anyString = any();
		given(personRepository.findById(anyString)).willReturn(Mono.empty());
				
		testClient.get()
				.uri("/person/{id}", "1")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound();
	}
	
	@Test
	public void insertPerson() {
		
		Person person = new Person();
		person.setId("1");
		person.setName("new");
		
		Mono<Person> anyPerson = any();
		given(personRepository.insert(anyPerson)).willReturn(Flux.just(person));
				
		testClient.post()
				.uri("/person")
				.body(Mono.just(person), Person.class)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Person.class);
				//.isEqualTo(person);
	}
}
