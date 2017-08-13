package it.bandits.nemodomus.vmen.model.FileSystem;

import java.util.ArrayList;

/**
 * Created by Nemo Domus on 8/12/2017.
 */

public class Project {
    private String projectName;
    private ArrayList<Directory> directories;

    public Project(String projectName, ArrayList<Directory> directories) {
        this.projectName = projectName;
        this.directories = directories;
    }

    public Project(String projectName) {
        this.projectName = projectName;
        this.directories = new ArrayList<>();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ArrayList<Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(ArrayList<Directory> directories) {
        this.directories = directories;
    }

    public void addDirectory(Directory directory){
        this.directories.add(directory);
    }

    public Directory getDirectoryAtPosition(int position){
        return this.directories.get(position);
    }
}
