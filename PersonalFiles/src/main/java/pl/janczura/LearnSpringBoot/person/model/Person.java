package pl.janczura.LearnSpringBoot.person.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 5)
    private String name;

    @NotEmpty
    @Size(min = 3, max = 5)
    private String surname;

    @NotEmpty
    @NotBlank
    @Size(min = 5, max = 10)
    @Column(name = "personal_id", unique = true, nullable = false)
    private String personalId;

    public Person() {}

    public Person(String name, String surname, String personalId) {
        this.id = null;
        this.name = name;
        this.surname = surname;
        this.personalId = personalId;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

}
