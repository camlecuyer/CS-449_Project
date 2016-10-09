package com.umkc.cj.ar_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

// Custom adapter to interact with custom ListItemData and custom ListView element

public class ItemListAdapter extends ArrayAdapter
{
    // holds context
    private final Context context;

    // holds list of values
    private final ArrayList<ListItemData> values;

    // Constructor
    public ItemListAdapter(Context context, ArrayList<ListItemData> values)
    {
        super(context, R.layout.list_item_layout, values);
        this.context = context;
        this.values = values;
    } // end constructor

    // Sets items to correct view element
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // inflates layout
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // gets the layout
        View rowView = inflater.inflate(R.layout.list_item_layout, parent, false);

        // finds the correct element and assigns data
        TextView nameView = (TextView) rowView.findViewById(R.id.item_name);
        nameView.setText(values.get(position).getName());

        // finds the correct element and assigns data
        TextView quantityView = (TextView) rowView.findViewById(R.id.item_quantity);
        quantityView.setText(String.valueOf(values.get(position).getQuantity()));

        // returns completed view
        return rowView;
    } // end getView
} // end class ItemListAdapter
