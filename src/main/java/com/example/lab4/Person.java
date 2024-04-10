package com.example.lab4;

/**
 * The Person class represents a generic person with a name, ID, and address.
 * It is an abstract class that provides common functionality for all types of persons.
 * @author Julius Jauga 5 gr. 1 pogr.
 */
public abstract class Person {
    String name;
    String id;
    String address;

    /**
     * Constructs a Person object with the specified name, ID, and address.
     *
     * @param name    the name of the person
     * @param id      the ID of the person
     * @param address the address of the person
     */
    public Person(String name, String id, String address) {
        this.name = name;
        this.id = id;
        this.address = address;
    }

    /**
     * Returns the name of the person.
     *
     * @return the name of the person
     */
    public abstract String getName();

    /**
     * Sets the name of the person.
     *
     * @param name the name of the person
     */
    public abstract void setName(String name);

    /**
     * Returns the ID of the person.
     *
     * @return the ID of the person
     */
    public abstract String getId();

    /**
     * Sets the ID of the person.
     *
     * @param id the ID of the person
     */
    public abstract void setId(String id);

    /**
     * Returns the address of the person.
     *
     * @return the address of the person
     */
    public abstract String getAddress();

    /**
     * Sets the address of the person.
     *
     * @param address the address of the person
     */
    public abstract void setAddress(String address);

    /**
     * Returns a string representation of the person's details.
     *
     * @return a string representation of the person's details
     */
    public String getDetails() {
        return "Name: " + name + ", ID: " + id;
    }
}
