package pl.janczura.LearnSpringBoot.person.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "person", uniqueConstraints = @UniqueConstraint(columnNames = "personal_id"))
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 10)
    private String name;

    @NotEmpty
    @Size(min = 3, max = 10)
    private String surname;

    @NotEmpty
    @NotBlank
    @Size(min = 5, max = 10)
    @Column(name = "personal_id", unique = true, nullable = false)
    private String personalId;

    public Person() {
    }

    public Person(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.surname = person.getSurname();
        this.personalId = person.getPersonalId();
    }

    public Person(String name, String surname, String idPersonal) {
        this.id = null;
        this.name = name;
        this.surname = surname;
        this.personalId = idPersonal;
    }

    public Person(Long id, String name, String surname, String idPersonal) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.personalId = idPersonal;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getPersonalId() {
        return this.personalId;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", personalId='" + personalId + '\'' +
                '}';
    }
}
