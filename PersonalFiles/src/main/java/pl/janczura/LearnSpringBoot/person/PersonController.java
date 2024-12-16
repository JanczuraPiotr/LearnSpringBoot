package pl.janczura.LearnSpringBoot.person;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.janczura.LearnSpringBoot.person.model.Person;
import pl.janczura.LearnSpringBoot.person.service.PersonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
        Person createdPerson = personService.save(person);

        if(createdPerson != null) {
            log.info("createPerson: {}", createdPerson);
            return ResponseEntity.ok().body(createdPerson);
        } else {
            log.info("Error while creating person : {}", person);
            return ResponseEntity.internalServerError().body(person);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePerson(@PathVariable("id") Long id) {
        boolean result = personService.deleteById(id);

        if(result) {
            log.info("deletePerson: {}", id);
            return ResponseEntity.ok().build();
        } else {
            log.info("deletePerson: {} not found", id);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id,  @Valid @RequestBody Person person) {
        Optional<Person> updatedPerson = personService.update(id, person);

        if(!updatedPerson.isPresent()) {
            log.info("updatedPerson: {}", updatedPerson);
            return ResponseEntity.notFound().build();
        }
        log.info("updatePerson: {}", updatedPerson);
        return ResponseEntity.ok(updatedPerson.get());
    }

    @GetMapping
    public List<Person> getAllPerson() {
        List<Person> personList = personService.getAll();
        log.info("getAllPerson: {}", personList);
        return personList;
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Long id) {
        Person person = personService.getById(id).get();
        log.info("getPersonById: {}", person);
        return person;
    }



}
