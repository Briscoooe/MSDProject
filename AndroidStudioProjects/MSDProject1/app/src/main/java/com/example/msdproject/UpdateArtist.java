/*
* Brian Briscoe
* C12468098
* DT211/3
* MSD Project Semester 1
*
* UpdateArtist.java
*
*/

package com.example.msdproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.SQLException;

public class UpdateArtist extends Activity {

    DBManager db = new DBManager(this);

    static final int dialogID = 1;
    int y, m, d;

    public EditText nameTxt;
    public AutoCompleteTextView venueTxt;
    public TextView dateTxt;
    public EditText commentsTxt;

    MediaPlayer guitarSound;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_artist);

        guitarSound = MediaPlayer.create(this, R.raw.guitar);

        Button setDate = (Button)findViewById(R.id.setDateButton);

        setDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                showDialog(dialogID);
            }
        });

        //These following lines query the database for all of the venues in the database and
        //and fill an array with these values to be used in an AutoComplete text view

        venueTxt = (AutoCompleteTextView)findViewById(R.id.updateVenue);

        try
        {
            db.open();
            String[] venues = db.getAllVenues();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, venues);
            venueTxt.setAdapter(adapter);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        db.close();

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
                    //As with the ViewArtist class, this method queries the database using the "num"
                    //value and assigns the information to the EditText boxes
                    String name = (c.getString(1));
                    String venue = (c.getString(2));
                    String date = (c.getString(3));
                    String comments = (c.getString(4));

                    TextView titleTxt = (TextView)findViewById(R.id.updateTitle);
                    nameTxt = (EditText)findViewById(R.id.updateName);
                    venueTxt = (AutoCompleteTextView)findViewById(R.id.updateVenue);
                    dateTxt = (TextView)findViewById(R.id.editDate);
                    commentsTxt = (EditText)findViewById(R.id.updateComments);


                    //Here the date string (in the format DD/MM/YYYY) is being split in to 3 different
                    //integer values by the '/'. The month integer value is then passed into the getMonth
                    //method which returns the string value of that month ie. 1 = January
                    String[] splitDate = date.split("/");
                    String strDay = splitDate[0];
                    String strMonth = splitDate[1];
                    String strYear = splitDate[2];

                    d = Integer.parseInt(strDay);
                    m = Integer.parseInt(strMonth);
                    y = Integer.parseInt(strYear);

                    titleTxt.setText(name + " - Editor");
                    nameTxt.setText(name);
                    venueTxt.setText(venue);
                    dateTxt.setText(d + "/" + (m + 1) + "/" + y);
                    commentsTxt.setText(comments);

                    Button update = (Button)findViewById(R.id.updateConcertButton);
                    update.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            //This if-statement ensures all text fields are not empty before proceeding
                            if (nameTxt.getText().toString().isEmpty()  || venueTxt.getText().toString().isEmpty() ||
                                    dateTxt.getText().toString().isEmpty() || commentsTxt.getText().toString().isEmpty() )
                            {
                                Toast.makeText(UpdateArtist.this, "Please fill in all fields!", Toast.LENGTH_LONG).show();
                            }
                            else
                            {

                                String updatedName = nameTxt.getText().toString();
                                String updatedVenue = venueTxt.getText().toString();
                                String dateMinusOne = (d + "/" + (m - 1) + "/" +y);
                                String updatedComments = commentsTxt.getText().toString();

                                UpdateArtistButton(updatedName,updatedVenue,dateMinusOne,updatedComments);
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

    //Parts of the following 2 methods were taken from a YouTube tutorial

    //This method creates a calendar pop up on the screen for selecting the date
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case dialogID:
                return new DatePickerDialog(this, mDateSetListener, y, m, d);
        }
        return null;
    }

    //This method assigns the date set in the previous method to the TextView dateTxt
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day)
        {
            y = year;
            m = month;
            d = day;

            TextView dateTxt = (TextView)findViewById(R.id.editDate);
            dateTxt.setText(d + "/" + (m + 1) + "/" + y);
        }
    };

    public void UpdateArtistButton(String passedName, String passedVenue, String passedDate, String passedComments)
    {
        try
        {
            db.open();
            db.updateConcert(passedName,passedVenue, passedDate, passedComments);
            Toast.makeText(UpdateArtist.this, "Concert Updated!", Toast.LENGTH_LONG).show();
            guitarSound.start();
            super.finish();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        db.close();
    }
}
