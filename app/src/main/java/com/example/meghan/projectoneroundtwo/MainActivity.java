package com.example.meghan.projectoneroundtwo;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    //TODO add check 100 character shortening, search button and camera aspects.

    private static final String TAG = "NOTES ACTIVITY";
    DatabaseManager dbManager;
    NotesCursorAdapter cursorListAdapter;

    ListView listView; //initializing listView
    Button addNotes_btn; //button to add notes
    String addNotes_text; //String to retain added notes till put in list

    Date date = new Date(); //Date variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DatabaseManager(this);

        addNotes_btn = (Button) findViewById(R.id.button_add_text); //connecting button
        final EditText addNotes_editText = (EditText) findViewById(R.id.notes_edittext); //setting up edittext for input options
        addNotes_text = addNotes_editText.getText().toString(); //getting the typed in text to edittext
        final ArrayList<String> myList = new ArrayList(); //Creating a list
        final ArrayList<String> displayList = new ArrayList(); //list to show 100 characaters or less
        listView = (ListView) findViewById(R.id.listview_notes); //List view and where to put the list

        Cursor cursor = dbManager.getAllNotes();
        cursorListAdapter = new NotesCursorAdapter(this, cursor, true);
        listView.setAdapter(cursorListAdapter);

        //final ArrayAdapter<String> arrayAdapter; //Array Adapter for list
        //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayList);

        addNotes_btn.setOnClickListener(new View.OnClickListener() { //When button activated should add notes and dates to list.
            @Override
            public void onClick(View v) {

                long timeNow = new Date().getTime(); //Getting time in long form
                Date date = new Date(); //making date variable
                date.setTime(timeNow); //Acquiring the exact time

                String littleNote = addNotes_editText.getText().toString(); //Getting written in text
                //myList.add(littleNote + " " + date); //adding it to list
                littleNote = littleNote + " " + date;

                /*
                if (littleNote.length() > 100) { //Will edit characteristics of string to 100
                    littleNote = littleNote.substring(0, 99); //Makes the large note smaller.
                    displayList.add(littleNote + "... " + date);
                } else {
                    displayList.add(littleNote + date);
                }
                */

                dbManager.addmmmNote(littleNote);
                cursorListAdapter.changeCursor(dbManager.getAllNotes());

                //listView.setAdapter(arrayAdapter); //Putting it in listView
                //arrayAdapter.notifyDataSetChanged(); //Changing view, showing on list.

                //String newItemText = addNotes_editText.getText().toString();
                //String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                //String combinedTextandDate = newItemText + " " + timeStamp;
            }
        });
    }

    @Override //Closes the Database when app is paused
    protected void onPause(){
        super.onPause();
        dbManager.close();
    }

    @Override //When app is restarted will reopen the database.
    protected void onResume(){
        super.onResume();
        dbManager = new DatabaseManager(this);
    }
}
