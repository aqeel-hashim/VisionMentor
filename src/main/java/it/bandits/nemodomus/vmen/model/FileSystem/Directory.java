package it.bandits.nemodomus.vmen.model.FileSystem;

import java.util.ArrayList;

/**
 * Created by Nemo Domus on 8/12/2017.
 */

public class Directory {
    private String packageName;
    private ArrayList<File> files;

    public Directory(String packageName) {
        this.packageName = packageName;
        this.files = new ArrayList<>();
    }

    public Directory(String packageName, ArrayList<File> files) {
        this.packageName = packageName;
        this.files = files;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    public void addFile(File file){
        this.files.add(file);
    }

    public File getFileAtPosition(int position){
        return this.files.get(position);
    }
}
