package vn.edu.fpt.fa24;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.fpt.fa24.Helpers.DatabaseHelper;
import vn.edu.fpt.fa24.Models.Major;

public class AddMajorActivity extends AppCompatActivity {

    private EditText editMajorName;
    private Button addButton, cancelButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_major);

        // Initialize views
        editMajorName = findViewById(R.id.editMajorName);
        addButton = findViewById(R.id.addButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Initialize database helper
        databaseHelper = DatabaseHelper.getInstance(this);

        // Set onClickListener for add button
        addButton.setOnClickListener(v -> addMajor());

        // Set onClickListener for cancel button
        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddMajorActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Method to add a new major to the database
    private void addMajor() {
        String majorName = editMajorName.getText().toString().trim();

        if (majorName.isEmpty()) {
            Toast.makeText(this, "Please enter a major name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Major object
        Major newMajor = new Major(majorName);

        // Add to the database
        boolean isInserted = databaseHelper.addMajor(newMajor);
        if (isInserted) {
            Toast.makeText(this, "Major added successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddMajorActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Failed to add major", Toast.LENGTH_SHORT).show();
        }
    }
}
