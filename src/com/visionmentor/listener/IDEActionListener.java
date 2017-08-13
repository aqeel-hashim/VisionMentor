package com.visionmentor.listener;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerAdapter;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import com.visionmentor.Common;
import com.visionmentor.model.Build;
import com.visionmentor.model.CodeSmell;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IDEActionListener implements AnActionListener {

    @Override
    public void beforeActionPerformed(AnAction anAction, DataContext dataContext, AnActionEvent anActionEvent) {

    }

    @Override
    public void afterActionPerformed(AnAction action, DataContext dataContext, AnActionEvent event) {
        VirtualFile x = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        VirtualFile[] virtualFileArray = event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
        virtualFileArray = dataContext.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
        Project project = event.getProject();

        String eventName = event.getPresentation().getText();
        System.out.println("Event : " + eventName);
        String projectName = project.getName();
        if (eventName.contains("Debug")) {

            System.out.println("Application is being debugged");
            serveBuild(project, virtualFileArray);
        } else if (eventName.contains("Run " + "'" + projectName + "'")) {
            System.out.println("Application is being run");
            serveBuild(project, virtualFileArray);
        } else if (eventName.contains("Build Project")) {
            System.out.println("Application is being built");
            serveBuild(project, virtualFileArray);
        }


    }

    @Override
    public void beforeEditorTyping(char c, DataContext dataContext) {

    }

    public void serveBuild(Project project, VirtualFile[] virtualFileArray) {

        List<VirtualFile> virtualFileList = new ArrayList<>();
        if (virtualFileArray.length > 0) {
            for (VirtualFile vf : virtualFileArray) {
                virtualFileList.add(vf);
            }
            Build build = new Build(project, Common.filesWithChanges);
            System.out.println("Project : " + build.getProjectName());
            System.out.println("Username : " + build.getUsername());
            System.out.println("Recommendations  count : " + build.getRecommendationCount());
            System.out.println("Total smell score : " + build.getTotalSmellScore());
            System.out.println("isFailed : " + build.isFailed());
            for (CodeSmell cs : build.getCodeSmells()) {
                System.out.println("File : " + cs.getFileName());
                System.out.println("Directory : " + cs.getDirectory());
                System.out.println("Severity : " + cs.getSeverity());
                System.out.println("Smell score : " + cs.getSmellScore());
                System.out.println("Description : " + cs.getDescription());
                System.out.println("Line Number : " + cs.getStartingLine());
            }
        }

    }
}
