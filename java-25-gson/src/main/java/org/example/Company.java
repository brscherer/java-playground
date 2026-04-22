package org.example;

import java.util.List;

public class Company {
    private String name;
    private List<User> employees;

    public Company(String name, List<User> employees) {
        this.name = name;
        this.employees = employees;
    }

    public Company() {}

    @Override
    public String toString() {
        return "Company{" + "name='" + name + '\'' + ", employees=" + employees + '}';
    }
}

