package com.editions.notepad;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    Button addNote_btn;
    EditText edTitle, edMessage;
    NoteDBHelper noteDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addNote_btn = findViewById(R.id.updateNote_btn);
        edTitle = findViewById(R.id.edTitle);
        edMessage = findViewById(R.id.edMessage);

        noteDBHelper = new NoteDBHelper(this);



        //addNote_btn set OnClick listener
        addNote_btn.setOnClickListener(v-> {
            String title = edTitle.getText().toString();
            String message = edMessage.getText().toString();

            //Insert Data in to Database using NoteDBHelper and check result
            long result = noteDBHelper.InsertData(title, message);
            if (result == -1) {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            }
            edTitle.setText("");
            edMessage.setText("");
            //Go to MainActivity
            finish();

        });
    }//END

}