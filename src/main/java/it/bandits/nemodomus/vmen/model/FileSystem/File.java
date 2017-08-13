package it.bandits.nemodomus.vmen.model.FileSystem;

import java.util.ArrayList;

/**
 * Created by Nemo Domus on 8/12/2017.
 */

public class File {
    private String fileName;
    private ArrayList<Build> builds;

    public File(String fileName) {
        this.fileName = fileName;
        this.builds = new ArrayList<>();
    }

    public File(String fileName, ArrayList<Build> builds) {
        this.fileName = fileName;
        this.builds = builds;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Build> getBuilds() {
        return builds;
    }

    public void setBuilds(ArrayList<Build> builds) {
        this.builds = builds;
    }

    public void addBuild(Build build){
        this.builds.add(build);
    }

    public Build getBuildAtPosition(int position){
        return this.builds.get(position);
    }
}
