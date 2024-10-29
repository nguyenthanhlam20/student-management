package vn.edu.fpt.fa24;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import vn.edu.fpt.fa24.Helpers.DatabaseHelper;
import vn.edu.fpt.fa24.Models.Major;
import vn.edu.fpt.fa24.Models.Student;

public class AddStudentActivity extends AppCompatActivity {

    private EditText editName, editGender, editEmail, editAddress, editDate;
    private Spinner spinnerMajor;
    private Button addButton, cancelButton;
    private DatabaseHelper databaseHelper;
    private int selectedMajorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        // Initialize views
        editName = findViewById(R.id.editName);
        editGender = findViewById(R.id.editGender);
        editEmail = findViewById(R.id.editEmail);
        editAddress = findViewById(R.id.editAddress);
        editDate = findViewById(R.id.editDate);
        spinnerMajor = findViewById(R.id.spinnerMajor);
        addButton = findViewById(R.id.addButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Initialize database helper
        databaseHelper = DatabaseHelper.getInstance(this);

        // Load majors into the spinner
        loadMajors();

        // Set onClickListener for add button
        addButton.setOnClickListener(v -> addStudent());

        // Set onClickListener for cancel button
        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddStudentActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Method to load majors into the spinner
    private void loadMajors() {
        List<Major> majors = databaseHelper.getAllMajors(); // Assuming this method returns a list of Major objects
        ArrayAdapter<Major> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, majors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMajor.setAdapter(adapter);

        // Set an item selected listener
        spinnerMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Major selectedMajor = (Major) parent.getItemAtPosition(position);
                selectedMajorId = selectedMajor.getId(); // Get the selected major ID
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedMajorId = -1; // Default value if no selection
            }
        });
    }

    // Method to add a new student to the database
    private void addStudent() {
        String name = editName.getText().toString().trim();
        String gender = editGender.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String address = editAddress.getText().toString().trim();
        String date = editDate.getText().toString().trim();

        // Validate input fields
        if (name.isEmpty() || gender.isEmpty() || email.isEmpty() || address.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Student object
        Student newStudent = new Student(name, gender, email, address, date, selectedMajorId);

        // Add to the database
        boolean isInserted = databaseHelper.addStudent(newStudent); // Assuming this method inserts a student and returns a boolean
        if (isInserted) {
            Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddStudentActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Failed to add student", Toast.LENGTH_SHORT).show();
        }
    }
}
