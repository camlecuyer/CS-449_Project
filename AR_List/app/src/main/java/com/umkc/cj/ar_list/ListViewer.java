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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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
    // holds the value of the selected item
    ListItemData selectedItem;
    // holds the size of the listView
    int listSize;
    //holds the position of selected item
    int curPosition = -1;
    // holds the img of the selected item
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_viewer);

        // get the database
        sql = Data.getInstance(this);

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

        // selects an item in the list
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // changes image to unselected
                if(curPosition != position && curPosition != -1)
                {
                    img.setImageResource(R.mipmap.item_unselected);
                } // end if

                // sets the curPosition of selectedItem
                curPosition = position;

                // saves the selectedItem info
                selectedItem = (ListItemData) parent.getItemAtPosition(position);

                // gets the ImageView associated with selectedItem
                img = (ImageView)view.findViewById(R.id.item_select);

                // changes the image to selected
                img.setImageResource(R.mipmap.item_selected);
            }
        });

        // get number of items in the list
        listSize = lv.getCount();

        // reset position
        curPosition = -1;
    } // end loadList

    // displays error message
    private void makeToast(String message)
    {
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    } // end makeToast

    // calls database to add item
    private void addItem(String name, int num)
    {
        // add data to database
        try
        {
            sql.addItemWithValidation(name, num, listID);
        }
        catch (Exception e)
        {
            makeToast("Add item failed: " + e.toString());
        } // end try/catch
    } // end addItem

    // calls database to delete item
    private void deleteItem(int id)
    {
        // delete data in database
        try
        {
            sql.deleteItemWithValidation(id);
        }
        catch (Exception e)
        {
            makeToast("Delete item failed: " + e.toString());
        } // end try/catch
    } // end deleteItem

    // calls database to update item
    private void updateItem(String name, int id, int num)
    {
        // update data in database
        try
        {
            sql.updateItemWithValidation(id, name, num);
        }
        catch (Exception e)
        {
            makeToast("Update item failed: " + e.toString());
        } // end try/catch
    } // end updateItem

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
        // creates new builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // switches based on the id of the button clicked
        switch (item.getItemId())
        {
            case R.id.add:
            {
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

                                // holder for input num
                                int num;

                                // tests to see if good input
                                if(inputQuantity.getText().toString().trim().isEmpty())
                                {
                                    num = 0;
                                }
                                else
                                {
                                    num = Integer.parseInt(inputQuantity.getText().toString().trim());
                                } // end if

                                // calls add
                                addItem(inputName.getText().toString().trim(), num);

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
            case R.id.delete:
            {
                if(listSize > 0 && curPosition != -1)
                {
                    if(selectedItem != null)
                    {
                        builder.setMessage("Are you sure you want to delete item: " + selectedItem.getName());
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            int num = selectedItem.getNumber();

                            public void onClick(DialogInterface dialog, int id) {
                                deleteItem(num);

                                // refresh list
                                loadList();
                            } // end OnClick
                        }); // builder setPositiveButton
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                                // close dialog
                                dialog.cancel();
                            } // end OnClick
                        }); // builder setNegativeButton

                        // show builder
                        builder.show();
                        return true;
                    } // end if
                } // end if
            }
            case R.id.update:
            {
                if(listSize > 0 && curPosition != -1)
                {
                    // inflates the view for adding item
                    View viewInflated = LayoutInflater.from(this).inflate(R.layout.add_list_item_layout,
                            (ViewGroup) findViewById(android.R.id.content), false);

                    // retrieve data from adding item
                    final EditText inputName = (EditText) viewInflated.findViewById(R.id.itemname);
                    inputName.setText(selectedItem.getName());
                    final EditText inputQuantity = (EditText) viewInflated.findViewById(R.id.itemquantity);
                    inputQuantity.setText(selectedItem.getQuantity() + "");

                    // inflate and set the layout for the dialog
                    builder.setView(viewInflated)
                            // Add action buttons
                            .setPositiveButton(R.string.update, new DialogInterface.OnClickListener()
                            {
                                // on click add data
                                @Override
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    // dismiss dialog
                                    dialog.dismiss();

                                    // holder for input num
                                    int num;
                                    int itemId = selectedItem.getNumber();

                                    // tests to see if good input
                                    if(inputQuantity.getText().toString().trim().isEmpty())
                                    {
                                        num = 0;
                                    }
                                    else
                                    {
                                        num = Integer.parseInt(inputQuantity.getText().toString().trim());
                                    } // end if

                                    // calls add
                                    updateItem(inputName.getText().toString().trim(), itemId, num);

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
                } // end if
            }
            default:
            {
                // returns a default action
                return super.onOptionsItemSelected(item);
            }
        } // end switch
    } // end onOptionsItemSelected
} // end ListViewer
