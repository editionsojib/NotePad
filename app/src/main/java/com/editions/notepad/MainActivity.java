package com.editions.notepad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    NoteDBHelper noteDBHelper;
    NoteAdapter noteAdapter;
    ArrayList<NoteModel> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recyclerView);

        //Remove Title Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialize NoteDBHelper
        noteDBHelper = new NoteDBHelper(this);



        //Recycler View set LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        arrayList.clear();
        //Get data from NoteHelper and set in to NoteAdapter
        Cursor cursor = noteDBHelper.SelectData();
        while (cursor.moveToNext()){
            arrayList.add(new NoteModel(cursor.getString(1), cursor.getString(2),
                    cursor.getInt(0)));
        }



        noteAdapter = new NoteAdapter(this, arrayList, noteDBHelper);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.scrollToPosition(arrayList.size());




        //floatingActionButton set OnClick listener
        floatingActionButton.setOnClickListener(v-> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });
    }//END


    //onResume()
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();

        // Clear existing data
        arrayList.clear();

        // Re-fetch and update list
        Cursor cursor = noteDBHelper.SelectData();
        while (cursor.moveToNext()) {
            arrayList.add(new NoteModel(cursor.getString(1), cursor.getString(2), cursor.getInt(0)));
        }

        // Notify adapter
        noteAdapter.notifyDataSetChanged();

    }

}