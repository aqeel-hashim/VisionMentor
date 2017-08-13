package it.bandits.nemodomus.vmen.model;

import java.util.ArrayList;

/**
 * Created by Nemo Domus on 8/12/2017.
 */

public class Employer extends User{

    private ArrayList<Employee> employees;

    public Employer(String username, String password, String email) {
        super(username, password, email);
        this.employees = new ArrayList<>();
    }

    public Employer(String username, String password, String email, ArrayList<Employee> employees) {
        super(username, password, email);
        this.employees = employees;
    }

    public void addEmployee(Employee employee){
        this.employees.add(employee);
    }

    public Employee getEmployeeAtPosition(int position){
        return this.employees.get(position);
    }
}
