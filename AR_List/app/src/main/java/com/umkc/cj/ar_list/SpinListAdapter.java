package com.umkc.cj.ar_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

// Custom spinner adapter for ListData class

public class SpinListAdapter extends ArrayAdapter<ListData>
{
    // holds context
    private Context context;
    // holds values list
    private List<ListData> values;

    // Constructor
    public SpinListAdapter(Context context, int textViewResourceId, List<ListData> values)
    {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    } // end constructor

    // Accessors
    // Returns size of values list
    public int getCount()
    {
        return values.size();
    } // end getCount

    // Returns the item at selected position
    public ListData getItem(int position)
    {
        return values.get(position);
    } // end getItem

    // Returns location of selected item
    public long getItemId(int position)
    {
        return position;
    } // end getItemId

    // Sets the view for the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // get the view
        View v = convertView;

        // if view is null inflate it with correct layout
        if (v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.spinner_text_view, null);
        } // end if

        // set new ListData item to ListData at current position
        ListData p = values.get(position);

        // if p is not empty
        if (p != null)
        {
            // find spinner item
            TextView label = (TextView) v.findViewById(R.id.textViewSpinnerItem);

            // if spinner item found
            if (label != null)
            {
                // set spinner to selected item
                label.setText(p.getName());
            } // end if
        } // end if

        // return the view
        return v;
    } // end getView

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        // get the view
        View v = convertView;

        // if view is null inflate it with correct layout
        if (v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.spinner_text_view, null);
        } // end if

        // set new ListData item to ListData at current position
        ListData p = values.get(position);

        // if p is not empty
        if (p != null)
        {
            // find spinner item
            TextView label = (TextView) v.findViewById(R.id.textViewSpinnerItem);

            // if spinner item found
            if (label != null)
            {
                // set spinner dropdown to selected item
                label.setText(p.getName());
            } // end if
        } // end if

        // return the view
        return v;
    } // end getDropDownView
} // end class SpinListAdapter
