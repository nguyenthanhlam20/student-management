package vn.edu.fpt.fa24;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import vn.edu.fpt.fa24.Helpers.DatabaseHelper;
import vn.edu.fpt.fa24.Models.Student;

public class StudentDetailsActivity extends AppCompatActivity {

    private TextView nameTextView, genderTextView, dateTextView, emailTextView, addressTextView, majorTextView;
    private Button updateButton, deleteButton;

    private DatabaseHelper databaseHelper;
    private int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        // Initialize views
        nameTextView = findViewById(R.id.nameTextView);
        genderTextView = findViewById(R.id.genderTextView);
        dateTextView = findViewById(R.id.dateTextView);
        emailTextView = findViewById(R.id.emailTextView);
        addressTextView = findViewById(R.id.addressTextView);
        majorTextView = findViewById(R.id.majorTextView);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Initialize database helper
        databaseHelper = DatabaseHelper.getInstance(this);

        // Get studentId from Intent
        studentId = getIntent().getIntExtra("student_id", -1);

        // Load student details
        loadStudentDetails();

        // Set up button listeners
        updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDetailsActivity.this, UpdateStudentActivity.class);
            intent.putExtra("student_id", studentId);
            startActivity(intent);
            finish();
        });

        deleteButton.setOnClickListener(v -> confirmDeleteStudent());
    }

    private void loadStudentDetails() {
        Student student = databaseHelper.getStudentById(studentId);
        if (student != null) {
            nameTextView.setText("Name: " + student.getName());
            genderTextView.setText("Gender: " + student.getGender());
            dateTextView.setText("Date of Birth: " + student.getDate());
            emailTextView.setText("Email: " + student.getEmail());
            addressTextView.setText("Address: " + student.getName());
            majorTextView.setText("Major: " + student.getMajor());
        } else {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void confirmDeleteStudent() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete this student?")
                .setPositiveButton("Yes", (dialog, which) -> deleteStudent())
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteStudent() {
        int result = databaseHelper.deleteStudent(studentId);
        if (result > 0) {
            Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StudentDetailsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error deleting student", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh details when returning from the update activity
        loadStudentDetails();
    }
}
