package com.statistics.corona.model;

import com.opencsv.bean.CsvBindByPosition;

public class DailyReportUsDto {

    @CsvBindByPosition(position = 0)
    String province;
    @CsvBindByPosition(position = 1)
    String country;
    @CsvBindByPosition(position = 2)
    String lastUpdate;
    @CsvBindByPosition(position = 5)
    Integer confirmed;
    @CsvBindByPosition(position = 6)
    Integer deaths;
    @CsvBindByPosition(position = 7)
    Double recovered;
    @CsvBindByPosition(position = 8)
    Double active;
    @CsvBindByPosition(position = 10)
    Double incidentRate;
    @CsvBindByPosition(position = 11)
    Double peopleTested;
    @CsvBindByPosition(position = 12)
    Double peopleHospitalized;
    @CsvBindByPosition(position = 13)
    Double mortalityRate;
    @CsvBindByPosition(position = 16)
    Double testingRate;
    @CsvBindByPosition(position = 17)
    Double hospitalizationRate;

    public DailyReportUsDto() {
        //Always needed an empty constructor
    }

    public DailyReportUsDto(DailyReportUsDto dailyReportUsDto) {
        this.province = dailyReportUsDto.getProvince();
        this.country = dailyReportUsDto.getCountry();
        this.lastUpdate = dailyReportUsDto.getLastUpdate();
        this.confirmed = dailyReportUsDto.getConfirmed();
        this.deaths = dailyReportUsDto.getDeaths();
        this.recovered = dailyReportUsDto.getRecovered();
        this.active = dailyReportUsDto.getActive();
        this.incidentRate = dailyReportUsDto.getIncidentRate();
        this.peopleTested = dailyReportUsDto.getPeopleTested();
        this.peopleHospitalized = dailyReportUsDto.getPeopleHospitalized();
        this.mortalityRate = dailyReportUsDto.getMortalityRate();
        this.testingRate = dailyReportUsDto.getTestingRate();
        this.hospitalizationRate = dailyReportUsDto.getHospitalizationRate();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Double getRecovered() {
        return recovered;
    }

    public void setRecovered(Double recovered) {
        this.recovered = recovered;
    }

    public Double getActive() {
        return active;
    }

    public void setActive(Double active) {
        this.active = active;
    }

    public Double getIncidentRate() {
        return incidentRate;
    }

    public void setIncidentRate(Double incidentRate) {
        this.incidentRate = incidentRate;
    }

    public Double getPeopleTested() {
        return peopleTested;
    }

    public void setPeopleTested(Double peopleTested) {
        this.peopleTested = peopleTested;
    }

    public Double getPeopleHospitalized() {
        return peopleHospitalized;
    }

    public void setPeopleHospitalized(Double peopleHospitalized) {
        this.peopleHospitalized = peopleHospitalized;
    }

    public Double getMortalityRate() {
        return mortalityRate;
    }

    public void setMortalityRate(Double mortalityRate) {
        this.mortalityRate = mortalityRate;
    }

    public Double getTestingRate() {
        return testingRate;
    }

    public void setTestingRate(Double testingRate) {
        this.testingRate = testingRate;
    }

    public Double getHospitalizationRate() {
        return hospitalizationRate;
    }

    public void setHospitalizationRate(Double hospitalizationRate) {
        this.hospitalizationRate = hospitalizationRate;
    }

    @Override
    public String toString() {
        return "DailyReportUsDto{" +
                "province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", confirmed=" + confirmed +
                ", deaths=" + deaths +
                ", recovered=" + recovered +
                ", active=" + active +
                ", incidentRate=" + incidentRate +
                ", peopleTested=" + peopleTested +
                ", peopleHospitalized=" + peopleHospitalized +
                ", mortalityRate=" + mortalityRate +
                ", testingRate=" + testingRate +
                ", hospitalizationRate=" + hospitalizationRate +
                '}';
    }
}