package pl.janczura.LearnSpringBoot.person.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janczura.LearnSpringBoot.person.model.Person;
import pl.janczura.LearnSpringBoot.person.model.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public boolean deleteById(Long id) {
        if( personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
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
            log.info("Person saved: {}",  objectMapper.writeValueAsString(ret));
        } catch (JsonProcessingException e) {
            if(null == ret) {
                log.error("Person not saved: Unknown error while processing json.");
            }
        }

        return ret;
    }

    public Person update(Long id, Person person) {
        Optional<Person> personSaved = personRepository.findById(id);
        if(!personSaved.isPresent()) {
            log.warn("Attempt to update non existing person with id:", id);
            return null;
        }
        Person personToUpdate = new Person(personSaved.get().getId(),
                                           person.getName(),
                                           person.getSurname(),
                                           person.getPersonalId());

        Person ret = personRepository.save(personToUpdate);
        try {
            log.info("Person updated: {}",  objectMapper.writeValueAsString(ret));
        } catch (JsonProcessingException e) {
            if(null == ret) {
                log.error("Person not udated: Unknown error while processing json.");
            }
        }

        return ret;
    }

}
