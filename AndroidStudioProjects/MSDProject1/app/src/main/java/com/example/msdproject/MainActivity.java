/*
*************** TO DO ****************
- look at rawQuery()
- look at passing in name rather than ROW_ID
- Make sure input screens have error checking to ensure all text boxes are filled
- Reference all code
- look at try/catch to ensure cleanliness
- Use extra features, phone sensors, location/map, email. Location of venue (?)
- possibly have pencil icon for editing artist
- ensure all strings are located in strings.xml
- global theme (look up)
- comment each java file + comment code
 */

package com.example.msdproject;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ListView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.view.View;
import java.sql.SQLException;
import java.util.ArrayList;


public class MainActivity extends ListActivity {

    public ArrayList<String> data_list=new ArrayList<String>();
    DBManager db =  new DBManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add = (Button)findViewById(R.id.addConcertButton);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddArtist.class);
                startActivity(i);
            }
        });

        /*
        try {
            db.reset();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ListView listView = (ListView) findViewById(android.R.id.list);
        Cursor c = db.getAllConcerts();
        if (c.moveToFirst())
        {
            do {
                data_list.add(c.getString(0));
            } while (c.moveToNext());
        }
        //ArrayAdapter<String> concertList=new ArrayAdapter<String>(getApplicationContext(),
                                        //R.layout.row_layout,R.id.label, data_list);
        ArrayAdapter<String> concertList=new ArrayAdapter<String>(getApplicationContext(),
                                            android.R.layout.simple_list_item_1, data_list);
        listView.setAdapter(concertList);

        //db.close();
    }


    public void onResume()
    {
        super.onResume();
        try {
            db.open();
            data_list = db.getAllConcerts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        concertList.notifyDataSetChanged();
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent i =  new Intent(MainActivity.this, ViewArtist.class);
        //ListView listView = (ListView) findViewById(android.R.id.list);
        //listView.setAdapter(null);
        i.putExtra("id", id);
        startActivity(i);
    }

}