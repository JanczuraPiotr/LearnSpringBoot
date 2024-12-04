package pl.janczura.LearnSpringBoot.person.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.AssertionFailureBuilder.assertionFailure;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


// Test written for educational purposes.
// 1. Is it possible to test compliance with constraints set for an entity somewhere other than in the controller?.
// 2. How to read and use restrictions set by annotations.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonTest {

    private static String NameToShort;
    private static String NameCorrectLongMinLim;
    private static String NameCorrectLongMaxLim;
    private static String NameToLong;
    private static String PersonalId = "1234567890";
    private static String SurnameToShort;
    private static String SurnameCorrectLongMinLim;
    private static String SurnameCorrectLongMaxLim;
    private static String SurnameToLong;
    private static String MessageAboutExpectedName;
    private static String MessageAboutExpectedSurname;

    @Autowired
    private Validator validator;

    public PersonTest() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @BeforeAll
    public static void setUp() {
        limitations();
    }

    @Test
    @Order(1)
    public void testGetSet() {

        Person person = new Person(NameCorrectLongMinLim, SurnameCorrectLongMinLim, PersonalId);
        assertEquals(NameCorrectLongMinLim, person.getName());
        assertEquals(SurnameCorrectLongMinLim, person.getSurname());
        assertEquals(PersonalId, person.getPersonalId());

        person.setName(NameCorrectLongMaxLim);
        person.setSurname(SurnameCorrectLongMaxLim);
        person.setPersonalId(PersonalId);

        assertEquals(NameCorrectLongMaxLim, person.getName());
        assertEquals(SurnameCorrectLongMaxLim, person.getSurname());
        assertEquals(PersonalId, person.getPersonalId());
    }

    @Test
    public void whenNameIsNull_thenValidationFails() {
        Person person = new Person();
        person.setName(null);
        person.setSurname(SurnameCorrectLongMinLim);
        person.setPersonalId(PersonalId);

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        Optional<Failure> failure = validationConclusion("name", violations);

        if (failure.isPresent()) {
            assertTrue(failure.get().attribute().equals("name") && failure.get().actual().equals("null"),
                    "Expected: " + failure.get().expected() + ", Actual: " + failure.get().actual());
        } else {
            assertionFailure()
                    .reason("name")
                    .expected(MessageAboutExpectedName)
                    .actual(person.getName()+ (person.getName() != null ? " (length = " + person.getName().length() + ")" : ""))
                    .buildAndThrow();

        }
    }

    @Test
    public void whenNameIsToShort_thenValidationFails() {
        Person person = new Person();
        person.setName(NameToShort);
        person.setSurname(null);
        person.setPersonalId(null);

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        Optional<Failure> failure = validationConclusion("name", violations);

        if (failure.isPresent()) {
            assertTrue(failure.get().attribute().equals("name") && failure.get().actual().equals(NameToShort),
                    "Expected: " + failure.get().expected() + ", Actual: " + failure.get().actual());
        } else {
            assertionFailure()
                    .reason("name")
                    .expected(MessageAboutExpectedName)
                    .actual(person.getName() + (person.getName() != null ? " (length = " + person.getName().length() + ")" : ""))
                    .buildAndThrow();
        }
    }

    @Test
    public void whenNameIsCorrectMinLong_thenValidationCorrect() {
        Person person = new Person();
        person.setName(NameCorrectLongMinLim);
        person.setSurname(null);
        person.setPersonalId(null);

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        Optional<Failure> failure = validationConclusion("name", violations);

        if (failure.isEmpty()) {
            assertTrue(true);
        } else {
            assertionFailure()
                    .reason("name")
                    .expected(failure.get().expected())
                    .actual(person.getName() + (person.getName() != null ? " (length = " + person.getName().length() + ")" : ""))
                    .buildAndThrow();
        }
    }

    @Test
    public void whenNameIsCorrectMaxLong_thenValidationCorrect() {
        Person person = new Person();
        person.setName(NameCorrectLongMaxLim);
        person.setSurname(null);
        person.setPersonalId(null);

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        Optional<Failure> failure = validationConclusion("name", violations);

        if (failure.isEmpty()) {
            assertTrue(true);
        } else {
            assertionFailure()
                    .reason("name")
                    .expected(failure.get().expected())
                    .actual(person.getName() + (person.getName() != null ? " (length = " + person.getName().length() + ")" : ""))
                    .buildAndThrow();
        }
    }

    @Test
    public void whenNameIsToLong_thenValidationFails() {
        Person person = new Person();
        person.setName(NameToLong);
        person.setSurname(null);
        person.setPersonalId(null);

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        Optional<Failure> failure = validationConclusion("name", violations);

        if (failure.isPresent()) {
            assertTrue(failure.get().attribute().equals("name") && failure.get().actual().equals(NameToLong),
                    "Expected: " + failure.get().expected() + ", Actual: " + failure.get().actual());
        } else {
            assertionFailure()
                    .reason("name")
                    .expected(MessageAboutExpectedName)
                    .actual(person.getName() + (person.getName() != null ? " (length = " + person.getName().length() + ")" : ""))
                    .buildAndThrow();
        }
    }

    @Test
    public void whenSurnameIsNull_thenValidationFails() {
        Person person = new Person();
        person.setName(NameCorrectLongMinLim);
        person.setSurname(null);
        person.setPersonalId(PersonalId);

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        Optional<Failure> failure = validationConclusion("surname", violations);

        if (failure.isPresent()) {
            assertTrue(failure.get().attribute().equals("surname") && failure.get().actual().equals("null"),
                    "Expected: " + failure.get().expected() + ", Actual: " + failure.get().actual());
        } else {
            assertionFailure()
                    .reason("surname")
                    .expected(MessageAboutExpectedSurname)
                    .actual(person.getSurname()+ (person.getSurname() != null ? " (length = " + person.getSurname().length() + ")" : ""))
                    .buildAndThrow();
        }
    }

    @Test
    public void whenSurnameIsToShort_thenValidationFails() {
        Person person = new Person();
        person.setName(null);
        person.setSurname(SurnameToShort);
        person.setPersonalId(null);

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        Optional<Failure> failure = validationConclusion("surname", violations);

        if (failure.isPresent()) {
            assertTrue(failure.get().attribute().equals("surname") && failure.get().actual().equals(SurnameToShort),
                    "Expected: " + failure.get().expected() + ", Actual: " + failure.get().actual());
        } else {
            assertionFailure()
                    .reason("surname")
                    .expected(MessageAboutExpectedSurname)
                    .actual(person.getSurname() + (person.getSurname() != null ? " (length = " + person.getSurname().length() + ")" : ""))
                    .buildAndThrow();
        }
    }

    @Test
    public void whenSurnameIsCorrectMinLong_thenValidationCorrect() {
        Person person = new Person();
        person.setName(NameCorrectLongMaxLim);
        person.setSurname(SurnameCorrectLongMinLim);
        person.setPersonalId(null);

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        Optional<Failure> failure = validationConclusion("surname", violations);

        if (failure.isEmpty()) {
            assertTrue(true);
        } else {
            assertionFailure()
                    .reason("surname")
                    .expected(failure.get().expected())
                    .actual(person.getSurname() + (person.getSurname() != null ? " (length = " + person.getSurname().length() + ")" : ""))
                    .buildAndThrow();
        }
    }

    @Test
    public void whenSurnameIsCorrectMaxLong_thenValidationCorrect() {
        Person person = new Person();
        person.setName(NameToShort);
        person.setSurname(SurnameCorrectLongMaxLim);
        person.setPersonalId(null);

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        Optional<Failure> failure = validationConclusion("surname", violations);

        if (failure.isEmpty()) {
            assertTrue(true);
        } else {
            assertionFailure()
                    .reason("surname")
                    .expected(failure.get().expected())
                    .actual(person.getSurname() + (person.getSurname() != null ? " (length = " + person.getSurname().length() + ")" : ""))
                    .buildAndThrow();
        }
    }

    @Test
    public void whenSurnameIsToLong_thenValidationFails() {
        Person person = new Person();
        person.setName(null);
        person.setSurname(SurnameToLong);
        person.setPersonalId(null);

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        Optional<Failure> failure = validationConclusion("surname", violations);

        if (failure.isPresent()) {
            assertTrue(failure.get().attribute().equals("surname") && failure.get().actual().equals(SurnameToLong),
                    "Expected: " + failure.get().expected() + ", Actual: " + failure.get().actual());
        } else {
            assertionFailure()
                    .reason("surname")
                    .expected(MessageAboutExpectedName)
                    .actual(person.getSurname() + (person.getSurname() != null ? " (length = " + person.getSurname().length() + ")" : ""))
                    .buildAndThrow();
        }
    }

    //
    // Celowo pomijam testy dla innych atrybutów.
    //

    private static class Failure {
        private String attribute;
        private String expected;
        private String actual;
        private String reason;

        public Failure(String attribute) { this.attribute = attribute;}

        public String attribute() { return attribute; }
        public Failure expected(String expected) { this.expected = expected; return this; }
        public Failure actual(String actual) { this.actual = actual; return this; }
        public Failure reason(String reason) { this.reason = reason; return this; }

        public String expected() { return expected; }
        public String actual() { return actual; }
        public String reason() { return reason; }
    }

    private Optional<Failure> validationConclusion(String attribute, Set<ConstraintViolation<Person>> violations) {
        Failure failure = new Failure(attribute);

        boolean result = violations.stream().anyMatch(v -> {
            if (v.getPropertyPath().toString().equals(attribute)) {
                failure.expected(v.getMessage());
                failure.actual(v.getInvalidValue() != null ? v.getInvalidValue().toString() : "null");
                failure.reason(attribute);
                return true;
            } else {
                return false;
            }
        });

        if (result) {
            return Optional.of(failure);
        } else {
            return Optional.empty();
        }
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
            }
            if(size.max() > 0) {
                NameCorrectLongMaxLim = "n".repeat(size.max());
                NameToLong = "n".repeat(size.max() + 1);
            }

            MessageAboutExpectedName = "Length of `name` must be between " + size.min() + " and " + size.max() + " characters long.";

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
            }
            if(size.max() > 0) {
                SurnameCorrectLongMaxLim = "s".repeat(size.max());
                SurnameToLong = "s".repeat(size.max() + 1);
            }
            MessageAboutExpectedSurname = "Length of `surname` must be between " + size.min() + " and " + size.max() + " characters long.";

        } catch (NoSuchFieldException e) {
            // ...
        }
    }
}
