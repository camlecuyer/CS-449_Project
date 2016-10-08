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

public class ListSelector extends AppCompatActivity implements View.OnClickListener
{
    private Spinner spinner;
    private List<ListData> data;
    private Data sql;
    private SpinListAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_selector);

        sql = new Data(this);

        spinner = (Spinner) findViewById(R.id.list_spinner);

        View openList = findViewById(R.id.openList);
        openList.setOnClickListener(this);

        View addList = findViewById(R.id.newList);
        addList.setOnClickListener(this);

        loadSpinner();
    } // end onCreate

    private void loadSpinner()
    {
        data = sql.getLists();

        // Create adapter for spinner
        dataAdapter = new SpinListAdapter(this, R.layout.spinner_text_view, data);

        // Drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attach data to spinner
        spinner.setAdapter(dataAdapter);
    } // end loadSpinner

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.openList:
            {
                ListData list = dataAdapter.getItem(spinner.getSelectedItemPosition());

                Intent intent = new Intent(getBaseContext(), ListViewer.class);
                intent.putExtra("EXTRA_LIST_ID", list.getNumber());
                startActivity(intent);

                break;
            } // end case openList
            case R.id.newList:
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // Get the layout inflater
                //LayoutInflater inflater = this.getLayoutInflater();
                View viewInflated = LayoutInflater.from(this).inflate(R.layout.add_list_layout,
                        (ViewGroup) findViewById(android.R.id.content), false);
                final EditText input = (EditText) viewInflated.findViewById(R.id.listname);

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(viewInflated)
                        // Add action buttons
                        .setPositiveButton(R.string.add_item, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.dismiss();
                                sql.addList(input.getText().toString());
                            } // end onClick
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.cancel();
                            } // end onClick
                        }); // end setView
                builder.show();
                loadSpinner();
                break;
            } // end case newList
        } // end switch
    } // end OnClick
} // end ListSelector
