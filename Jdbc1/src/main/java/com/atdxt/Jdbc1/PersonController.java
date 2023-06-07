package com.atdxt.Jdbc1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/get")
    public List<Person> getAllPersons() {
        String sql = "SELECT * FROM people";

        List<Person> persons = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Person person = new Person();
            person.setId(resultSet.getInt("People_id"));
            person.setName(resultSet.getString("People_name"));
            person.setCity(resultSet.getString("People_city"));
            return person;
        });

        return persons;
    }

    @PostMapping("/add")
    public ResponseEntity<String> insertPerson(@RequestBody Person person) {
        String sql = "INSERT INTO people (People_name, People_city) VALUES (?, ?)";

        int rowsAffected = jdbcTemplate.update(sql, person.getName(), person.getCity());

        if (rowsAffected == 1) {
            return new ResponseEntity<>("Person inserted successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to insert person", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

