package com.gfg.springbootDemo.dbDemo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    public String name;

    public Integer id;


    /*public Person(String name, int id) {
        this.name=name;
        this.id=id;

    }*/
}
