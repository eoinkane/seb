package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Extract employee salary information
        Employee emp = a.getEmployee("Tse", "Herber");

        // Test the size of the returned data - should be 42000
        a.displayEmployee(emp);

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /** getEmployee
     *
     * @param ID, the ID of the employee to search the data base for
     * @return Employee with the given ID
     */
    public Employee getEmployee(int ID)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT e.emp_no, e.first_name, e.last_name, title, salary, dept_name, mngr.emp_no, "
                            + "mngr.first_name, mngr.last_name "
                            + "FROM employees e "
                            + "JOIN dept_emp de on e.emp_no = de.emp_no "
                            + "JOIN departments d on d.dept_no = de.dept_no "
                            + "JOIN titles t on e.emp_no = t.emp_no "
                            + "JOIN salaries s on e.emp_no = s.emp_no "
                            + "JOIN dept_manager dm on d.dept_no = dm.dept_no "
                            + "JOIN employees mngr on mngr.emp_no = dm.emp_no "
                            + "WHERE e.emp_no = " + ID + " AND s.to_date = \"9999-01-01\" AND dm.to_date = \"9999-01-01\";";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next()) {
                Department dept = getDepartment(rset.getString("dept_name"));
                Employee mngr = new Employee();
                mngr.emp_no = rset.getInt("mngr.emp_no");
                mngr.first_name = rset.getString("mngr.first_name");
                mngr.last_name = rset.getString("mngr.last_name");

                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.title = rset.getString("title");
                emp.salary = rset.getInt("salary");
                emp.dept = dept;
                emp.manager = mngr;
                return emp;
            } else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    /**
     * Gets all the current employees and salaries.
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<Employee> getAllSalaries() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries "
                            + "WHERE employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01' "
                            + "ORDER BY employees.emp_no ASC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    /**
     * Gets all the current employees and salaries by a given role.
     * @param role: a string that represents the selected job role
     * @return A list of all employees and salaries with a given role, or null if there is an error.
     */
    public ArrayList<Employee> getSalariesByRole(String role)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries, titles "
                            + "WHERE employees.emp_no = salaries.emp_no "
                            + "AND employees.emp_no = titles.emp_no "
                            + "AND salaries.to_date = '9999-01-01' "
                            + "AND titles.to_date = '9999-01-01' "
                            + "AND titles.title = '<title>' "
                            + "ORDER BY employees.emp_no ASC;";
            strSelect = strSelect.replace("<title>", role);
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next())
            {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    /** displayEmployee
     *
     * @param emp: Employee, the employee object to display the information
     */
    public void displayEmployee(Employee emp) {
        if (emp != null) {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary: " + emp.salary + "\n"
                            + "Department: " + (emp.dept != null ? emp.dept.dept_name : null) + "\n"
                            + "Manager: "
                            + (emp.manager != null ? emp.manager.first_name + " " + emp.manager.last_name : null) + "\n");
        }
    }

    /**
     * Prints a list of employees.
     * @param employees The list of employees to print.
     */
    public void printSalaries(ArrayList<Employee> employees) {
        // Check employees is not null
        if (employees == null)
        {
            System.out.println("No employees");
            return;
        }
        // Print header
        System.out.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
        // Loop over all employees in the list
        for (Employee emp : employees)
        {
            if (emp == null)
                continue;
            String emp_string =
                    String.format("%-10s %-15s %-20s %-8s",
                            emp.emp_no, emp.first_name, emp.last_name, emp.salary);
            System.out.println(emp_string);
        }
    }

    /**
     * Returns the department with a given name
     * @param dept_name Search for the department with this name
     * @return A department instance of the matching department name or null if an error occured
     */
    public Department getDepartment(String dept_name) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT dept.dept_no, dept.dept_name, dept_mngr.emp_no, emp.first_name, emp.last_name,"
                            + " titles.title FROM departments dept JOIN dept_manager dept_mngr ON dept.dept_no = "
                            + "dept_mngr.dept_no JOIN employees emp ON dept_mngr.emp_no = emp.emp_no "
                            + "JOIN titles ON dept_mngr.emp_no = titles.emp_no WHERE dept_mngr.to_date = "
                            + "'9999-01-01' AND titles.to_date = '9999-01-01' AND dept.dept_name = '" + dept_name + "';";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract employee information
            Employee mngr = new Employee();
            Department dept = new Department();
            if (rset.next()) {
                mngr.emp_no = rset.getInt("emp_no");
                mngr.first_name = rset.getString("first_name");
                mngr.last_name = rset.getString("last_name");
                mngr.title = rset.getString("title");

                dept.dept_no = rset.getString("dept_no");
                dept.dept_name = rset.getString("dept_name");
                dept.manager = mngr;
            }
            return dept;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get department");
            return null;
        }
    }

    /**
     * Gets all the current employees and salaries by a given department.
     * @param dept: a department to get all the salaries from
     * @return A list of all employees and salaries with a given department, or null if there is an error.
     */
    public ArrayList<Employee> getSalariesByDepartment(Department dept) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries, dept_emp, departments "
                            + "WHERE employees.emp_no = salaries.emp_no "
                            + "AND employees.emp_no = dept_emp.emp_no "
                            + "AND dept_emp.dept_no = departments.dept_no "
                            + "AND salaries.to_date = '9999-01-01' "
                            + "AND departments.dept_no = '" + dept.dept_no + "' "
                            + "ORDER BY employees.emp_no ASC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salaries by role");
            return null;
        }
    }

    /**
     * Returns the first employee with a given name
     * @param first_name Search for the employee with this first name
     * @param last_name Search for the employee with this first name
     */
    public Employee getEmployee(String first_name, String last_name) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT emp.emp_no, emp.first_name, emp.last_name, titles.title, salaries.salary, dept.dept_name, "
                            + "dept_mngr.emp_no as `mngr_emp_no`, mngr_emp.first_name as `mngr_first_name`, "
                            + "mngr_emp.last_name as `mngr_last_name` FROM employees emp JOIN salaries ON "
                            + "salaries.emp_no = emp.emp_no JOIN current_dept_emp dept_emps ON dept_emps.emp_no = "
                            + "emp.emp_no JOIN departments dept ON dept.dept_no = dept_emps.dept_no JOIN titles ON "
                            + "titles.emp_no = emp.emp_no JOIN dept_manager dept_mngr ON dept_mngr.dept_no = "
                            + "dept_emps.dept_no JOIN employees mngr_emp ON mngr_emp.emp_no = dept_mngr.emp_no WHERE "
                            + "salaries.to_date = '9999-01-01' AND dept_emps.to_date = '9999-01-01' AND titles.to_date "
                            + "= '9999-01-01' AND dept_mngr.to_date = '9999-01-01' AND "
                            + "emp.first_name = '" + first_name + "' AND emp.last_name = '" + last_name + "';";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract employee information
            Employee emp = new Employee();
            Employee mngr = new Employee();
            if (rset.next()) {
                Department dept = getDepartment(rset.getString("dept_name"));
                mngr.emp_no = rset.getInt("mngr_emp_no");
                mngr.first_name = rset.getString("mngr_first_name");
                mngr.last_name = rset.getString("mngr_last_name");

                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.title = rset.getString("title");
                emp.salary = rset.getInt("salary");

                emp.manager = mngr;
                emp.dept = dept;
            }
            return emp;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee");
            return null;
        }
    }
}

