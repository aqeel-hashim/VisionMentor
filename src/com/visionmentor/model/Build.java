package com.visionmentor.model;

import com.intellij.codeInsight.CodeSmellInfo;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.impl.CodeSmellDetectorImpl;
import com.intellij.openapi.vfs.VirtualFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Build {
    private String username;
    private boolean isFailed;
    private Timestamp buildTime;
    private int recommendationCount;
    private int totalSmellScore;
    private String projectName;
    List<CodeSmell> codeSmells;
    private Project project;
    private List<VirtualFile> filesWithChanges;

    public Build(Project project, List<VirtualFile> filesWithChanges) {
        this.project = project;
        this.filesWithChanges = filesWithChanges;
        this.projectName = project.getName();
        extractCodeSmells();
        calculateTotalSmellScore();
        calculateTotalRecommendationCound();
    }

    private void extractCodeSmells(){
        codeSmells = new ArrayList<>();
        //Used to hold one VF instance since CodeSmellDetector requires VF List
        List<VirtualFile> tempVFList = new ArrayList<>();
        CodeSmellDetectorImpl codeSmellDetector = new CodeSmellDetectorImpl(project);
        for(VirtualFile vf : filesWithChanges){
            tempVFList.add(vf);
            //getting the codesmellinfo for each vf to create custom CodeSmell object
            List<CodeSmellInfo> codeSmellInfos = codeSmellDetector.findCodeSmells(tempVFList);
            for(CodeSmellInfo csi: codeSmellInfos){
                CodeSmell codeSmell = new CodeSmell(csi, vf);
                codeSmells.add(codeSmell);
            }

            tempVFList.clear();
        }
    }

    private void calculateTotalSmellScore(){
        for(CodeSmell cs : codeSmells){
            totalSmellScore += cs.getSmellScore();
        }
    }

    private void calculateTotalRecommendationCound(){
        for (CodeSmell cs : codeSmells){
            if(!cs.getSeverity().equals("ERROR")){
                recommendationCount++;
            }
            else{
                this.isFailed = true;
            }
        }
    }

    public int getTotalSmellScore() {
        return totalSmellScore;
    }

    public boolean isFailed() {
        return isFailed;
    }

    public void setFailed(boolean failed) {
        isFailed = failed;
    }

    public Timestamp getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Timestamp buildTime) {
        this.buildTime = buildTime;
    }

    public int getRecommendationCount() {
        return recommendationCount;
    }

    public void setRecommendationCount(int recommendationCount) {
        this.recommendationCount = recommendationCount;
    }

    public void setTotalSmellScore(int totalSmellScore) {
        this.totalSmellScore = totalSmellScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<CodeSmell> getCodeSmells() {
        return codeSmells;
    }

    public void setCodeSmells(List<CodeSmell> codeSmells) {
        this.codeSmells = codeSmells;
    }
}
