package com.example.coronapp.Model;

public class Country implements Comparable<Country>{

    private String countryName;
    private String totalCases;
    private String newCases;
    private String totalDeaths;
    private String newDeaths;
    private String totalRecovered;
    private String activeCases;
    private String seriousCritical;
    private String totCases1MPop;
    private String totDeaths1MPop;

    public Country() {
    }


    public Country(String activeCases, String countryName, String newCases, String newDeaths, String seriousCritical, String totCases1MPop, String totDeaths1MPop, String totalCases, String totalDeaths, String totalRecovered) {
        this.countryName = countryName;
        this.totalCases = totalCases;
        this.newCases = newCases;
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;
        this.totalRecovered = totalRecovered;
        this.activeCases = activeCases;
        this.seriousCritical = seriousCritical;
        this.totCases1MPop = totCases1MPop;
        this.totDeaths1MPop = totDeaths1MPop;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(String totalCases) {
        this.totalCases = totalCases;
    }

    public String getNewCases() {
        return newCases;
    }

    public void setNewCases(String newCases) {
        this.newCases = newCases;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public String getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(String newDeaths) {
        this.newDeaths = newDeaths;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public String getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(String activeCases) {
        this.activeCases = activeCases;
    }

    public String getSeriousCritical() {
        return seriousCritical;
    }

    public void setSeriousCritical(String seriousCritical) {
        this.seriousCritical = seriousCritical;
    }

    public String getTotCases1MPop() {
        return totCases1MPop;
    }

    public void setTotCases1MPop(String totCases1MPop) {
        this.totCases1MPop = totCases1MPop;
    }

    public String getTotDeaths1MPop() {
        return totDeaths1MPop;
    }

    public void setTotDeaths1MPop(String totDeaths1MPop) {
        this.totDeaths1MPop = totDeaths1MPop;
    }

    @Override
    public int compareTo(Country o) {
        return this.getCountryName().compareTo(o.countryName);
    }


}
