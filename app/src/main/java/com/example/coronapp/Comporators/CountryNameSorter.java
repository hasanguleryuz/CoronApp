package com.example.coronapp.Comporators;

import com.example.coronapp.Model.Country;

import java.util.Comparator;
public class CountryNameSorter implements Comparator<Country>
{
    public int compare(Country c1, Country c2)
    {
        return c1.getCountryName().compareTo(c2.getCountryName());
    }
}