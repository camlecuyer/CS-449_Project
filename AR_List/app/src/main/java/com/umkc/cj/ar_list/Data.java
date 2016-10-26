package com.umkc.cj.ar_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.umkc.cj.ar_list.ListData;
import com.umkc.cj.ar_list.ListItemData;

import java.util.ArrayList;
import java.util.List;

// Primary purpose of this class is to interact with and create a database

public class Data extends SQLiteOpenHelper
{
    // database info
    private static final String DATABASE_NAME = "ARData";
    private static final int DATABASE_VERSION = 2;

    // table names
    private static final String DATA_TABLE_LIST = "Lists";
    private static final String DATA_TABLE_ITEMS = "List_Items";

    // List column names
    private static final String KEY_LIST_ID = "ID";
    private static final String KEY_LIST_NAME = "NAME";

    // List Item column names
    private static final String KEY_LIST_ITEM_ID = "ID";
    private static final String KEY_LIST_ITEM_NAME = "NAME";
    private static final String KEY_LIST_ITEM_QUANTITY = "QUANTITY";
    private static final String KEY_LIST_ITEM_FK = "LIST_ID";

    // Constructor
    public Data(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    } // end constructor

    // Initialize database
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // build table creation query for lists
        String DATA_TABLE_CREATE =
                "CREATE TABLE " + DATA_TABLE_LIST + " (" +
                        KEY_LIST_ID + " INTEGER PRIMARY KEY, " +
                        KEY_LIST_NAME + " TEXT);";

        // build table creation query for list items
        String DATA_TABLE_CREATE_2 =
                "CREATE TABLE " + DATA_TABLE_ITEMS + " (" +
                        KEY_LIST_ITEM_ID + " INTEGER PRIMARY KEY, " +
                        KEY_LIST_ITEM_NAME + " TEXT, " +
                        KEY_LIST_ITEM_QUANTITY + " INTEGER, " +
                        KEY_LIST_ITEM_FK + " INTEGER REFERENCES "+ DATA_TABLE_LIST + ");";

        // execute table creation
        db.execSQL(DATA_TABLE_CREATE);
        db.execSQL(DATA_TABLE_CREATE_2);
    } // end onCreate

    // If database changes delete tables and start again
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // if the versions have changed
        if(oldVersion != newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + DATA_TABLE_LIST);
            db.execSQL("DROP TABLE IF EXISTS " + DATA_TABLE_ITEMS);
            onCreate(db);
        } // end if
    } // end onUpgrade

    // Add an item to the list table
    // pass in the name of the list as a string
    public void addList(String name)
    {
        // gets the database
        SQLiteDatabase db = getWritableDatabase();

        // starts connection to the database
        db.beginTransaction();

        // tries to insert to database, throws error if insert failed
        try
        {
            // get values
            ContentValues values = new ContentValues();

            // put the values into the corresponding location
            values.put(KEY_LIST_NAME, name);

            // attempt to insert
            db.insertOrThrow(DATA_TABLE_LIST, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            Log.d("add", "Error trying to add list to database");
        }
        finally
        {
            // close connection
            db.endTransaction();
        } // end try/catch
    } // end addList

    // Add an item to the list table
    // pass in the name of the list as a string
    public void addListItem(String name, int quantity, int foreignKey)
    {
        // gets the database
        SQLiteDatabase db = getWritableDatabase();

        // starts connection to the database
        db.beginTransaction();

        // tries to insert to database, throws error if insert failed
        try
        {
            // get values
            ContentValues values = new ContentValues();

            // put the values into the corresponding location
            values.put(KEY_LIST_ITEM_NAME, name);
            values.put(KEY_LIST_ITEM_QUANTITY, quantity);
            values.put(KEY_LIST_ITEM_FK, foreignKey);

            // attempt to insert
            db.insertOrThrow(DATA_TABLE_ITEMS, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            Log.d("addItem", "Error trying to add list item to database");
        }
        finally
        {
            // close connection
            db.endTransaction();
        } // end try/catch
    } // end addListItem

    // Get all lists in the database
    public List<ListData> getLists()
    {
        // Select all lists
        String LISTS_SELECT_QUERY = "SELECT * FROM " + DATA_TABLE_LIST;

        // gets the database
        SQLiteDatabase db = getReadableDatabase();

        // set cursor to new data
        Cursor cursor = db.rawQuery(LISTS_SELECT_QUERY, null);

        // create a list to hold data
        List<ListData> data = new ArrayList<ListData>();

        // attempt to get data
        try
        {
            // move cursor to start
            if (cursor.moveToFirst())
            {
                // loop through data
                do
                {
                    // create ListData item
                    ListData dataCall =
                            new ListData(cursor.getString(cursor.getColumnIndex(KEY_LIST_NAME)),
                                    cursor.getInt(cursor.getColumnIndex(KEY_LIST_ID)));

                    // add ListData item to list
                    data.add(dataCall);
                } while(cursor.moveToNext()); // end loop
            } // end if
        }
        catch (Exception e)
        {
            Log.d("get", "Error while trying to get lists from database");
        }
        finally
        {
            // close the cursor if not done already
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            } // end if

            // close connection
            db.close();
        } // end try/catch

        // return list of data
        return data;
    } // end getLists

    // Get all list items with same listID in the database
    public ArrayList<ListItemData> getListItems(int listID)
    {
        // Select all items with same listID
        String LISTS_SELECT_QUERY = "SELECT * FROM " + DATA_TABLE_ITEMS + " WHERE "
                + KEY_LIST_ITEM_FK + " = " + listID;

        // gets the database
        SQLiteDatabase db = getReadableDatabase();

        // set cursor to new data
        Cursor cursor = db.rawQuery(LISTS_SELECT_QUERY, null);

        // create list to hold data
        ArrayList<ListItemData> data = new ArrayList<ListItemData>();

        // attempt to get data
        try
        {
            // move cursor to start
            if (cursor.moveToFirst())
            {
                // loop through data
                do
                {
                    // create ListItemData item
                    ListItemData dataCall =
                            new ListItemData(cursor.getString(cursor.getColumnIndex(KEY_LIST_ITEM_NAME)),
                                    cursor.getInt(cursor.getColumnIndex(KEY_LIST_ITEM_ID)),
                                    cursor.getInt(cursor.getColumnIndex(KEY_LIST_ITEM_QUANTITY)));

                    // add ListItemData item to list
                    data.add(dataCall);
                } while(cursor.moveToNext()); // end loop
            } // end if
        }
        catch (Exception e)
        {
            Log.d("getItem", "Error while trying to get list items from database");
        }
        finally
        {
            // close the cursor if not done already
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            } // end if

            // close connection
            db.close();
        } // end try/catch

        // return list of data
        return data;
    } // end getListItems

    // Delete all list items associated with a list
    // then deletes the list
    public void deleteList(int listID)
    {
        // Delete all items with same listID
        String LIST_ITEMS_DELETE_QUERY = "DELETE FROM " + DATA_TABLE_ITEMS + " WHERE "
                + KEY_LIST_ITEM_FK + " = " + listID;

        // Delete list with listID
        String LISTS_DELETE_QUERY = "DELETE FROM " + DATA_TABLE_LIST + " WHERE "
                + KEY_LIST_ID + " = " + listID;

        // gets the database
        SQLiteDatabase db = getWritableDatabase();

        // starts connection to the database
        db.beginTransaction();

        // attempt to get data
        try
        {
            db.execSQL(LIST_ITEMS_DELETE_QUERY);
            db.execSQL(LISTS_DELETE_QUERY);

            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            Log.d("deleteList", "Error while trying to delete list from database");
        }
        finally
        {
            // close connection
            db.endTransaction();
        } // end try/catch
    } // end getListItems
} // end Data
