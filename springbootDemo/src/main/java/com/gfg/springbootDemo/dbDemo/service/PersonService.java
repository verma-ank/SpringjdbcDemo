package com.gfg.springbootDemo.dbDemo.service;

import com.gfg.springbootDemo.dbDemo.Person;
import com.gfg.springbootDemo.dbDemo.repositery.IPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
   @Qualifier("SpringjdbcDemo1")
    public IPerson iPerson;
    public List<Person> getPersonData(){

       return iPerson.getAllPerson();
    }

    public Person addPerson(Person person) {
      //  return personRepository.addPerson(person);
        return iPerson.addPersonWithPreparedStatement(person);

    }
    public int updatePerson(int id,String name){

        return iPerson.updatePerson(id,name);
    }
}
