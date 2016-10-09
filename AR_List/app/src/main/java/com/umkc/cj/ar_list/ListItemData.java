package com.umkc.cj.ar_list;

// Class holds the data retrieved from the database related to a list item

public class ListItemData
{
    // holds the name of the list item
    private String name;
    // holds the id of the list item
    private int number;
    // holds the quantity of the list item
    private int quantity;

    // Constructors
    // Default constructor
    ListItemData()
    {
        name = "";
        number = 0;
        quantity = 0;
    } // end default constructor

    // Parameter constructor
    // pass in the name, id number, and quanity
    ListItemData(String tempName, int num, int quan)
    {
        name = tempName;
        number = num;
        quantity = quan;
    } // end parameter constructor

    // Accessors
    // Returns the name of the list item
    String getName()
    {
        return name;
    } // end getName

    // Returns the id number of the list item
    int getNumber()
    {
        return number;
    } // end getNumber

    // Returns the quantity of the list item
    int getQuantity()
    {
        return quantity;
    } // end getQuantity

    // Prints the name of the list item
    public String toString()
    {
        return(getName());
    } // end toString
} // end class ListItemData
