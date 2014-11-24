/*
* Brian Briscoe
* C12468098
* DT211/3
* MSD Project Semester 1
*
* MainActivity.java
*
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

        //Assigning the Spinner and sortBy array
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        sortBy = getResources().getStringArray(R.array.sortBy);
        //Initialising the spinnerAdapter variable and creating the spinner
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sortBy);
        spinner1.setAdapter(spinnerAdapter);

        //Setting up the spinner1 listener method in the onCreate so that the list can be
        //Dynamically modified when a spinner item is selected
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                spinnerInt = position;
                //These 2 line were taken from the internet as I couldn't get the Spinner to
                //Change color by only using the XML
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
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //Re-calling the addData class so that the list updates whenever the activity is returned to
        addData();
    }


    public void addData()
    {
        //This switch statements calls 1 of 4 possible getAllConcertsXXX methods in the
        //DBManager class based on which spinner item was selected
        switch (spinnerInt)
        {
            case 0:
                try
                {
                    //The simple cursor adapter here is assigning the 2 Database columns "name"
                    //and "venue" to the 2 textviews in the row_layout.xml file
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

            //Here the database is queried based on the id of the item clicked.
            //The resulting string is then converted to a long and passed to the next activity
            //where information will be displayed based on the row_id
            Cursor c = db.getConcert(id);
            String string_id = c.getString(0);
            long long_id = Long.parseLong(string_id);

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
