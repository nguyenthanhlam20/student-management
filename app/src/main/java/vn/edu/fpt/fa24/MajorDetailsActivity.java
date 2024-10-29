package vn.edu.fpt.fa24;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.fpt.fa24.Helpers.DatabaseHelper;
import vn.edu.fpt.fa24.Models.Major;

public class MajorDetailsActivity extends AppCompatActivity {

    private TextView textViewMajorName;
    private Button buttonUpdate, buttonDelete;
    private DatabaseHelper databaseHelper;
    private int majorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major_details);

        // Initialize views
        textViewMajorName = findViewById(R.id.textViewMajorName);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);

        // Initialize database helper
        databaseHelper = DatabaseHelper.getInstance(this);

        // Retrieve major ID from intent
        majorId = getIntent().getIntExtra("major_id", -1);
        loadMajorData(majorId);

        // Set onClickListener for the update button
        buttonUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(MajorDetailsActivity.this, UpdateMajorActivity.class);
            intent.putExtra("major_id", majorId);
            startActivity(intent);
            finish();
        });

        // Set onClickListener for the delete button
        buttonDelete.setOnClickListener(v -> showDeleteConfirmationDialog(majorId));
    }

    // Method to load major data from the database
    private void loadMajorData(int id) {
        Major major = databaseHelper.getMajorById(id);
        if (major != null) {
            textViewMajorName.setText(major.getName());
        } else {
            Toast.makeText(this, "Major not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Method to show confirmation dialog for deleting a major
    private void showDeleteConfirmationDialog(final int majorId) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Delete Major")
                .setMessage("Are you sure you want to delete this major?")
                .setPositiveButton("Delete", (dialog, which) -> deleteMajor(majorId))
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Method to delete the major
    private void deleteMajor(int majorId) {
        int isDeleted = databaseHelper.deleteMajor(majorId);
        if (isDeleted > 0) {
            Toast.makeText(this, "Major deleted successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MajorDetailsActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the activity
        } else {
            Toast.makeText(this, "Failed to delete major", Toast.LENGTH_SHORT).show();
        }
    }
}
