package com.example.fhictcompanion;

public class Person {
    private String givenName;
    private String surName;

    public Person(String givenName, String surName) {
        this.givenName = givenName;
        this.surName = surName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getSurName() {
        return surName;
    }

    @Override
    public String toString() {
        return getGivenName() + " " + getSurName();
    }
}
