package pl.janczura.LearnSpringBoot.service;

import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import pl.janczura.LearnSpringBoot.person.model.Person;
import pl.janczura.LearnSpringBoot.person.service.PersonService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Test written for educational purposes.
// 1. Is it possible to test compliance with constraints set for an entity somewhere other than in the controller?.
// 2. How to read and use restrictions set by annotations.
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Autowired
    @InjectMocks
    private PersonService personService;

    @BeforeAll
    public static void setUp() {
        limitations();
    }

    private static String NameToShort;
    private static String NameCorrectLongMinLim;
    private static String NameCorrectLongMaxLim;
    private static String NameToLong;
    private static String SurnameToShort;
    private static String SurnameCorrectLongMinLim;
    private static String SurnameCorrectLongMaxLim;
    private static String SurnameToLong;

    @Test
    public void personIsOk() {
        {
            Person person = new Person(NameCorrectLongMinLim, SurnameCorrectLongMaxLim);

            assertDoesNotThrow( () -> {
                personService.validatePerson(person);
            });

        }
        {
            Person person = new Person(NameCorrectLongMaxLim, SurnameCorrectLongMinLim);

            assertDoesNotThrow( () -> {
                personService.validatePerson(person);
            });

        }
    }

    @Test
    public void personNameIsNull() {
        Person person = new Person(null, SurnameCorrectLongMinLim);

        assertThrows(IllegalArgumentException.class, () -> {
            personService.validatePerson(person);
        });
    }

    @Test
    public void personSurnameIsNull() {
        Person person = new Person(NameCorrectLongMinLim, null);

        assertThrows(IllegalArgumentException.class, () -> {
            personService.validatePerson(person);
        });
    }

    @Test
    public void personNameIsToShort() {
        Person person = new Person(NameToShort, SurnameCorrectLongMaxLim);
        assertThrows(IllegalArgumentException.class, () -> {
            personService.validatePerson(person);
        });
    }

    @Test
    public void personSurnameIsToShort() {
        Person person = new Person(NameCorrectLongMaxLim, SurnameToShort);

        assertThrows(IllegalArgumentException.class, () -> {
            personService.validatePerson(person);
        });
    }

    @Test
    public void personNameIsToLong() {
        Person person = new Person(NameToLong, SurnameCorrectLongMaxLim);

        assertThrows(IllegalArgumentException.class, () -> {
            personService.validatePerson(person);
        });
    }

    @Test
    public void personSurnameIsToLong() {
        Person person = new Person(NameCorrectLongMaxLim, SurnameToLong);

        assertThrows(IllegalArgumentException.class, () -> {
            personService.validatePerson(person);
        });
    }

    private static void limitations() {
        Class<Person> clazz = Person.class;
        try {
            Field fieldName = clazz.getDeclaredField("name");
            Annotation annotation = fieldName.getAnnotation(Size.class);
            Size size =(Size)annotation;

            if(size.min() > 0) {
                NameToShort = "n".repeat(size.min() - 1);
                NameCorrectLongMinLim = "n".repeat(size.min());
                if(size.min() < size.max()) {
                    NameCorrectLongMaxLim = "n".repeat(size.max());
                    NameToLong = "n".repeat(size.max() + 1);
                }
            }

        } catch (NoSuchFieldException e) {
            // ...
        }

        try {
            Field fieldSurname = clazz.getDeclaredField("surname");
            Annotation annotation = fieldSurname.getAnnotation(Size.class);
            Size size =(Size)annotation;

            if(size.min() > 0) {
                SurnameToShort = "s".repeat(size.min() - 1);
                SurnameCorrectLongMinLim = "s".repeat(size.min());
                if(size.min() < size.max()) {
                    SurnameCorrectLongMaxLim = "s".repeat(size.max());
                    SurnameToLong = "s".repeat(size.max() + 1);
                }
            }

        } catch (NoSuchFieldException e) {
            // ...
        }


    }
}