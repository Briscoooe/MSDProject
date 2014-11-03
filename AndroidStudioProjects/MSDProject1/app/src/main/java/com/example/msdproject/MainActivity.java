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

        Button btn2 = (Button)findViewById(R.id.deleteConcertButton);
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DeleteArtist.class);
                startActivity(i);
            }
        });


        DBManager db =  new DBManager(this);

        try {
            db.open();
            /*
            db.insertConcert("Miley Cyrus", "The O2", "2/18/2012", "sdgsdg");
            db.insertConcert("Beyonce", "Marley Park","2/18/2012", "bbbbb");
            db.insertConcert("One Direction", "Croke Park","2/18/2012", "bbbbb");
            db.insertConcert("u2", "Slane Castle","2/18/2012", "bbbbb");
            */
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ArrayList<String> data_list=new ArrayList<String>();
        ListView listView = (ListView) findViewById(android.R.id.list);
        Cursor c = db.getAllConcerts();
        if (c.moveToFirst())
        {
            do {
                data_list.add(c.getString(0));
            } while (c.moveToNext());
        }
        ArrayAdapter<String> aa=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, data_list);
        listView.setAdapter(aa);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent i = new Intent(MainActivity.this,UpdateArtist.class);
        startActivity(i);
    }



}