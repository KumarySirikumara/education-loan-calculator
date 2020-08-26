package com.example.educationloancalculator.HistoryRepository;

public class HistoryRepository {
    float lAmount, iRate;
    String ratePer, termPeriod, sysDate;
    int lTerm;

    public HistoryRepository() {
    }

    public HistoryRepository(float lAmount, float iRate, String ratePer, String termPeriod, String sysDate, int lTerm) {
        this.lAmount = lAmount;
        this.iRate = iRate;
        this.ratePer = ratePer;
        this.termPeriod = termPeriod;
        this.sysDate = sysDate;
        this.lTerm = lTerm;
    }

    public float getlAmount() {
        return lAmount;
    }

    public void setlAmount(float lAmount) {
        this.lAmount = lAmount;
    }

    public float getiRate() {
        return iRate;
    }

    public void setiRate(float iRate) {
        this.iRate = iRate;
    }

    public String getRatePer() {
        return ratePer;
    }

    public void setRatePer(String ratePer) {
        this.ratePer = ratePer;
    }

    public String getTermPeriod() {
        return termPeriod;
    }

    public void setTermPeriod(String termPeriod) {
        this.termPeriod = termPeriod;
    }

    public String getSysDate() {
        return sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    public int getlTerm() {
        return lTerm;
    }

    public void setlTerm(int lTerm) {
        this.lTerm = lTerm;
    }
}
