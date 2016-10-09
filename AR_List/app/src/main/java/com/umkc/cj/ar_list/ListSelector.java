package com.umkc.cj.ar_list;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.List;

// Class attached to list selector activity

public class ListSelector extends AppCompatActivity implements View.OnClickListener
{
    // holds the spinner
    private Spinner spinner;
    // holds the data needed by the spinner
    private List<ListData> data;
    // holds the connection to the database
    private Data sql;
    // holds custom spinner adapter
    private SpinListAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_selector);

        // get the database
        sql = new Data(this);

        // finds the spinner
        spinner = (Spinner) findViewById(R.id.list_spinner);

        // sets listener for open list button
        View openList = findViewById(R.id.openList);
        openList.setOnClickListener(this);

        // sets listener for add list button
        View addList = findViewById(R.id.newList);
        addList.setOnClickListener(this);

        // loads the spinner
        loadSpinner();
    } // end onCreate

    // deals with loading the spinner object
    private void loadSpinner()
    {
        // retrieve list data
        data = sql.getLists();

        // create adapter for spinner
        dataAdapter = new SpinListAdapter(this, R.layout.spinner_text_view, data);

        // drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attach data to spinner
        spinner.setAdapter(dataAdapter);
    } // end loadSpinner

    @Override
    public void onClick(View v)
    {
        // switches based on the id of the button clicked
        switch (v.getId())
        {
            case R.id.openList:
            {
                // get item at selected location
                ListData list = dataAdapter.getItem(spinner.getSelectedItemPosition());

                // create new intent
                Intent intent = new Intent(getBaseContext(), ListViewer.class);

                // add list id number to intent
                intent.putExtra("EXTRA_LIST_ID", list.getNumber());

                // launch List Viewer activity
                startActivity(intent);

                break;
            } // end case openList
            case R.id.newList:
            {
                // creates new builder
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                // inflates the view for adding list
                View viewInflated = LayoutInflater.from(this).inflate(R.layout.add_list_layout,
                        (ViewGroup) findViewById(android.R.id.content), false);

                // gets the input from adding list
                final EditText input = (EditText) viewInflated.findViewById(R.id.listname);

                // inflate and set the layout for the dialog
                builder.setView(viewInflated)
                        // add action buttons
                        .setPositiveButton(R.string.add_item, new DialogInterface.OnClickListener()
                        {
                            // on click add data
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                // dismiss dialog
                                dialog.dismiss();

                                // add data to database
                                sql.addList(input.getText().toString());

                                // refresh spinner
                                loadSpinner();
                            } // end onClick
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                        {
                            // on click cancel action
                            public void onClick(DialogInterface dialog, int id)
                            {
                                // close dialog
                                dialog.cancel();
                            } // end onClick
                        }); // end setView

                // show builder
                builder.show();
                break;
            } // end case newList
        } // end switch
    } // end OnClick
} // end ListSelector
