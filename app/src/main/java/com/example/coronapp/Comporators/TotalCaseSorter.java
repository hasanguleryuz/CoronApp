package com.example.coronapp.Comporators;

import com.example.coronapp.Model.Country;

import java.util.Comparator;

public class TotalCaseSorter implements Comparator<Country> {
    public int compare(Country c1, Country c2)
    {
        return c2.getTotalCases().compareTo(c1.getTotalCases());
    }
}
