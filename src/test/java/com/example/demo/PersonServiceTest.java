package com.example.demo;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.bhailaverse.reactive.Person;
import com.bhailaverse.reactive.PersonRepository;
import com.bhailaverse.reactive.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)

public class PersonServiceTest {

	PersonService personService;
	PersonRepository personRepo;

	// @Before
	// public void init() {
	// PersonRepository personRepo = mock(PersonRepository.class);
	// personService = new PersonService(personRepo);
	// }

	@Test
	public void testForFindPersonById() {

		// assertTrue("BELSOON".equalsIgnoreCase("belsoon"));
		assertTrue("BelsOon".equalsIgnoreCase("BELSOON"));

		PersonRepository personRepo = mock(PersonRepository.class);
		personService = new PersonService(personRepo);

		Person p = new Person();
		p.setId("test");
		p.setName("homer");
		Mono<String> anyString = any();
		given(personRepo.findById(anyString)).willReturn(Mono.just(p));
		// given(personService.findPerson(any())).willReturn(Mono.just(p));

		Person p2 = personService.findPerson(Mono.just("test")).block();

		assertTrue(p2.getId().equals("test"));
		assertTrue(p2.getName().equals("homer"));
	}

	@Test
	public void testForInsertPerson() {

		PersonRepository personRepo = mock(PersonRepository.class);
		personService = new PersonService(personRepo);

		Person p = new Person();
		p.setId("test");
		p.setName("homer");

		Mono<Person> anyPerson = any();
		given(personRepo.insert(anyPerson)).willReturn(Flux.just(p));

		Person p2 = personService.insertPerson(Mono.just(p)).block();

		assertTrue(p2.getId().equals("test"));
		assertTrue(p2.getName().equals("homer"));
	}

	@Test
	public void testForDeletePerson() {

		PersonRepository personRepo = mock(PersonRepository.class);
		personService = new PersonService(personRepo);

		Person p = new Person();
		p.setId("test");
		p.setName("homer");

		Mono<String> anyString = any();
		given(personRepo.deleteById(anyString)).willReturn(Mono.empty());

		Void v = personService.deletePerson(Mono.just("test")).block();
		assertTrue(v == null);
	}

}
