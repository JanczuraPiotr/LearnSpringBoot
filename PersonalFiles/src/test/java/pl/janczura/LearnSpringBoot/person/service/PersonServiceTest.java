package pl.janczura.LearnSpringBoot.person.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.janczura.LearnSpringBoot.person.model.Person;
import pl.janczura.LearnSpringBoot.person.model.PersonRepository;
import pl.janczura.LearnSpringBoot.person.model.PersonTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
public class PersonServiceTest {

    @Autowired
    @InjectMocks
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @Test
    public void deleteById() {
        when(personRepository.existsById(PersonTest.Person1_saved.getId())).thenReturn(true, false);
        assertTrue(personRepository.existsById(PersonTest.Person1_saved.getId()));
        assertDoesNotThrow(() -> personService.deleteById(PersonTest.Person1_saved.getId()));
        assertFalse(personRepository.existsById(PersonTest.Person1_saved.getId()));
    }

    @Test
    public void findAll() {

        List<Person> personsSaved = List.of(PersonTest.Person1_saved,
                                            PersonTest.Person2_saved,
                                            PersonTest.Person3_saved);

        when(personRepository.findAll()).thenReturn(personsSaved);

        List<Person> personsRead = personService.getAll();

        assertEquals(3, personService.getAll().size());
        assertEquals(personsSaved.get(0).getPersonalId(), personsRead.get(0).getPersonalId());
        assertEquals(personsSaved.get(1).getPersonalId(), personsRead.get(1).getPersonalId());
        assertEquals(personsSaved.get(2).getPersonalId(), personsRead.get(2).getPersonalId());
    }

    @Test
    public void getById() {

        when(personRepository.findById(PersonTest.Person1_saved.getId())).thenReturn(java.util.Optional.empty());
        when(personRepository.findById(PersonTest.Person2_saved.getId())).thenReturn(java.util.Optional.of(PersonTest.Person2_saved));

        Optional<Person> personRead1 = personService.getById(PersonTest.Person1_saved.getId());
        assertFalse(personRead1.isPresent());

        Optional<Person> personRead2 = personService.getById(PersonTest.Person2_saved.getId());
        assertNotNull(personRead2);
        assertEquals(PersonTest.Person2_saved.getPersonalId(), personRead2.get().getPersonalId());
    }

    @Test
    public void save() {

        when(personRepository.save(any(Person.class))).thenReturn(PersonTest.Person1_saved);

        Person savedPerson = personService.save(PersonTest.Person1);

        assertNotNull(savedPerson);
        assertEquals(PersonTest.Person1_saved.getName(), savedPerson.getName());
        assertEquals(PersonTest.Person1_saved.getSurname(), savedPerson.getSurname());
        assertEquals(PersonTest.Person1_saved.getPersonalId(), savedPerson.getPersonalId());
    }

    @Test
    public void save_DuplicatePersonalId() {
        Person person1 = PersonTest.Person1;
        Person person2 = PersonTest.Person2;

        when(personRepository.save(person1)).thenReturn(person1);
        when(personRepository.save(person2)).thenThrow(new IllegalArgumentException("Duplicate personalId value"));
    
        personService.save(person1);
        assertThrows(IllegalArgumentException.class, () -> personService.save(person2));
    }

    @Test
    public void update() {
        Person personToUpdate = new Person(PersonTest.Person2.getName(),
                                           PersonTest.Person2.getSurname(),
                                           PersonTest.Person2.getPersonalId());
        Person personUpdated = new Person(PersonTest.Person1_saved.getId(),
                                          PersonTest.Person2.getName(),
                                          PersonTest.Person2.getSurname(),
                                          PersonTest.Person2.getPersonalId());

        when(personRepository.findById(PersonTest.Person1_saved.getId())).thenReturn(Optional.of(PersonTest.Person1_saved));
        when(personRepository.save(personToUpdate)).thenReturn(personUpdated);

        Person personNew = personService.update(PersonTest.Person1_saved.getId(), personToUpdate);

        assertEquals(PersonTest.Person1_saved.getId(), personNew.getId());
        assertEquals(PersonTest.Person2.getName(), personNew.getName());
        assertEquals(PersonTest.Person2.getSurname(), personNew.getSurname());
        assertEquals(PersonTest.Person2.getPersonalId(), personNew.getPersonalId());

//        when(personRepository.save(person2)).thenThrow(new IllegalArgumentException("Duplicate personalId value"));
//
//        personService.save(person1);
//        assertThrows(IllegalArgumentException.class, () -> personService.save(person2));
    }
}