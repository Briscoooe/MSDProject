package com.example.msdproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Calendar;

public class AddArtist extends Activity {

    DBManager db = new DBManager(this);
    static final int dialogID = 1;
    int y, m, d;

	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_artist);

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

    public void AddArtistButton () throws SQLException
    {
        EditText nameTxt = (EditText)findViewById(R.id.editName);
        EditText venueTxt = (EditText)findViewById(R.id.editVenue);
        TextView dateTxt = (TextView)findViewById(R.id.editDate);
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
