package com.example.msdproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class AddArtist extends Activity {

    DBManager db = new DBManager(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_artist);
	}

    public void AddArtist (View v) throws SQLException
    {
        EditText nameTxt = (EditText)findViewById(R.id.editName);
        EditText venueTxt = (EditText)findViewById(R.id.editVenue);
        EditText dateTxt = (EditText)findViewById(R.id.editDate);
        EditText commentsTxt = (EditText)findViewById(R.id.editComments);

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
