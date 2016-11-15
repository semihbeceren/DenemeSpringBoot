package com.semihbeceren.model;

import javax.persistence.MappedSuperclass;

/**
 * Created by Semih Beceren on 11.11.2016.
 */

@MappedSuperclass
public class NamedEntity extends BaseEntity {

    protected String name, surname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
