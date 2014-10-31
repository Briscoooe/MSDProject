package com.example.msdproject;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.Context;
import android.widget.Toast;

import java.sql.SQLException;

public class MainActivity extends ListActivity {

    String[] items = {"Beyonce", "Jay Z", "Ed Sheeran", "Lady Gaga", "Mumford and Sons"};
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));

        addListenerOnButton();

        DBManager db =  new DBManager(this);

        try {
            db.open();
            long id = db.insertConcert("TESTING", "2/18/2012", "agasgasg", "sdgsdg");
            id = db.insertConcert("another test", "2/18/sdgs", "aaaaa", "bbbbb");
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            db.open();
            Cursor c = db.getAllConcerts();
            if (c.moveToFirst())
            {
                do
                {
                    DisplayRecord(c);
                }
                while (c.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    private void DisplayRecord(Cursor c)
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                "Name: " + c.getString(1) + "\n" +
                "Venue: " + c.getString(2),
                Toast.LENGTH_SHORT).show();
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