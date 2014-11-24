/*
*************** TO DO ****************
* Use extra features, phone sensors, location/map, email. Location of venue (?)
* comment each java file + comment code
* are you sure dialog box
* rename artist to concert in files
* search for music by this artist
 */

package com.example.msdproject;

import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View;
import java.sql.SQLException;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ListActivity {

    DBManager db =  new DBManager(this);
    String [] sortBy;
    Spinner spinner1;
    int spinnerInt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        sortBy = getResources().getStringArray(R.array.sortBy);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sortBy);
        spinner1.setAdapter(spinnerAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                spinnerInt = position;
                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView)parent.getChildAt(0)).setTextSize(18);
                addData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                //Do nothing
            }
        });

        ImageButton add = (ImageButton)findViewById(R.id.addConcertButton);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, AddArtist.class);
                startActivity(i);
            }
        });

        //addData();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        addData();
    }


    public void addData()
    {

        Log.d("test", "Spinner ID = " + spinnerInt);
        switch (spinnerInt)
        {
            case 0:
                try
                {
                    db.open();
                    SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                            R.layout.row_layout,
                            db.getAllConcertsNameASC(),
                            new String[] { "name", "venue" },
                            new int[] { R.id.listName, R.id.listDate });

                    ListView listView = (ListView) findViewById(android.R.id.list);
                    listView.setAdapter(adapter);

                }

                catch (SQLException e)
                {
                    e.printStackTrace();
                }

                db.close();
                break;

            case 1:
                try
                {
                    db.open();
                    SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                            R.layout.row_layout,
                            db.getAllConcertsNameDESC(),
                            new String[] { "name", "venue" },
                            new int[] { R.id.listName, R.id.listDate });

                    ListView listView = (ListView) findViewById(android.R.id.list);
                    listView.setAdapter(adapter);
                }

                catch (SQLException e)
                {
                    e.printStackTrace();
                }

                db.close();
                break;

            case 2:
                try
                {
                    db.open();
                    SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                            R.layout.row_layout,
                            db.getAllConcertsVenueASC(),
                            new String[] { "name", "venue" },
                            new int[] { R.id.listName, R.id.listDate });

                    ListView listView = (ListView) findViewById(android.R.id.list);
                    listView.setAdapter(adapter);
                }

                catch (SQLException e)
                {
                    e.printStackTrace();
                }

                db.close();
                break;

            case 3:
                try
                {
                    db.open();
                    SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                            R.layout.row_layout,
                            db.getAllConcertsVenueDESC(),
                            new String[] { "name", "venue" },
                            new int[] { R.id.listName, R.id.listDate });

                    ListView listView = (ListView) findViewById(android.R.id.list);
                    listView.setAdapter(adapter);
                }

                catch (SQLException e)
                {
                    e.printStackTrace();
                }

                db.close();
                break;
        }
    }

    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        try
        {
            db.open();

            Cursor c = db.getConcert(id);
            String string_id = c.getString(0);
            long long_id = Long.parseLong(string_id);
            Log.d("test", "id passed = " + long_id);

            Intent i =  new Intent(MainActivity.this, ViewArtist.class);
            i.putExtra("id", long_id);
            startActivity(i);
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
        db.close();
    }
}
