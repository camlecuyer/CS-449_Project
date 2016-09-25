package com.umkc.cj.ar_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ListSelector extends AppCompatActivity
{
    Spinner spinner;
    List<ListData> data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_selector);

        Data sql = new Data(this);
        data = sql.getLists();

        spinner = (Spinner) findViewById(R.id.list_spinner);

        loadSpinner();
    } // end onCreate

    void loadSpinner()
    {
        // Create adapter for spinner
        ArrayAdapter<ListData> dataAdapter = new ArrayAdapter<ListData>(this,
                R.layout.spinner_text_view, data);

        // Drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attach data to spinner
        spinner.setAdapter(dataAdapter);
    } // end loadSpinner
} // end ListSelector
