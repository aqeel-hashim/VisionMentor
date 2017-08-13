package it.bandits.nemodomus.vmen.model.FileSystem;

import java.util.ArrayList;

/**
 * Created by Nemo Domus on 8/12/2017.
 */

public class Build {
    private boolean isBuildFail;
    private int buildNum;
    private String timeStamp;
    private int recommendationScore;
    private int overallSmellScore;
    private ArrayList<CodeSmell> smells;

    public Build(boolean isBuildFail, int buildNum, String timeStamp, int recommendationScore, int overallSmellScore) {
        this.isBuildFail = isBuildFail;
        this.buildNum = buildNum;
        this.timeStamp = timeStamp;
        this.recommendationScore = recommendationScore;
        this.overallSmellScore = overallSmellScore;
        this.smells = new ArrayList<>();
    }

    public Build(boolean isBuildFail, int buildNum, String timeStamp, int recommendationScore, int overallSmellScore, ArrayList<CodeSmell> smells) {
        this.isBuildFail = isBuildFail;
        this.buildNum = buildNum;
        this.timeStamp = timeStamp;
        this.recommendationScore = recommendationScore;
        this.overallSmellScore = overallSmellScore;
        this.smells = smells;
    }

    public boolean isBuildFail() {
        return isBuildFail;
    }

    public void setBuildFail(boolean buildFail) {
        isBuildFail = buildFail;
    }

    public int getBuildNum() {
        return buildNum;
    }

    public void setBuildNum(int buildNum) {
        this.buildNum = buildNum;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getRecommendationScore() {
        return recommendationScore;
    }

    public void setRecommendationScore(int recommendationScore) {
        this.recommendationScore = recommendationScore;
    }

    public int getOverallSmellScore() {
        return overallSmellScore;
    }

    public void setOverallSmellScore(int overallSmellScore) {
        this.overallSmellScore = overallSmellScore;
    }

    public ArrayList<CodeSmell> getSmells() {
        return smells;
    }

    public void setSmells(ArrayList<CodeSmell> smells) {
        this.smells = smells;
    }

    public void addSmell(CodeSmell smell){
        this.smells.add(smell);
    }

    public CodeSmell getSmellAtPosition(int position){
        return this.smells.get(position);
    }
}
