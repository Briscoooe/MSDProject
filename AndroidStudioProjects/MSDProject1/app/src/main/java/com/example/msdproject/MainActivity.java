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
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View;
import java.sql.SQLException;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {

    DBManager db =  new DBManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        addData();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        addData();
    }

    public void addData()
    {

        try
        {
            db.open();
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                                          android.R.layout.simple_list_item_2,
                                          db.getAllConcerts(),
                                          new String[] { "name", "date" },
                                          new int[] { android.R.id.text1, android.R.id.text2 });

            //TextView tv = (TextView)findViewById(android.R.id.text1);
            //tv.setTextColor(Color.BLACK);

            ListView listView = (ListView) findViewById(android.R.id.list);
            listView.setAdapter(adapter);
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        db.close();
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
