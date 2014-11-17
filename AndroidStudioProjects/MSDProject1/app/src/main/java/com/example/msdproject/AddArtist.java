package com.example.msdproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class AddArtist extends Activity {

    DBManager db = new DBManager(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_artist);

        Button add = (Button) findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    AddArtistButton();
                }

                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        });
	}

    public void AddArtistButton () throws SQLException
    {
        EditText nameTxt = (EditText)findViewById(R.id.editName);
        EditText venueTxt = (EditText)findViewById(R.id.editVenue);
        EditText dateTxt = (EditText)findViewById(R.id.editDate);
        EditText commentsTxt = (EditText)findViewById(R.id.editComments);

        if (nameTxt.getText().toString().isEmpty()  || venueTxt.getText().toString().isEmpty() ||
            dateTxt.getText().toString().isEmpty() || commentsTxt.getText().toString().isEmpty())
        {
            Toast.makeText(AddArtist.this, "Error! Please fill in all fields", Toast.LENGTH_LONG).show();
        }

        else
        {

            db.open();
            long id = db.insertConcert(nameTxt.getText().toString(),
                    venueTxt.getText().toString(),
                    dateTxt.getText().toString(),
                    commentsTxt.getText().toString());
            db.close();

            nameTxt.setText("");
            venueTxt.setText("");
            dateTxt.setText("");
            commentsTxt.setText("");

            Toast.makeText(AddArtist.this, "Concert Added", Toast.LENGTH_LONG).show();
            super.finish();
        }
    }
}
