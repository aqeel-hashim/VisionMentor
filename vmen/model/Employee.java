package it.bandits.nemodomus.vmen.model;

import java.util.ArrayList;

import it.bandits.nemodomus.vmen.model.FileSystem.Project;

/**
 * Created by Nemo Domus on 8/12/2017.
 */

public class Employee extends User {
    private ArrayList<Project> projects;

    public Employee(String username, String password, String email) {
        super(username, password, email);
        this.projects = new ArrayList<>();
    }

    public Employee(String username, String password, String email, ArrayList<Project> prs) {
        super(username, password, email);
        this.projects = prs;
    }

    public void addProject(Project project){
        this.projects.add(project);
    }

    public Project getProjectAtPosition(int position){
        return this.projects.get(position);
    }
}
