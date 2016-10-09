package com.umkc.cj.ar_list;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

// Class attached to list viewer activity

public class ListViewer extends AppCompatActivity implements OnClickListener
{
    // holds database connection
    private Data sql;
    // holds id number passed from list selector
    private int listID;
    // holds the list of items
    ArrayList<ListItemData> items;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_viewer);

        // get the database
        sql = new Data(this);

        // get extras from caller activity
        Bundle extras = getIntent().getExtras();

        // if extras found
        if (extras != null)
        {
            // extract data
            listID = extras.getInt("EXTRA_LIST_ID");
        } // end if

        // loads the list
        loadList();
    } // end onCreate

    // deals with loading the list view object
    private void loadList()
    {
        // retrieves the list of items from database
        items = sql.getListItems(listID);

        // find the list view
        ListView lv=(ListView) findViewById(R.id.list_viewer);

        // place the data into the list
        lv.setAdapter(new ItemListAdapter(this, items));
    } // end loadList

    @Override
    public void onClick(View v)
    {

    } // end OnClick

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    } // end onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // switches based on the id of the button clicked
        switch (item.getItemId())
        {
            case R.id.add:
            {
                // creates new builder
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                // inflates the view for adding item
                View viewInflated = LayoutInflater.from(this).inflate(R.layout.add_list_item_layout,
                        (ViewGroup) findViewById(android.R.id.content), false);

                // retrieve data from adding item
                final EditText inputName = (EditText) viewInflated.findViewById(R.id.itemname);
                final EditText inputQuantity = (EditText) viewInflated.findViewById(R.id.itemquantity);

                // inflate and set the layout for the dialog
                builder.setView(viewInflated)
                        // Add action buttons
                        .setPositiveButton(R.string.add_item, new DialogInterface.OnClickListener()
                        {
                            // on click add data
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                // dismiss dialog
                                dialog.dismiss();

                                // add data to database
                                sql.addListItem(inputName.getText().toString(),
                                        Integer.parseInt(inputQuantity.getText().toString()),
                                        listID);

                                // refresh list
                                loadList();
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
                return true;
            }
            default:
            {
                // returns a default action
                return super.onOptionsItemSelected(item);
            }
        } // end switch
    } // end onOptionsItemSelected

    /*@Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        ListItemData item = (ListItemData) getListAdapter().getItem(position);

    } // end onListItemClick*/
} // end ListViewer
