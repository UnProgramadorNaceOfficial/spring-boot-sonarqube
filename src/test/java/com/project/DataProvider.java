package com.project;

import com.project.persistence.entity.Customer;

import java.util.List;

public class DataProvider {

    public static List<Customer> dataMockList() {
        return List.of(
                new Customer("John", "Doe"),
                new Customer("Jane", "Smith"),
                new Customer("Michael", "Johnson"),
                new Customer("Emily", "Davis"),
                new Customer("Robert", "Brown")
        );
    }

    public static Customer dataMockObject() {
        return new Customer("John", "Doe");
    }

    public static Customer dataMockNewObject() {
        return new Customer("Jonny", "Deep");
    }
}
