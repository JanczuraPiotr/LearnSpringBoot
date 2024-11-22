package pl.janczura.LearnSpringBoot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import pl.janczura.LearnSpringBoot.person.model.Person;
import pl.janczura.LearnSpringBoot.person.service.PersonService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Autowired
    @InjectMocks
    private PersonService personService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void shouldThrowExceptionWhenPersonIsInvalid() {
        Person invalidPerson = new Person(null, "123456");

        assertThrows(IllegalArgumentException.class, () -> {
            personService.validatePerson(invalidPerson);
        });
    }

    @Test
    public void shouldThrowExceptionWhenPersonIsToShort() {
        Person invalidPerson = new Person("12", "1234");

        assertThrows(IllegalArgumentException.class, () -> {
            personService.validatePerson(invalidPerson);
        });
    }

    @Test
    public void shouldThrowExceptionWhenPersonIsToLong() {
        Person invalidPerson = new Person("123456", "1234");

        assertThrows(IllegalArgumentException.class, () -> {
            personService.validatePerson(invalidPerson);
        });
    }

    @Test
    public void shouldPassValidationWhenPersonIsValid() {
        Person validPerson = new Person("123456", "12345");

        assertDoesNotThrow(() -> {
            personService.validatePerson(validPerson);
        });
    }
}