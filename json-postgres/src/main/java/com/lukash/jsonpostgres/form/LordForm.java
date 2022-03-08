package com.lukash.jsonpostgres.form;

import javax.persistence.*;


public class LordForm {

    private String age;
    private String name;


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
