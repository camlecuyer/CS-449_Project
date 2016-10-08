package com.umkc.cj.ar_list;

/**
 * Created by CJ on 9/25/2016.
 */

public class ListItemData
{
    private String name;
    private int number;
    private int quantity;

    // Constructors
    ListItemData()
    {
        name = "";
        number = 0;
        quantity = 0;
    } // end default constructor

    ListItemData(String tempName, int num, int quan)
    {
        name = tempName;
        number = num;
        quantity = quan;
    } // end parameter constructor

    // Accessors
    String getName()
    {
        return name;
    } // end getName

    int getNumber()
    {
        return number;
    } // end getNumber

    int getQuantity()
    {
        return quantity;
    } // end getQuantity

    public String toString()
    {
        return(getName());
    } // end toString
} // end class ListItemData
