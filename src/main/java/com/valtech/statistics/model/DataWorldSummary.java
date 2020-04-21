package com.valtech.statistics.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "dataWorldSummary")
public class DataWorldSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long dataWorldSummaryId;
    private int newConfirmed;
    private int totalConfirmed;
    private int newRecovered;
    private int totalRecovered;
    private int newDeaths;
    private int totalDeaths;
    private LocalDate localDate;
    private LocalTime localTime;

    public DataWorldSummary() {
    }

    public DataWorldSummary(int totalConfirmed, int totalRecovered, int totalDeaths) {
        this.totalConfirmed = totalConfirmed;
        this.totalRecovered = totalRecovered;
        this.totalDeaths = totalDeaths;
    }

    public long getDataWorldSummaryId() {
        return dataWorldSummaryId;
    }

    public void setDataWorldSummaryId(long dataWorldSummaryId) {
        this.dataWorldSummaryId = dataWorldSummaryId;
    }

    public int getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(int newConfirmed) {
        this.newConfirmed = newConfirmed;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(int totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public int getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(int newRecovered) {
        this.newRecovered = newRecovered;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(int newDeaths) {
        this.newDeaths = newDeaths;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }
}