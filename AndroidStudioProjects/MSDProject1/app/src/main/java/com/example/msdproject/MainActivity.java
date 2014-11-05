/*
*************** TO DO ****************
- look at rawQuery()
- look at passing in name rather than ROW_ID
- custom list layout (look at downloaded list zip file)
- Make sure input screens have error checking to ensure all text boxes are filled
- Reference all code
- look at try/catch to ensure cleanliness
- Use extra features, phone sensors, location/map, email. Location of venue (?)
- rename buttons from btn1 etc.
- ensure all strings are located in strings.xml
- global theme (look up)
- comment each java file + comment code
 */

package com.example.msdproject;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.RowId;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    public ArrayList<String> data_list=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.addConcertButton);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddArtist.class);
                startActivity(i);
            }
        });

        DBManager db =  new DBManager(this);

        /*
        try {
            db.reset();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        /*
        The list is showing the first attribute on the list (artist),
        When I added artist1 (ROW_ID = 1) nothing pops up on the view screen,
        when I added 0 (ROW_ID = 2) as the artist, all of the artist1 info pops up.

        Conclusion: When a list item is clicked, the previous ROW_ID Pops up.

        Solutions:  1. ROW_ID + 1/ -1 passed into view screen
                    2. Fix 0/1 being passed into view screen

        */

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
        db.insertConcert("Concert 1", "1", "1", "1");
        db.insertConcert("Concert 2", "2", "2", "2");
        db.insertConcert("Concert 3", "3", "3", "3");
        db.insertConcert("Concert 4", "4", "4", "4");
        */


        ListView listView = (ListView) findViewById(android.R.id.list);
        Cursor c = db.getAllConcerts();
        if (c.moveToFirst())
        {
            do {
                data_list.add(c.getString(0));
            } while (c.moveToNext());
        }
        ArrayAdapter<String> concertList=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, data_list);
        listView.setAdapter(concertList);
        concertList.notifyDataSetChanged();

        db.close();
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent i =  new Intent(MainActivity.this, ViewArtist.class);
        i.putExtra("id", id);
        startActivity(i);

    }

}