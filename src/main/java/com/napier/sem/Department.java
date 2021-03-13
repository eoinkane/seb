package com.napier.sem;

/**
 * Represents a Department
 */
public class Department {
    /**
     * Department Number
     * In the format 'd001' and so on.
     */
    public String dept_no;

    /**
     * Department Name
     * e.g. 'Sales'
     */
    public String dept_name;

    /**
     * Department Manager
     * an object storing a representation of an manager constructed from the active dept_manager record
     */
    public Employee manager;
}
