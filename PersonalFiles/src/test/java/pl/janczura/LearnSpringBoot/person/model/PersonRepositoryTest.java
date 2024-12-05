package pl.janczura.LearnSpringBoot.person.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void idIsSetAfterSave() {
        Person person = new Person(PersonTest.NameCorrectLongMinLim,
                                   PersonTest.SurnameCorrectLongMinLim,
                                   PersonTest.PersonalId);
        assertNull(person.getId());

        Person personSaved = personRepository.save(person);
        assertNotNull(personSaved.getId());
    }

    @Test
    public void findPersonById() {
        Person person = new Person(PersonTest.NameCorrectLongMinLim,
                                   PersonTest.SurnameCorrectLongMinLim,
                                   PersonTest.PersonalId);
        personRepository.save(person);

        Person personRead = personRepository.findById(person.getId()).orElse(null);
        assertNotNull(personRead);
        assertEquals(PersonTest.NameCorrectLongMinLim, personRead.getName());
        assertEquals(PersonTest.SurnameCorrectLongMinLim, personRead.getSurname());
        assertEquals(PersonTest.PersonalId, personRead.getPersonalId());
    }

    @Test
    public void findPersonByName() {
        Person personMin = new Person(PersonTest.NameCorrectLongMinLim,
                                      PersonTest.SurnameCorrectLongMinLim,
                                      PersonTest.PersonalId);
        personRepository.save(personMin);

        Person personMax = new Person(PersonTest.NameCorrectLongMaxLim,
                                      PersonTest.SurnameCorrectLongMaxLim,
                                      PersonTest.PersonalId);
        personRepository.save(personMax);

        List<Person> personList = personRepository.findPersonByName(PersonTest.NameCorrectLongMinLim);
        assertEquals(personList.size(), 1);
        assertTrue(personList.stream().allMatch(p -> p.getName().equals(PersonTest.NameCorrectLongMinLim)));
    }

    @Test
    public void findPersonBySurname() {
        Person personMin = new Person(PersonTest.NameCorrectLongMinLim,
                                      PersonTest.SurnameCorrectLongMinLim,
                                      PersonTest.PersonalId);
        personRepository.save(personMin);

        Person personMax = new Person(PersonTest.NameCorrectLongMaxLim,
                                      PersonTest.SurnameCorrectLongMaxLim,
                                      PersonTest.PersonalId);
        personRepository.save(personMax);

        List<Person> personList = personRepository.findPersonBySurname(PersonTest.SurnameCorrectLongMinLim);
        assertEquals(personList.size(), 1);
        assertTrue(personList.stream().allMatch(p -> p.getSurname().equals(PersonTest.SurnameCorrectLongMinLim)));

    }

    @Test
    public void findPersonByNameAndSurname() {
        Person personMin = new Person(PersonTest.NameCorrectLongMinLim,
                                      PersonTest.SurnameCorrectLongMinLim,
                                      PersonTest.PersonalId);
        personRepository.save(personMin);

        Person personMax = new Person(PersonTest.NameCorrectLongMaxLim,
                                      PersonTest.SurnameCorrectLongMaxLim,
                                      PersonTest.PersonalId);
        personRepository.save(personMax);

        List<Person> personList = personRepository.findPersonByNameAndSurname(PersonTest.NameCorrectLongMinLim,
                                                                              PersonTest.SurnameCorrectLongMinLim);
        assertEquals(personList.size(), 1);
        assertTrue(personList.stream().allMatch(p -> p.getSurname().equals(PersonTest.SurnameCorrectLongMinLim)));

    }

}
