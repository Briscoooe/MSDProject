/*
*************** TO DO ****************
*
* Plan for Friday:
* Click to view, pencil to delete
* make decent interface
* "are you sure?" dialog box
*
* Monday:
* multiple click items on list
*
* General:
* look at rawQuery()
- look at passing in name rather than ROW_ID
- Reference all code
- look at try/catch to ensure cleanliness
- Use extra features, phone sensors, location/map, email. Location of venue (?)
- examine all files in project (xml and java) to ensure no errors or warnings in sidebar
- global theme (look up)
- simple cursor adapter > 2 xml files needed, main_Activity + row.xml
- comment each java file + comment code
- ************position on list for onlistitemclick!************
- startactivityforresult()
- inputType for date entry
- use dp rather than px
 */

package com.example.msdproject;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View;
import java.sql.SQLException;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

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

        /*
        ImageButton edit = (ImageButton)findViewById(R.id.deleteIcon);
        edit.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, UpdateArtist.class);
                startActivity(i);
            }
        });*/


        /*
        try {
            db.reset();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        addData();
        //db.close();
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
                    android.R.layout.simple_list_item_1,
                    db.getAllConcerts(),
                    new String[] { "name" },
                    new int[] { android.R.id.text1 });

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
