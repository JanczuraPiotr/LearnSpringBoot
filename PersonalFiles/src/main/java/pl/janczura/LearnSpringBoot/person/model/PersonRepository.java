package pl.janczura.LearnSpringBoot.person.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findPersonByName(String firstName);

    List<Person> findPersonBySurname(String firstName);

    List<Person> findPersonByNameAndSurname(String firstName, String lastName);
}
