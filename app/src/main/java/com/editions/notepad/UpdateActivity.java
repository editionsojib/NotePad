package com.editions.notepad;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateActivity extends AppCompatActivity {

    EditText edTitle, edMessage;
    TextView textID;
    Button updateNote_btn;
    NoteDBHelper noteDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edTitle = findViewById(R.id.edTitle);
        edMessage = findViewById(R.id.edMessage);
        updateNote_btn = findViewById(R.id.updateNote_btn);
        textID = findViewById(R.id.textID);


        noteDBHelper = new NoteDBHelper(this);



        //Get Data From Intent Extra
        String e_id = getIntent().getStringExtra("id");
        String e_title = getIntent().getStringExtra("title");
        String e_message = getIntent().getStringExtra("message");
        textID.setText(e_id);
        edTitle.setText(e_title);
        edMessage.setText(e_message);



        //Update Notes From Button Click
        updateNote_btn.setOnClickListener(v -> {
            String id = textID.getText().toString();
            String title = edTitle.getText().toString();
            String message = edMessage.getText().toString();
            if (title.isEmpty() || message.isEmpty()) {
                edTitle.setError("Title Required");
                edMessage.setError("Message Required");
            } else {
                boolean result = noteDBHelper.updateData(id, title, message);
                if (result) {
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

        });







    }
}