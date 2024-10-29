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

public class UpdateStudentActivity extends AppCompatActivity {

    private EditText editName, editGender, editEmail, editAddress, editDate;
    private Spinner spinnerMajor;
    private Button updateButton, cancelButton;
    private DatabaseHelper databaseHelper;
    private int studentId;
    private int selectedMajorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        // Initialize views
        editName = findViewById(R.id.editName);
        editGender = findViewById(R.id.editGender);
        editEmail = findViewById(R.id.editEmail);
        editAddress = findViewById(R.id.editAddress);
        editDate = findViewById(R.id.editDate);
        spinnerMajor = findViewById(R.id.spinnerMajor);
        updateButton = findViewById(R.id.updateButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Initialize database helper
        databaseHelper = DatabaseHelper.getInstance(this);

        // Retrieve student ID from intent
        studentId = getIntent().getIntExtra("student_id", -1);
        loadStudentData(studentId);
        loadMajors(); // Load majors into the spinner

        // Set onClickListener for update button
        updateButton.setOnClickListener(v -> updateStudent());

        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateStudentActivity.this, StudentDetailsActivity.class);
            intent.putExtra("student_id", studentId); // Assuming getId() gives student ID

            startActivity(intent);
            finish();
        });
    }

    // Method to load student data from database
    private void loadStudentData(int id) {
        Student student = databaseHelper.getStudentById(id);
        if (student != null) {
            editName.setText(student.getName());
            editGender.setText(student.getGender());
            editEmail.setText(student.getEmail());
            editAddress.setText(student.getAddress());
            editDate.setText(student.getDate());

            // Set the selected major in the spinner
            selectedMajorId = student.getMajorId(); // Assuming you have a method to get major ID
            spinnerMajor.setSelection(selectedMajorId-1);
        } else {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
            finish();
        }
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

    // Method to update student in database
    private void updateStudent() {
        String name = editName.getText().toString().trim();
        String gender = editGender.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String address = editAddress.getText().toString().trim();
        String date = editDate.getText().toString().trim();

        // Create an updated Student object
        Student updatedStudent = new Student(studentId, name, gender, email, address, date, selectedMajorId);

        // Update in the database
        boolean isUpdated = databaseHelper.updateStudent(updatedStudent);
        if (isUpdated) {
            Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateStudentActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the activity
        } else {
            Toast.makeText(this, "Failed to update student", Toast.LENGTH_SHORT).show();
        }
    }
}
