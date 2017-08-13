package it.bandits.nemodomus.vmen.model;

/**
 * Created by Nemo Domus on 8/12/2017.
 */

public class Measurement {
    private int value;
    private String timeStamp;

    public Measurement(int value, String timeStamp) {
        this.value = value;
        this.timeStamp = timeStamp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
