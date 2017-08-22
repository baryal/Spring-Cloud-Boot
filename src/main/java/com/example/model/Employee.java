package com.example.model;

public class Employee {

    private int id;
    private String name;
    private String lastName;
    private boolean active;

    public Employee() {
        super();
    }

    public Employee(String name, String lastName, boolean active) {
        super();
        this.name = name;
        this.lastName = lastName;
        this.active = active;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
