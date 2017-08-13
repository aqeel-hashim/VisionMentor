package com.visionmentor.model;

import com.intellij.codeInsight.CodeSmellInfo;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.vfs.VirtualFile;

public class CodeSmell {
    private CodeSmellInfo codeSmellInfo;
    private String directory;
    private String fileName;
    private int startingLine;
    private int smellScore;
    private String severity;
    private String description;

    public CodeSmell(CodeSmellInfo codeSmellInfo, VirtualFile fileWithSmell) {
        this.codeSmellInfo = codeSmellInfo;
        this.fileName = fileWithSmell.getName();
        this.directory = fileWithSmell.getPath().replace(fileName,"");
        this.smellScore = calculateSmellScore();
        this.startingLine = findSmellStartLine();
        this.severity = findSmellSeverity();
        this.description = codeSmellInfo.getDescription();
    }

    public int calculateSmellScore() {
        HighlightSeverity zeroSeverity = new HighlightSeverity("zero", 0);
        HighlightSeverity severity = codeSmellInfo.getSeverity();
        int smellScore = severity.compareTo(zeroSeverity);
        return smellScore;
    }

    public int findSmellStartLine(){
        return codeSmellInfo.getTextRange().getStartOffset();
    }

    public String findSmellSeverity(){
        if(smellScore == HighlightSeverity.INFORMATION.myVal){
            severity = "INFORMATION";
        }
        else if(smellScore == HighlightSeverity.WEAK_WARNING.myVal){
            severity = "WEAK WARNING";
        }
        else if(smellScore == HighlightSeverity.WARNING.myVal){
            severity = "WARNING";
        }
        else {
            severity = "ERROR";
        }

        return severity;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getStartingLine() {
        return startingLine;
    }

    public void setStartingLine(int startingLine) {
        this.startingLine = startingLine;
    }

    public int getSmellScore() {
        return smellScore;
    }

    public void setSmellScore(int smellScore) {
        this.smellScore = smellScore;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
