package com.umkc.cj.ar_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


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

    public Data(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    } // end constructor

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String DATA_TABLE_CREATE =
                "CREATE TABLE " + DATA_TABLE_LIST + " (" +
                        KEY_LIST_ID + " INTEGER PRIMARY KEY, " +
                        KEY_LIST_NAME + " TEXT);";

        String DATA_TABLE_CREATE_2 =
                "CREATE TABLE " + DATA_TABLE_ITEMS + " (" +
                        KEY_LIST_ITEM_ID + " INTEGER PRIMARY KEY, " +
                        KEY_LIST_ITEM_NAME + " TEXT, " +
                        KEY_LIST_ITEM_QUANTITY + " INTEGER, " +
                        KEY_LIST_ITEM_FK + " INTEGER REFERENCES "+ DATA_TABLE_LIST + ");";

        db.execSQL(DATA_TABLE_CREATE);
        db.execSQL(DATA_TABLE_CREATE_2);
    } // end onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(oldVersion != newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + DATA_TABLE_LIST);
            db.execSQL("DROP TABLE IF EXISTS " + DATA_TABLE_ITEMS);
            onCreate(db);
        } // end if
    } // end onUpgrade

    public void addList(String name)
    {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try
        {
            ContentValues values = new ContentValues();
            values.put(KEY_LIST_NAME, name);

            db.insertOrThrow(DATA_TABLE_LIST, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            Log.d("add", "Error trying to add list to database");
        }
        finally
        {
            db.endTransaction();
        } // end try/catch
    } // end addListItem

    // Get all posts in the database
    public List<ListData> getLists()
    {
        // Select all
        String LISTS_SELECT_QUERY = "SELECT * FROM " + DATA_TABLE_LIST;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(LISTS_SELECT_QUERY, null);
        List<ListData> data = new ArrayList<ListData>();

        try
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    ListData dataCall =
                            new ListData(cursor.getString(cursor.getColumnIndex(KEY_LIST_NAME)),
                                    cursor.getInt(cursor.getColumnIndex(KEY_LIST_ID)));
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
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            } // end if
            db.close();
        } // end try/catch
        return data;
    } // end getLists
} // end Data
