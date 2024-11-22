package pl.janczura.LearnSpringBoot.person.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janczura.LearnSpringBoot.person.model.Person;
import pl.janczura.LearnSpringBoot.person.model.PersonRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

//    @Autowired
    private Validator validator;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public PersonService() {
        // TODO Czemu nie działa @Autovired
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();

    }

    public void validatePerson(Person person) {
        Set<ConstraintViolation<Person>> violations = validator.validate(person);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Person> violation : violations) {
                System.out.println(violation.getMessage());
            }
            throw new IllegalArgumentException("Validation failed for Person object");
        }
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Optional<Person> getById(Long id) {
        return personRepository.findById(id);
    }

    public Person save(Person person) {
        Person ret = personRepository.save(person);

        try {
            log.info("savePerson: {}",  objectMapper.writeValueAsString(ret));
        } catch (JsonProcessingException e) {
            log.info("savePerson: Unknown error while processing json");
        }

        return ret;
    }

    public Person update(Long id, Person person) {
        Person personToUpdate = personRepository.getOne(id);
        personToUpdate.setName(person.getName());
        personToUpdate.setSurname(person.getSurname());
        Person ret = personRepository.save(personToUpdate);

        try {
            log.info("updatePerson: {}",  objectMapper.writeValueAsString(ret));
        } catch (JsonProcessingException e) {
            log.info("updatePerson: Unknown error while processing json");
        }

        return ret;
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
