package com.example.msdproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;

public class UpdateArtist extends Activity {

    DBManager db = new DBManager(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_artist);

        Intent intent = getIntent();
        final long num = intent.getLongExtra("id", 1);

        String name;
        String venue;
        String date;
        String comments;

        try {
            db.open();
            Cursor c = db.getConcert(num + 1);
            if (c.moveToFirst())
            {
                do
                {
                    name = (c.getString(1));
                    venue = (c.getString(2));
                    date = (c.getString(3));
                    comments = (c.getString(4));

                    TextView titleTxt = (TextView)findViewById(R.id.updateTitle);
                    final EditText nameTxt = (EditText)findViewById(R.id.updateName);
                    final EditText venueTxt = (EditText)findViewById(R.id.updateVenue);
                    final EditText dateTxt = (EditText)findViewById(R.id.updateDate);
                    final EditText commentsTxt = (EditText)findViewById(R.id.updateComments);

                    
                    titleTxt.setText("Update " + name);
                    nameTxt.setText(name);
                    venueTxt.setText(venue);
                    dateTxt.setText(date);
                    commentsTxt.setText(comments);

                    Button update = (Button)findViewById(R.id.updateConcertButton);
                    update.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            db.updateConcert(nameTxt.getText().toString(),
                                            venueTxt.getText().toString(),
                                            dateTxt.getText().toString(),
                                            commentsTxt.getText().toString());
                            db.close();
                        }
                    });

                    /*
                    EditText nameTxt = (EditText)findViewById(R.id.editName);
                    EditText venueTxt = (EditText)findViewById(R.id.editVenue);
                    EditText dateTxt = (EditText)findViewById(R.id.editDate);
                    EditText commentsTxt = (EditText)findViewById(R.id.editComments);

                    db.open();
                    long id = db.insertConcert(nameTxt.getText().toString(),
                            venueTxt.getText().toString(),
                            dateTxt.getText().toString(),
                            commentsTxt.getText().toString());
                    db.close();*/

                } while (c.moveToNext());
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
	}
}
