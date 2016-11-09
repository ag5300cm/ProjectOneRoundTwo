package com.example.meghan.projectoneroundtwo;

import android.content.Context;
import android.database.Cursor;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;



public class NotesCursorAdapter extends CursorAdapter {
    // This adapter takes data from a database Cursor object* and uses that to
    //create and populate each ListView list element.

    //private static final String TAG = "NOTE CURSOR ADAPTER";

    private static final int ID_COL = 0;
    private static final int NOTES_COL = 1;

    public NotesCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_item_notes, parent, false);
        return v;
    }

    @Override //Receives the "public Cursor getAllNotes()" from DatabaseManager.java, that fetched all the records
    public void bindView(View view, Context context, final Cursor cursor) {

        final Button nameTV = (Button) view.findViewById(R.id.list_item_notes_text_view); //Was originally text_view before I changed it to buttons.

        final String shortenNote = (cursor.getString(NOTES_COL));
        final String longenNote = (cursor.getString(NOTES_COL));
        //Cursor is set to the correct database row, that corresponds to this row of the list.
        //Get data by reading the column needed.

        if (longenNote.length() > 100) { //Will edit characteristics of string to 100
            String newShortenNote = shortenNote.substring(0, 99)  ; //Makes the large note smaller.
            nameTV.setText(newShortenNote + "... " );
        } else {
            nameTV.setText(shortenNote);
        }

        //Because its a button, it is expecting to recieve click events.
        nameTV.setOnClickListener(new View.OnClickListener() { //If a longer note (100+) when clicked on should show the whole note.
            @Override
            public void onClick(View v) {
                nameTV.setText(longenNote);
            }
        });


        //Need this to update data - good idea to use a primary key
        final int notes_id = cursor.getInt(ID_COL);

    }
}
