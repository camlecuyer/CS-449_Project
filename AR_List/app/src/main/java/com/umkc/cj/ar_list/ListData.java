package com.umkc.cj.ar_list;

/**
 * Created by CJ on 9/25/2016.
 */

public class ListData
{
    private String name;
    private int number;

    // Constructors
    ListData()
    {
        name = "";
        number = 0;
    } // end default constructor

    ListData(String tempName, int num)
    {
        name = tempName;
        number = num;
    } // end parameter constructor

    // Accessors
    String getName()
    {
        return name;
    } // end getName

    int getNumber()
    {
        return number;
    } // end get Number

    public String toString()
    {
        return(getName());
    } // end toString
}
