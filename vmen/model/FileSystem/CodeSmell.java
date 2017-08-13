package it.bandits.nemodomus.vmen.model.FileSystem;

/**
 * Created by Nemo Domus on 8/12/2017.
 */

public class CodeSmell {
    private int lineNum;
    private int individualScore;
    private String severity;
    private String error_detail;

    public CodeSmell(int lineNum, int individualScore, String severity, String error_detail) {
        this.lineNum = lineNum;
        this.individualScore = individualScore;
        this.severity = severity;
        this.error_detail = error_detail;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public int getIndividualScore() {
        return individualScore;
    }

    public void setIndividualScore(int individualScore) {
        this.individualScore = individualScore;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getError_detail() {
        return error_detail;
    }

    public void setError_detail(String error_detail) {
        this.error_detail = error_detail;
    }
}
