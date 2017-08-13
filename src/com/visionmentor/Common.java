package com.visionmentor;

import com.intellij.openapi.vfs.VirtualFile;

import java.util.ArrayList;
import java.util.List;

public class Common {
    private static Common common;
    public static List<VirtualFile> filesWithChanges = new ArrayList<>();;
    public static final String URL ="";
    public static String currentUser = "";

    private Common() {
    }

    public static Common getInstance(){
        if(common == null){
            common = new Common();
        }
        return common;
    }

    public static List<VirtualFile> getFilesWithChanges() {
        return filesWithChanges;
    }

    public static void setFilesWithChanges(List<VirtualFile> filesWithChanges) {
        Common.filesWithChanges = filesWithChanges;
    }

    public static String getURL() {
        return URL;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        Common.currentUser = currentUser;
    }
}

