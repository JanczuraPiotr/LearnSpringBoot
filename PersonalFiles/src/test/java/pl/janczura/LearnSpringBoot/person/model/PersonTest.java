package pl.janczura.LearnSpringBoot.person.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.AssertionFailureBuilder.assertionFailure;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


// Test written for educational purposes.
// 1. Is it possible to test compliance with constraints set for an entity somewhere other than in the controller?.
// 2. How to read and use restrictions set by annotations.
// I deliberately omit the tests for surname and personal_id, they will look the same.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonTest {

    private static final Logger log = LoggerFactory.getLogger(PersonTest.class);
    public static final String Name_ToShort               = "Ab";
    public static final String Name_Correct_LongMin       = "Abc";
    public static final String Name_Correct_LongMax       = "Abcdefghij";
    public static final String Name_ToLong                = "Abcdefghijk";

    public static final String Surname_ToShort            = "Lm";
    public static final String Surname_Correct_LongMin    = "Lmn";
    public static final String Surname_Correct_LongMax    = "Lmnoprstqu";
    public static final String Surname_ToLong             = "Lmnoprstquw";

    public static final String PersonalId_ToShort         = "1234";
    public static final String PersonalId_Correct_LongMin = "12345";
    public static final String PersonalId_Correct_LongMax = "1234567890";
    public static final String PersonalId_ToLong          = "12345678901";

    public static final Person Person1       = new Person(Name_Correct_LongMin,
                                                          Surname_Correct_LongMin,
                                                          PersonalId_Correct_LongMin);
    public static final Person Person2       = new Person(Name_Correct_LongMin + "x",
                                                          Surname_Correct_LongMin + "y",
                                                          PersonalId_Correct_LongMin + "z");
    public static final Person Person3       = new Person(Name_Correct_LongMax,
                                                          Surname_Correct_LongMax,
                                                          PersonalId_Correct_LongMax);
    public static final Person Person1_saved = new Person(1L,
                                                          Person1.getName(),
                                                          Person1.getSurname(),
                                                          Person1.getPersonalId());
    public static final Person Person2_saved = new Person(2L,
                                                          Person2.getName(),
                                                          Person2.getSurname(),
                                                          Person2.getPersonalId());
    public static final Person Person3_saved = new Person(3L,
                                                          Person3.getName(),
                                                          Person3.getSurname(),
                                                          Person3.getPersonalId());

    public static String MessageAboutExpectedName    = "Length of `name` must be between 3 and 10 characters long.";

    @Autowired
    private Validator validator;

    @BeforeAll
    public static void setUp() {
        log.info(Person1.toString());
        log.info(Person2.toString());
        log.info(Person3.toString());
        log.info(Person1_saved.toString());
        log.info(Person2_saved.toString());
        log.info(Person3_saved.toString());
    }

    public PersonTest() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @Order(1)
    public void create() {
        Person person = new Person(Name_Correct_LongMin, Surname_Correct_LongMin, PersonalId_Correct_LongMin);
        assertEquals(Name_Correct_LongMin, person.getName());
        assertEquals(Surname_Correct_LongMin, person.getSurname());
        assertEquals(PersonalId_Correct_LongMin, person.getPersonalId());

        Person savedPerson = new Person(1L, Name_Correct_LongMin, Surname_Correct_LongMin, PersonalId_Correct_LongMin);
        assertEquals(Name_Correct_LongMin, savedPerson.getName());
        assertEquals(Surname_Correct_LongMin, savedPerson.getSurname());
        assertEquals(PersonalId_Correct_LongMin, savedPerson.getPersonalId());

        Person personCopy = new Person(person);
        assertEquals(Name_Correct_LongMin, personCopy.getName());
        assertEquals(Surname_Correct_LongMin, personCopy.getSurname());
        assertEquals(PersonalId_Correct_LongMin, personCopy.getPersonalId());
    }

    @Test
    public void whenNameIsNull_thenValidationFails() {
        Person person = new Person(null,Surname_Correct_LongMin,PersonalId_Correct_LongMin);

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
        Person person = new Person(Name_ToShort,null,null);

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        Optional<Failure> failure = validationConclusion("name", violations);

        if (failure.isPresent()) {
            assertTrue(failure.get().attribute().equals("name") && failure.get().actual().equals(Name_ToShort),
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
        Person person = new Person(Name_Correct_LongMin,null,null);

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
        Person person = new Person(Name_Correct_LongMax,null,null);

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
        Person person = new Person(Name_ToLong,null,null);

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        Optional<Failure> failure = validationConclusion("name", violations);

        if (failure.isPresent()) {
            assertTrue(failure.get().attribute().equals("name") && failure.get().actual().equals(Name_ToLong),
                    "Expected: " + failure.get().expected() + ", Actual: " + failure.get().actual());
        } else {
            assertionFailure()
                    .reason("name")
                    .expected(MessageAboutExpectedName)
                    .actual(person.getName() + (person.getName() != null ? " (length = " + person.getName().length() + ")" : ""))
                    .buildAndThrow();
        }
    }

    // I'm checking how to test a created object based on annotations in its class definition.
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

}
