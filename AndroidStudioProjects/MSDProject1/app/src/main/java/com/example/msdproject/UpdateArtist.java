package com.example.msdproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Calendar;

public class UpdateArtist extends Activity {

    DBManager db = new DBManager(this);

    //Some of the code involving the dialog and calendar box was taken from a youtube tutorial
    static final int dialogID = 1;
    int y, m, d;

    public EditText nameTxt;
    public EditText venueTxt;
    public TextView dateTxt;
    public EditText commentsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_artist);

        Calendar today = Calendar.getInstance();
        y = today.get(Calendar.YEAR);
        m = today.get(Calendar.MONTH);
        d = today.get(Calendar.DAY_OF_MONTH);

        Button setDate = (Button)findViewById(R.id.setDateButton);

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogID);
            }
        });

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
                    dateTxt = (TextView)findViewById(R.id.editDate);
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

    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case dialogID:
                return new DatePickerDialog(this, mDateSetListener, y, m, d);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day)
        {
            y = year;
            m = month;
            d = day;

            TextView dateTxt = (TextView)findViewById(R.id.editDate);
            dateTxt.setText(d + "/" + m + "/" + y);
        }
    };

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
