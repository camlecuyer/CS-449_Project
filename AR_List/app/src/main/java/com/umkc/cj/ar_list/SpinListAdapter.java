package com.umkc.cj.ar_list;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinListAdapter extends ArrayAdapter<ListData>
{

    private Context context;
    private List<ListData> values;

    public SpinListAdapter(Context context, int textViewResourceId, List<ListData> values)
    {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    } // end constructor

    // Accessors
    public int getCount()
    {
        return values.size();
    } // end getCount

    public ListData getItem(int position)
    {
        return values.get(position);
    } // end getItem

    public long getItemId(int position)
    {
        return position;
    } // end getItemId

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if (v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.spinner_text_view, null);
        } // end if

        ListData p = values.get(position);

        if (p != null)
        {
            TextView label = (TextView) v.findViewById(R.id.textViewSpinnerItem);

            if (label != null)
            {
                label.setText(p.getName());
            } // end if
        } // end if

        return v;
    } // end getView

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {

        View v = convertView;

        if (v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.spinner_text_view, null);
        } // end if

        ListData p = values.get(position);

        if (p != null)
        {
            TextView label = (TextView) v.findViewById(R.id.textViewSpinnerItem);

            if (label != null)
            {
                label.setText(p.getName());
            } // end if
        } // end if

        return v;
    } // end getDropDownView
} // end class SpinListAdapter
