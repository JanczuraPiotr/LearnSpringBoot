package pl.janczura.LearnSpringBoot.person.model;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@DataJpaTest
public class PersonRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(PersonRepositoryTest.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void clearDatabase() {
        jdbcTemplate.execute("TRUNCATE TABLE person" );
    }

    @Test
    public void findAll() {
        personRepository.save(PersonTest.Person1);
        personRepository.save(PersonTest.Person2);
        personRepository.save(PersonTest.Person3);

        assertEquals(3, personRepository.findAll().size());

        assertEquals(PersonTest.Person1.getPersonalId(), personRepository.findAll().get(0).getPersonalId());
        assertEquals(PersonTest.Person2.getPersonalId(), personRepository.findAll().get(1).getPersonalId());
        assertEquals(PersonTest.Person3.getPersonalId(), personRepository.findAll().get(2).getPersonalId());
    }

    @Test
    public void findPersonById() {
        assertEquals(0, personRepository.findAll().size());

        Person savedPerson = personRepository.save(PersonTest.Person1);
        Optional<Person> personOptional = personRepository.findById(savedPerson.getId());

        assertTrue(personOptional.isPresent());
        Person personRead = personOptional.get();

        assertEquals(savedPerson.getName(), personRead.getName());
        assertEquals(savedPerson.getSurname(), personRead.getSurname());
        assertEquals(savedPerson.getPersonalId(), personRead.getPersonalId());
    }

    @Test
    public void findPersonByName() {
        personRepository.save(PersonTest.Person1);
        personRepository.save(PersonTest.Person2);
        personRepository.save(PersonTest.Person3);

        List<Person> personList = personRepository.findPersonByName(PersonTest.Name_Correct_LongMin);
        assertEquals(1, personList.size());
        assertTrue(personList.stream().allMatch(p -> p.getName().equals(PersonTest.Name_Correct_LongMin)));
    }

    @Test
    public void findPersonBySurname() {
        personRepository.save(PersonTest.Person1);
        personRepository.save(PersonTest.Person2);
        personRepository.save(PersonTest.Person3);

        List<Person> personList = personRepository.findPersonBySurname(PersonTest.Surname_Correct_LongMin);
        assertEquals(1, personList.size());
        assertTrue(personList.stream().allMatch(p -> p.getSurname().equals(PersonTest.Surname_Correct_LongMin)));
    }

    @Test
    public void findPersonByNameAndSurname() {
        personRepository.save(PersonTest.Person1);
        personRepository.save(PersonTest.Person2);
        personRepository.save(PersonTest.Person3);

        List<Person> personList = personRepository.findPersonByNameAndSurname(PersonTest.Name_Correct_LongMin,
                                                                              PersonTest.Surname_Correct_LongMin);
        assertEquals(1, personList.size());
        assertTrue(personList.stream().allMatch(p -> p.getSurname().equals(PersonTest.Surname_Correct_LongMin)));

        List<Person> noPersons = personRepository.findPersonByNameAndSurname(PersonTest.Name_Correct_LongMin,
                                                                             PersonTest.Surname_Correct_LongMax);
        assertEquals(0, noPersons.size());
    }

    @Test
    public void save() {
        Person personNew = PersonTest.Person1;
        Person personSaved = personRepository.save(personNew);

        assertNotNull(personSaved.getId());
        assertEquals(personNew.getName(), personSaved.getName());
        assertEquals(personNew.getSurname(), personSaved.getSurname());
        assertEquals(personNew.getPersonalId(), personSaved.getPersonalId());
    }

    @Test
    public void save_DuplicatePersonalId() {
        personRepository.save(PersonTest.Person1);
        Person person2 = new Person(PersonTest.Person2.getName(),
                                    PersonTest.Person2.getSurname(),
                                    PersonTest.Person1.getPersonalId());

        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> personRepository.save(person2));
        assertTrue(exception.getMessage().contains("could not execute statement"));
    }

    @Test
    public void update() {
        // TODO przetestować pojawianie się właściwych logów
        assertFalse(true, "Not implemented yet.");
    }

    @Test
    public void update_DuplicatePersonalId() {
        // TODO przetestować pojawianie się właściwych logów
        assertFalse(true, "Not implemented yet.");
    }

    @Test
    public void update_NonExistingPerson() {
        // TODO przetestować pojawianie się właściwych logów
        assertFalse(true, "Not implemented yet.");
    }


}
