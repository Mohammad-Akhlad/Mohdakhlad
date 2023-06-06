package com.atdxt.Jdbc1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    @ResponseBody
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





}
