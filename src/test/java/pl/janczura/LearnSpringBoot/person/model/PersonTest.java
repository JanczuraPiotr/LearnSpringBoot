package pl.janczura.LearnSpringBoot.person.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {

    @Test
    public void testGetName() {
        Person person = new Person("John", "Doe");
        assertEquals("John", person.getName());
    }

    @Test
    public void testGetSurname() {
        Person person = new Person("John", "Doe");
        assertEquals("Doe", person.getSurname());
    }

    @Test
    public void testId() {
        Person person = new Person("", "");
        assertEquals(null, person.getId());
    }

}
