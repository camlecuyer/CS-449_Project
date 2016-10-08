package com.umkc.cj.ar_list;

import android.app.ListActivity;
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
import android.widget.Toast;
import java.util.ArrayList;


public class ListViewer extends AppCompatActivity implements OnClickListener
{
    private Data sql;
    private int listID;
    ArrayList<ListItemData> items;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_viewer);

        sql = new Data(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null)
        {
            listID = extras.getInt("EXTRA_LIST_ID");
        } // end if

        loadList();
    } // end onCreate

    private void loadList()
    {
        items = sql.getListItems(listID);

        ListView lv=(ListView) findViewById(R.id.list_viewer);
        lv.setAdapter(new ItemListAdapter(this, items));
    } // end loadList

    @Override
    public void onClick(View v)
    {

    } // end OnClick

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    } // end onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.add:
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // Get the layout inflater
                View viewInflated = LayoutInflater.from(this).inflate(R.layout.add_list_item_layout,
                        (ViewGroup) findViewById(android.R.id.content), false);
                final EditText inputName = (EditText) viewInflated.findViewById(R.id.itemname);
                final EditText inputQuantity = (EditText) viewInflated.findViewById(R.id.itemquantity);

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
                                sql.addListItem(inputName.getText().toString(),
                                        Integer.parseInt(inputQuantity.getText().toString()),
                                        listID);
                                loadList();
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
                return true;
            }
            default:
            {
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
