package vn.edu.fpt.fa24;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.fpt.fa24.Helpers.DatabaseHelper;
import vn.edu.fpt.fa24.Models.Major;

public class UpdateMajorActivity extends AppCompatActivity {

    private EditText editMajorName;
    private Button updateButton, cancelButton;
    private DatabaseHelper databaseHelper;
    private int majorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_major);

        // Initialize views
        editMajorName = findViewById(R.id.editMajorName);
        updateButton = findViewById(R.id.updateButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Initialize database helper
        databaseHelper = DatabaseHelper.getInstance(this);

        // Retrieve major ID from intent
        majorId = getIntent().getIntExtra("major_id", -1);
        loadMajorData(majorId);

        // Set onClickListener for update button
        updateButton.setOnClickListener(v -> updateMajor());

        // Set onClickListener for cancel button
        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateMajorActivity.this, MajorDetailsActivity.class);
            intent.putExtra("major_id", majorId); // Assuming getId() gives student ID
            startActivity(intent);
            finish();
        });
    }

    // Method to load major data from database
    private void loadMajorData(int id) {
        Major major = databaseHelper.getMajorById(id); // Assuming this method returns a Major object
        if (major != null) {
            editMajorName.setText(major.getName());
        } else {
            Toast.makeText(this, "Major not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Method to update major in database
    private void updateMajor() {
        String majorName = editMajorName.getText().toString().trim();

        if (majorName.isEmpty()) {
            Toast.makeText(this, "Please enter a major name", Toast.LENGTH_SHORT).show();
            return;
        }

        Major updatedMajor = new Major(majorId, majorName); // Assuming your Major class has a constructor for ID and name
        boolean isUpdated = databaseHelper.updateMajor(updatedMajor);
        if (isUpdated) {
            Toast.makeText(this, "Major updated successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateMajorActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Failed to update major", Toast.LENGTH_SHORT).show();
        }
    }
}
