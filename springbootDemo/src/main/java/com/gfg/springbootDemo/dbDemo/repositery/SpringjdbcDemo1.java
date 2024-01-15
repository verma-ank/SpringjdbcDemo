package com.gfg.springbootDemo.dbDemo.repositery;

import com.gfg.springbootDemo.dbDemo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class SpringjdbcDemo1 implements IPerson{

    @Autowired
    public JdbcTemplate jdbcTemplate;
    @Override
    public List<Person> getAllPerson() {
        System.out.println("=========================================ankit===================");
        return jdbcTemplate.query("select * from person", new RowMapper<Person>() {
            @Override
            public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return  new Person(resultSet.getString("name"),resultSet.getInt("id"));

            }
        });
    }

    @Override
    public int updatePerson(int id, String name) {
        return 0;
    }
//Prepared Stament with spring jdbc
    @Override
    public Person addPersonWithPreparedStatement(Person person) {
        jdbcTemplate.execute("insert into person (name, id) VALUES (? , ?)", new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, person.getName());
                ps.setInt(2, person.getId());
                return ps.execute();
            }
        });
        return person;
    }

    @Override
    public Person addPerson(Person person) {
        return null;
    }
    // 1) snapshot, m2, release etc

// 2) try prepared statement with sping jdbc template

// 3) for update ( do it a lock on one row or the complete table)

// 4) create a get request which will return u the specific data id =2, id =1

// 5) configure no of  connections u want to create hikari ?
}
