package com.umkc.cj.ar_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import junit.framework.Assert;

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

    // static variable for singleton
    private static Data sInstance;

    // gets the singleton instance
    public static synchronized Data getInstance(Context context)
    {
        // get the database
        if (sInstance == null)
        {
            sInstance = new Data(context.getApplicationContext());
        } // end if

        return sInstance;
    } // end getInstance

    // Constructor
    private Data(Context context)
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

    // validates data and calls addList
    // throws: Exception
    public void addListWithValidation(String name) throws Exception
    {
        if(name.length() == 0)
        {
            Exception e = new Exception("List name cannot be empty");
            throw e;
        }
        else
        {
            addList(name);
        } // end if
    } // end addListWithValidation

    // validates data and calls addListItem
    // throws: Exception
    public void addItemWithValidation(String name, int quantity, int foreignKey) throws Exception
    {
        if(name.length() == 0)
        {
            Exception e = new Exception("Item name cannot be empty");
            throw e;
        }
        else
        {
            addListItem(name, quantity, foreignKey);
        } // end if
    } // end addItemWithValidation

    // calls deleteList
    public void deleteListWithValidation(int listID)
    {
        deleteList(listID);
    } // end deleteListWithValidation

    // calls deleteItem
    public void deleteItemWithValidation(int itemID)
    {
        deleteItem(itemID);
    } // end deleteItemWithValidation

    // validates data and calls updateItem
    // throws: Exception
    public void updateItemWithValidation(int itemID, String name, int quan) throws Exception
    {
        if(name.length() == 0)
        {
            Exception e = new Exception("Item name cannot be empty");
            throw e;
        }
        else
        {
            updateItem(itemID, name, quan);
        } // end if
    } // end updateItemWithValidation

    // validates data and calls updateList
    // throws: Exception
    public void updateListWithValidation(int listID, String name) throws Exception
    {
        if(name.length() == 0)
        {
            Exception e = new Exception("Item name cannot be empty");
            throw e;
        }
        else
        {
            updateList(listID, name);
        } // end if
    } // end updateListWithValidation

    // Add an item to the list table
    // pass in the name of the list as a string
    private void addList(String name)
    {
        if (AssertSettings.PRIORITY1_ASSERTIONS)
        {
            // Assert if name is null
            Assert.assertNotNull("name is null", name);
        } // end if

        // gets the database
        SQLiteDatabase db = getWritableDatabase();

        // get values
        ContentValues values = new ContentValues();

        // put the values into the corresponding location
        values.put(KEY_LIST_NAME, name);

        // attempt to insert
        db.insertOrThrow(DATA_TABLE_LIST, null, values);
    } // end addList

    // Add an item to the list table
    // pass in the name of the list as a string
    private void addListItem(String name, int quantity, int foreignKey)
    {
        if (AssertSettings.PRIORITY1_ASSERTIONS)
        {
            // Assert if name is null
            Assert.assertNotNull("name is null", name);
        } // end if

        // gets the database
        SQLiteDatabase db = getWritableDatabase();

        // get values
        ContentValues values = new ContentValues();

        // put the values into the corresponding location
        values.put(KEY_LIST_ITEM_NAME, name);
        values.put(KEY_LIST_ITEM_QUANTITY, quantity);
        values.put(KEY_LIST_ITEM_FK, foreignKey);

        // attempt to insert
        db.insertOrThrow(DATA_TABLE_ITEMS, null, values);
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
    private void deleteList(int listID)
    {
        // Delete all items with same listID
        String LIST_ITEMS_DELETE_QUERY = "DELETE FROM " + DATA_TABLE_ITEMS + " WHERE "
                + KEY_LIST_ITEM_FK + " = " + listID;

        // Delete list with listID
        String LISTS_DELETE_QUERY = "DELETE FROM " + DATA_TABLE_LIST + " WHERE "
                + KEY_LIST_ID + " = " + listID;

        // gets the database
        SQLiteDatabase db = getWritableDatabase();

        // attempt to get data
        try
        {
            db.execSQL(LIST_ITEMS_DELETE_QUERY);
            db.execSQL(LISTS_DELETE_QUERY);
        }
        catch (Exception e)
        {
            Log.d("deleteList", "Error while trying to delete list from database");
        }
        finally
        {
            // close connection
            db.close();
        } // end try/catch
    } // end deleteList

    // Delete list item associated with itemID
    private void deleteItem(int itemID)
    {
        // Delete all items with same itemID
        String LIST_ITEMS_DELETE_QUERY = "DELETE FROM " + DATA_TABLE_ITEMS + " WHERE "
                + KEY_LIST_ITEM_ID + " = " + itemID;

        // gets the database
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL(LIST_ITEMS_DELETE_QUERY);

        // close connection
        db.close();
    } // end deleteItem

    // Update list associated with listID
    private void updateList(int listID, String name)
    {
        if (AssertSettings.PRIORITY1_ASSERTIONS)
        {
            // Assert if name is null
            Assert.assertNotNull("name is null in update", name);
        } // end if

        // Update list with listID
        String LISTS_UPDATE_QUERY = "UPDATE " + DATA_TABLE_LIST + " SET " +
                KEY_LIST_NAME + "='" + name +
                "' WHERE " + KEY_LIST_ID + " = " + listID;

        // gets the database
        SQLiteDatabase db = getWritableDatabase();

        // attempt to get data
        try
        {
            db.execSQL(LISTS_UPDATE_QUERY);
        }
        catch (Exception e)
        {
            Log.d("updateList", "Error while trying to update list");
        }
        finally
        {
            // close connection
            db.close();
        } // end try/catch
    } // end updateList

    // Update list item associated with itemID
    // name is new name
    // quan is new quantity
    private void updateItem(int itemID, String name, int quan)
    {
        if (AssertSettings.PRIORITY1_ASSERTIONS)
        {
            // Assert if name is null
            Assert.assertNotNull("name is null in update", name);
        } // end if

        // Update item with same itemID
        String LIST_ITEMS_UPDATE_QUERY = "UPDATE " + DATA_TABLE_ITEMS + " SET " +
                KEY_LIST_ITEM_NAME + "='" + name + "', " + KEY_LIST_ITEM_QUANTITY + "=" + quan +
                " WHERE " + KEY_LIST_ITEM_ID + " = " + itemID;

        // gets the database
        SQLiteDatabase db = getWritableDatabase();

        // attempt to get data
        try
        {
            db.execSQL(LIST_ITEMS_UPDATE_QUERY);
        }
        catch (Exception e)
        {
            Log.d("updateItem", "Error while trying to update item");
        }
        finally
        {
            // close connection
            db.close();
        } // end try/catch
    } // end updateItem
} // end Data
