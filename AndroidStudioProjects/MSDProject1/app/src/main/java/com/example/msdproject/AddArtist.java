/*
* Brian Briscoe
* C12468098
* DT211/3
* MSD Project Semester 1
*
* AddArtist.ava
*
*/

package com.example.msdproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddArtist extends Activity {

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
        setContentView(R.layout.activity_add_artist);

        //This sets the default day on the calendar pop up to the current day
        Calendar today = Calendar.getInstance();
        y = today.get(Calendar.YEAR);
        m = today.get(Calendar.MONTH);
        d = today.get(Calendar.DAY_OF_MONTH);

        //These following lines query the database for all of the venues in the database and
        //and fill an array with these values to be used in an AutoComplete text view
        venueTxt = (AutoCompleteTextView)findViewById(R.id.editVenue);

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

        guitarSound = MediaPlayer.create(this, R.raw.guitar);

        Button setDate = (Button)findViewById(R.id.setDateButton);

        setDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                showDialog(dialogID);
            }
        });

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

    public void AddArtistButton () throws SQLException
    {

        nameTxt = (EditText)findViewById(R.id.editName);
        venueTxt = (AutoCompleteTextView)findViewById(R.id.editVenue);
        dateTxt = (TextView)findViewById(R.id.editDate);
        commentsTxt = (EditText)findViewById(R.id.editComments);

        //This if-statement ensures that all text boxes are filled before proceeding
        if (nameTxt.getText().toString().isEmpty()  || venueTxt.getText().toString().isEmpty() ||
            dateTxt.getText().toString().isEmpty() || commentsTxt.getText().toString().isEmpty())
        {
            Toast.makeText(AddArtist.this, "Please fill in all fields!", Toast.LENGTH_LONG).show();
        }

        else
        {

            db.open();

            String date = dateTxt.getText().toString();
            String[] splitDate = date.split("/");
            String strDay = splitDate[0];
            String strMonth = splitDate[1];
            String strYear = splitDate[2];

            d = Integer.parseInt(strDay);
            m = Integer.parseInt(strMonth);
            y = Integer.parseInt(strYear);

            //I was having a lot of trouble getting the same date to show up in the calendar view, the textview
            //and the database so this is the solution  I came up with
            String dateMinusOne = (d + "/" + (m - 1) + "/" +y);

            long id = db.insertConcert(nameTxt.getText().toString(),
                    venueTxt.getText().toString(),
                    dateMinusOne,
                    commentsTxt.getText().toString());
            db.close();

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int option)
                {
                    switch (option)
                    {
                        //This section of code is the "Yes" answer to a dialog box.
                        //The question is "Would you like to add this concert to the calendar?"
                        //and if yes is selected, the values of the EditText boxes in this activity
                        //are passed into the new activity which opens the default calendar app and creates
                        //a new event. This new event then contains all of the information that
                        //was typed in to this activity as a result of the putExtra statements
                        case DialogInterface.BUTTON_POSITIVE:
                            Intent intent = new Intent(Intent.ACTION_EDIT);
                            intent.setType("vnd.android.cursor.item/event");

                            GregorianCalendar cal = new GregorianCalendar(y, (m - 1), d);
                            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                    cal.getTimeInMillis());
                            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY,
                                    false);
                            intent.putExtra(CalendarContract.Events.TITLE,
                                    nameTxt.getText().toString() + " Concert");
                            intent.putExtra(CalendarContract.Events.EVENT_LOCATION,
                                    venueTxt.getText().toString());
                            intent.putExtra(CalendarContract.Events.DESCRIPTION,
                                    commentsTxt.getText().toString());

                            guitarSound.start();

                            startActivity(intent);
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            guitarSound.start();
                            finish();
                            break;
                    }
                }
            };

            AlertDialog.Builder popUp = new AlertDialog.Builder(this);
            popUp.setMessage("Would you like to add this concert to your calendar?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }
    }
}
