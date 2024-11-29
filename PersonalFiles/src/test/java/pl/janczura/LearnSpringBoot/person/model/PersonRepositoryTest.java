package pl.janczura.LearnSpringBoot.person.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testIdIsSetAfterSave() {
        Person person = new Person("John", "Doe");
        assertNull(person.getId());

        Person personSaved = personRepository.save(person);
        assertNotNull(personSaved.getId());
    }

}
