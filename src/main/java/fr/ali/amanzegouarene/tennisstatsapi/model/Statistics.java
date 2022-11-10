package fr.ali.amanzegouarene.tennisstatsapi.model;

public class Statistics {
    private Country countryWithBestRatio;
    private Double playersIMCAverage;
    private Integer heightPlayersMedian;

    public Statistics() {
    }

    public Statistics(Country countryWithBestRatio, double imcPlayersAverage, Integer heightPlayersMedian) {
        this.countryWithBestRatio = countryWithBestRatio;
        this.playersIMCAverage = imcPlayersAverage;
        this.heightPlayersMedian = heightPlayersMedian;
    }

    public Country getCountryWithBestRatio() {
        return countryWithBestRatio;
    }

    public void setCountryWithBestRatio(Country countryWithBestRatio) {
        this.countryWithBestRatio = countryWithBestRatio;
    }

    public Double getPlayersIMCAverage() {
        return playersIMCAverage;
    }

    public void setPlayersIMCAverage(Double playersIMCAverage) {
        this.playersIMCAverage = playersIMCAverage;
    }

    public Integer getHeightPlayersMedian() {
        return heightPlayersMedian;
    }

    public void setHeightPlayersMedian(Integer heightPlayersMedian) {
        this.heightPlayersMedian = heightPlayersMedian;
    }
}
