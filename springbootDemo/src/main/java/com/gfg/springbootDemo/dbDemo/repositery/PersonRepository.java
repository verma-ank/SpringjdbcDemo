package com.gfg.springbootDemo.dbDemo.repositery;

import com.gfg.springbootDemo.dbDemo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class PersonRepository implements IPerson{
    //feild injection
    private  Connection connection;
    public PersonRepository(Connection connection){
        this.connection =connection;
        createTablePerson();
    }
    private static Logger logger = LoggerFactory.getLogger(PersonRepository.class);
@Override
    public List<Person> getAllPerson() {
    List<Person> list=new ArrayList<>();
    Statement statement=null;
    try {
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from person");
        while (resultSet.next()) {
           Person p= new Person(resultSet.getString("Name"),resultSet.getInt("id"));

            list.add(p);
        }
    }
    catch (SQLException e) {
        throw new RuntimeException(e);
    }
    finally {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    return list;
}
    @Override
    public Person addPerson(Person person) {
        Statement statement=null;
        try {
            statement = connection.createStatement();
           Boolean result= statement.execute("insert into person (name, id) VALUES ('" + person.getName() +"' ," +person.getId() +");");
           logger.info(" the result of query is:"+result);
        }
        catch (SQLException E){

        }
        return person;
    }
    public void createTablePerson(){
        Statement statement =null;

        try {
            statement = connection.createStatement();
            statement.execute("create table if not exists person (name varchar(30), id int )");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public Person addPersonWithPreparedStatement(Person person){
        PreparedStatement ps  = null;
        try{
            ps =  connection.prepareStatement("insert into person (name, id) VALUES ( ?, ?) ;");
        //  ps=  connection.prepareStatement("UPDATE person SET Name = (?) WHERE id=(?); ");
            ps.setString(1, person.getName());
            ps.setInt(2, person.getId());
//            ps.setInt(3, person.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return person;

    }
    @Override
    public int updatePerson(int id,String name){
    PreparedStatement ps=null;
        boolean autocommit= Boolean.parseBoolean(null);

        try {
             autocommit=connection.getAutoCommit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int result=0;
        try {
            connection.setAutoCommit(false);
            ps=connection.prepareStatement("update person set name=? where id=?");

            ps.setString(1,name);
            ps.setInt(2,id);
            result=  ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        finally {
            try {
                connection.setAutoCommit(autocommit);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}
