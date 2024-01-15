package com.gfg.springbootDemo.dbDemo.Controller;

import com.gfg.springbootDemo.dbDemo.Person;
import com.gfg.springbootDemo.dbDemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;
    @GetMapping("/getPersons")
    public ResponseEntity<List<Person>> getPersonData(){
        //validation -> for this case, u do not have any validations to put on
        // HTTP request -> we can return anything
        // HTTP provides us a class to get returned

        //
        List<Person> list = personService.getPersonData();
        ResponseEntity<List<Person>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);

        return responseEntity;

    }
    @PostMapping("/addPerson")
    public ResponseEntity<Person> addperson(@RequestBody Person person){
       if(person.getName()==null){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
       Person p=personService.addPerson(person);


        return new ResponseEntity<>(p,HttpStatus.OK);

    }
@PutMapping("/updatePerson")
    public ResponseEntity<Integer>updatePerson(@RequestBody Person person){
    if(person.getId()==null){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(personService.updatePerson(person.getId(),person.getName()),HttpStatus.OK);
}

}
