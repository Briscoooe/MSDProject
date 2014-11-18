package com.example.msdproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class UpdateArtist extends Activity {

    DBManager db = new DBManager(this);

    public EditText nameTxt;
    public EditText venueTxt;
    public EditText dateTxt;
    public EditText commentsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_artist);

        Intent intent = getIntent();
        final long num = intent.getLongExtra("id", 1);

        try
        {
            db.open();
            Cursor c = db.getConcert(num);
            if (c.moveToFirst())
            {
                do
                {
                    String name = (c.getString(1));
                    String venue = (c.getString(2));
                    String date = (c.getString(3));
                    String comments = (c.getString(4));

                    TextView titleTxt = (TextView)findViewById(R.id.updateTitle);
                    nameTxt = (EditText)findViewById(R.id.updateName);
                    venueTxt = (EditText)findViewById(R.id.updateVenue);
                    dateTxt = (EditText)findViewById(R.id.updateDate);
                    commentsTxt = (EditText)findViewById(R.id.updateComments);


                    titleTxt.setText(name + " - Editor");
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
                            if (nameTxt.getText().toString().isEmpty()  || venueTxt.getText().toString().isEmpty() ||
                                    dateTxt.getText().toString().isEmpty() || commentsTxt.getText().toString().isEmpty() )
                            {
                                Toast.makeText(UpdateArtist.this, "Error! Please fill in all fields", Toast.LENGTH_LONG).show();
                            }
                            else
                            {

                                String updatedName = nameTxt.getText().toString();
                                String updatedVenue = venueTxt.getText().toString();
                                String updatedDate = dateTxt.getText().toString();
                                String updatedComments = commentsTxt.getText().toString();

                                UpdateArtistButton(updatedName,updatedVenue,updatedDate,updatedComments);
                            }
                        }
                    });

                } while (c.moveToNext());
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void UpdateArtistButton(String passedName, String passedVenue, String passedDate, String passedComments)
    {
        try
        {
            db.open();
            db.updateConcert(passedName,passedVenue, passedDate, passedComments);
            Toast.makeText(UpdateArtist.this, "Concert Updated!", Toast.LENGTH_LONG).show();
            super.finish();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        db.close();
    }
}
