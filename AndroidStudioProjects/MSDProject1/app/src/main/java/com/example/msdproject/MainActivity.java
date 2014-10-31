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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    ///String[] items = {"Beyonce", "Jay Z", "Ed Sheeran", "Lady Gaga", "Mumford and Sons"};
    Button btn;

    private static ArrayList<String> Concert_List = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));

        addListenerOnButton();

        DBManager db =  new DBManager(this);

        try {
            db.open();
            db.insertConcert("Miley Cyrus", "The O2", "2/18/2012", "sdgsdg");
            db.insertConcert("Beyonce", "Marley Park","2/18/2012", "bbbbb");
            db.insertConcert("Beyonce", "Marley Park","2/18/2012", "bbbbb");
            db.insertConcert("Beyonce", "Marley Park","2/18/2012", "bbbbb");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
        db.getAllConcerts();

        ArrayList<String> Concert_List = (ArrayList<String>) db.getAllConcerts();

        ListView listView = (ListView) findViewById(android.R.id.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Concert_List);

        getListView().setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id)
            {
                Toast.makeText(getApplicationContext(),
                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });
        */

    }


    public void addListenerOnButton() {

        final Context context = this;

        btn = (Button) findViewById(R.id.addConcertButton);

        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, AddArtist.class);
                startActivity(intent);

            }

        });

    }
}