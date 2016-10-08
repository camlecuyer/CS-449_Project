package com.umkc.cj.ar_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemListAdapter extends ArrayAdapter
{
    private final Context context;
    private final ArrayList<ListItemData> values;

    public ItemListAdapter(Context context, ArrayList<ListItemData> values)
    {
        super(context, R.layout.list_item_layout, values);
        this.context = context;
        this.values = values;
    } // end constructor

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_item_layout, parent, false);

        TextView nameView = (TextView) rowView.findViewById(R.id.item_name);
        nameView.setText(values.get(position).getName());

        TextView quantityView = (TextView) rowView.findViewById(R.id.item_quantity);
        quantityView.setText(String.valueOf(values.get(position).getQuantity()));

        return rowView;
    } // end getView
} // end class ItemListAdapter
