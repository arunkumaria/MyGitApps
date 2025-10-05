package com.persons.dao;

import java.sql.SQLException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.persons.beans.Person;

public class PersonDao {
  
    JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public Person getPersonsByName(String personName) throws SQLException {
        String sql = "select * from personsdetails where name=?";
        return template.queryForObject(sql, new Object[] {personName},
                new BeanPropertyRowMapper<Person>(Person.class));
    }
    
    public Person getPersonsByServiceNumber(String serviceNumber) throws SQLException {
        String sql = "select * from personsdetails where serviceNumber=?";
        return template.queryForObject(sql, new Object[] {serviceNumber},
                new BeanPropertyRowMapper<Person>(Person.class));
    }
    
    public Person getPersonsById(int id) throws SQLException {
        String sql = "select * from personsdetails where id =?";
        return template.queryForObject(sql, new Object[] { id },
                new BeanPropertyRowMapper<Person>(Person.class));
    }
    
    public Person getPersonsByConsumedUnits(int consumedUnits) throws SQLException {
        String sql = "select * from personsdetails where consumedUnits=?";
        return template.queryForObject(sql, new Object[] { consumedUnits },
                new BeanPropertyRowMapper<Person>(Person.class));
    }

}