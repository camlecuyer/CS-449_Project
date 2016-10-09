package com.umkc.cj.ar_list;

// Class holds the data retrieved from the database related to a list

public class ListData
{
    // holds the name of the list
    private String name;
    // holds the id number of the list
    private int number;

    // Constructors
    // Default constructor
    ListData()
    {
        name = "";
        number = 0;
    } // end default constructor

    // Parameter constructor
    // pass in the name and id number of the list
    ListData(String tempName, int num)
    {
        name = tempName;
        number = num;
    } // end parameter constructor

    // Accessors
    // Returns the name of the list
    String getName()
    {
        return name;
    } // end getName

    // Return the id number of the list
    int getNumber()
    {
        return number;
    } // end getNumber

    // prints the name of the list
    public String toString()
    {
        return(getName());
    } // end toString
} // end class ListData
