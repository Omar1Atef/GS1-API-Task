package com.assessment.api.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvalidBookRequest {

    private String caseName;
    private String id;
    private int expectedStatus;
    private String expectedTitle;

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getExpectedStatus() {
        return expectedStatus;
    }

    public void setExpectedStatus(int expectedStatus) {
        this.expectedStatus = expectedStatus;
    }

    public String getExpectedTitle() {
        return expectedTitle;
    }

    public void setExpectedTitle(String expectedTitle) {
        this.expectedTitle = expectedTitle;
    }

    @Override
    public String toString() {
        return caseName;
    }
}
