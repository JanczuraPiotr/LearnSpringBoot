package pl.janczura.LearnSpringBoot.person;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.janczura.LearnSpringBoot.person.model.Person;
import pl.janczura.LearnSpringBoot.person.model.PersonTest;
import pl.janczura.LearnSpringBoot.person.service.PersonService;

import java.util.List;
import java.util.Optional;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    private static final Logger log = LoggerFactory.getLogger(PersonControllerTest.class);
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    public void createPerson()  {
        Person personToSave = PersonTest.Person1;
        Person personSaved = PersonTest.Person1_saved;

        given(personService.save(any(Person.class)))
                .willReturn(personSaved);

        try {
            mockMvc.perform(post("/person")
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                    .content(objectMapper.writeValueAsString(personToSave))
                   )
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.id", is(personSaved.getId().intValue())))
                   .andExpect(jsonPath("$.name", is(personSaved.getName())))
                   .andExpect(jsonPath("$.surname", is(personSaved.getSurname())))
            ;

        } catch (Exception e) {
            log.error("Error while testing PersonController.createPerson: {}", e.getMessage());
        }
    }

    @Test
    public void deletePersonIfExists() {
        Long personId = PersonTest.Person1_saved.getId();
        log.info("personId: {}", personId);
        given(personService.deleteById(personId)).willReturn(true);

        try {
            mockMvc.perform(delete("/person/{id}", personId))
                   .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("Error while testing PersonController.deletePerson: {}", e.getMessage());
        }
    }

    @Test
    public void deletePersonIfNotExist() {
        Long personId = PersonTest.Person1_saved.getId();

        given(personService.deleteById(personId)).willReturn(false);

        try {
            mockMvc.perform(delete("/person/{id}", personId))
                   .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            log.error("Error while testing PersonController.deletePerson: {}", e.getMessage());
        }
    }

    @Test
    public void getAllPerson() {
        List<Person> persons = List.of(PersonTest.Person1_saved, PersonTest.Person2_saved);

        given(personService.getAll())
                .willReturn(persons);

        try {
            mockMvc.perform(get("/person"))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$[0].id", is(persons.get(0).getId().intValue())))
                   .andExpect(jsonPath("$[0].name", is(persons.get(0).getName())))
                   .andExpect(jsonPath("$[0].surname", is(persons.get(0).getSurname())))
                   .andExpect(jsonPath("$[1].id", is(persons.get(1).getId().intValue())))
                   .andExpect(jsonPath("$[1].name", is(persons.get(1).getName())))
                   .andExpect(jsonPath("$[1].surname", is(persons.get(1).getSurname())));
        } catch (Exception e) {
            log.error("Error while testing PersonController.getAllPerson: {}", e.getMessage());
        }
    }

    @Test
    public void getPersonById() {
        Long personId = PersonTest.Person1_saved.getId();
        Person person = PersonTest.Person1_saved;

        given(personService.getById(personId))
                .willReturn(Optional.of(person));

        try {
            mockMvc.perform(get("/person/{id}", personId))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.id", is(person.getId().intValue())))
                   .andExpect(jsonPath("$.name", is(person.getName())))
                   .andExpect(jsonPath("$.surname", is(person.getSurname())));
        } catch (Exception e) {
            log.error("Error while testing getPersonById: {}", e.getMessage());
        }
    }


// ------------------------------------------------------------------------------------------------------------------


}
