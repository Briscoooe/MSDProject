package com.example.msdproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;

import java.sql.SQLException;


public class DBManager {

    public static final String COL_ROWID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_VENUE = "venue";
    public static final String COL_DATE = "date";
    public static final String COL_COMMENTS = "comments";

    private static final String DB_NAME = "Concerts";
    private static final String DB_TABLE = "Concert_Info";
    private static final int DB_VERSION = 1;

    private static final String DB_CREATE =
            "create table Concert_Info (_id integer primary key autoincrement, name text not null, " +
            "venue text not null, date text not null, " +
            "comments text not null);";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBManager(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            /*
            //taken from YouTube Tutorial video
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("drop table if exists Concert_Info");
            onCreate(db);*/
        }
    }

    public DBManager open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBHelper.close();
    }

    public long insertConcert(String name, String venue, String date, String comments)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COL_NAME, name);
        initialValues.put(COL_VENUE, venue);
        initialValues.put(COL_DATE, date);
        initialValues.put(COL_COMMENTS, comments);
        return db.insert(DB_TABLE, null, initialValues);
    }

    public boolean deleteConcert(long ROW_ID)
    {
        return db.delete(DB_TABLE, COL_ROWID + "=" + ROW_ID, null) > 0;
    }

    public Cursor getAllConcerts() {
        return db.query(DB_TABLE, new String[]
                        {
                                COL_NAME,
                                COL_VENUE,
                                COL_DATE,
                                COL_COMMENTS
                        },
                        null,
                        null,
                        null,
                        null,
                        null
                );
    }

    public Cursor getConcert(long ROW_ID) throws SQLException
    {
        Cursor mCursor =
                db.query(DB_TABLE, new String[]
                        {
                                COL_ROWID,
                                COL_NAME,
                                COL_VENUE,
                                COL_DATE,
                                COL_COMMENTS
                        },
                        COL_ROWID + "=" + ROW_ID,
                        null,
                        null,
                        null,
                        null
                );
        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    public boolean updateConcert(long ROW_ID, String name, String venue, String date, String comments)
    {
        ContentValues args = new ContentValues();
        args.put(COL_NAME, name);
        args.put(COL_VENUE, venue);
        args.put(COL_DATE, date);
        args.put(COL_COMMENTS, comments);
        return db.update(DB_TABLE, args, COL_ROWID  + "=" + ROW_ID, null) > 0;

    }

}
