package pl.janczura.LearnSpringBoot.person;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.janczura.LearnSpringBoot.person.model.Person;
import pl.janczura.LearnSpringBoot.person.service.PersonService;

import java.util.Optional;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;


    @Test
    public void testCreatePerson() throws Exception {
        Long personId = 1L;
        Person person = new Person("Jan", "Kowalski", "1234567890");
        person.setId(personId);

        given(personService.getById(personId)).willReturn(Optional.of(person));

//        mockMvc.perform(post("/person/{id}", personId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(person.getId().intValue())))
//                .andExpect(jsonPath("$.name", is(person.getName())))
//                .andExpect(jsonPath("$.surname", is(person.getSurname())));
    }

    @Test
    public void testUpdatePerson() throws Exception {
        Long personId = 1L;
        Person person = new Person("Jan", "Kowalski", "1234567890");
        person.setId(personId);

        given(personService.getById(personId)).willReturn(Optional.of(person));

//        mockMvc.perform(post("/person/{id}", personId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(person.getId().intValue())))
//                .andExpect(jsonPath("$.name", is(person.getName())))
//                .andExpect(jsonPath("$.surname", is(person.getSurname())));

    }

    @Test
    public void testDeletePerson() throws Exception {

    }

    @Test
    public void testGetPersonById() throws Exception {
//        Long personId = 1L;
//        Person person = new Person("Jan", "Kowalski", "1234567890");
//        person.setId(personId);
//
//        given(personService.getById(personId)).willReturn(Optional.of(person));
//
//        mockMvc.perform(get("/person/{id}", personId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(person.getId().intValue())))
//                .andExpect(jsonPath("$.name", is(person.getName())))
//                .andExpect(jsonPath("$.surname", is(person.getSurname())));

    }

    @Test
    public void testGetAllPerson() throws Exception {
//        Long personId = 1L;
//        Person person = new Person("Jan", "Kowalski", "1234567890");
//        person.setId(personId);
//
//        given(personService.getById(personId)).willReturn(Optional.of(person));
//
//        mockMvc.perform(get("/person/{id}", personId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(person.getId().intValue())))
//                .andExpect(jsonPath("$.name", is(person.getName())))
//                .andExpect(jsonPath("$.surname", is(person.getSurname())));
//
    }
}
