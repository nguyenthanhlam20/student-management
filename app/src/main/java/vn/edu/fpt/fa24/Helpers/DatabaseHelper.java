package vn.edu.fpt.fa24.Helpers;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.fa24.Models.Major;
import vn.edu.fpt.fa24.Models.Student;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentManager.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper instance;

    // Student Table
    public static final String TABLE_STUDENT = "Student";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ADDRESS = "address";

    // Major Table
    public static final String TABLE_MAJOR = "Major";
    public static final String COLUMN_ID_MAJOR = "IDMajor";
    public static final String COLUMN_NAME_MAJOR = "nameMajor";
    public static final String COLUMN_MAJOR = "major";

    // Private constructor to prevent direct instantiation
    private DatabaseHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void insertSampleData(SQLiteDatabase db) {
        // Insert sample data into Major
        String[] majors = {
                "Computer Science", "Business", "Engineering",
                "Mathematics", "Psychology"
        };

        for (int i = 0; i < majors.length; i++) {
            ContentValues majorValues = new ContentValues();
            majorValues.put(COLUMN_ID_MAJOR, i + 1);
            majorValues.put(COLUMN_NAME_MAJOR, majors[i]);
            db.insert(TABLE_MAJOR, null, majorValues);
        }

        // Insert sample data into Student
        String[][] students = {
                {"Alice", "Female", "2000-01-01", "alice@example.com", "123 Main St.", "1"},
                {"Bob", "Male", "2000-01-02", "bob@example.com", "456 Elm St.", "2"},
                {"Charlie", "Male", "2000-01-03", "charlie@example.com", "789 Oak St.", "3"},
        };

        for (String[] student : students) {
            ContentValues studentValues = new ContentValues();
            studentValues.put(COLUMN_NAME, student[0]);
            studentValues.put(COLUMN_GENDER, student[1]);
            studentValues.put(COLUMN_DATE, student[2]);
            studentValues.put(COLUMN_EMAIL, student[3]);
            studentValues.put(COLUMN_ADDRESS, student[4]);
            studentValues.put(COLUMN_ID_MAJOR, Integer.parseInt(student[5]));
            db.insert(TABLE_STUDENT, null, studentValues);
        }
    }

    // Singleton instance
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Major Table
        db.execSQL("CREATE TABLE " + TABLE_MAJOR + " (" +
                COLUMN_ID_MAJOR + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_MAJOR + " TEXT)");

        // Create Student Table
        db.execSQL("CREATE TABLE " + TABLE_STUDENT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_EMAIL + " TEXT UNIQUE, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_ID_MAJOR + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_ID_MAJOR + ") REFERENCES " + TABLE_MAJOR + "(" + COLUMN_ID_MAJOR + "))");

        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAJOR);
        onCreate(db);
    }

    // Get all students
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to join Student and Major tables and sort by student name
        String query = "SELECT Student.ID, Student.name, Student.gender, Student.date, " +
                "Student.email, Student.Address, Student.idMajor, Major.nameMajor " +
                "FROM Student " +
                "JOIN Major ON Student.idMajor = Major.IDMajor " +
                "ORDER BY Student.name ASC";

        Cursor cursor = db.rawQuery(query, null);

        // Check if cursor is not null and has data
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Create a new Student object with data from the cursor
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                int majorId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_MAJOR));
                String major = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_MAJOR));

                // Create Student object and add to list
                Student student = new Student(id, name, gender, email, address, date,  majorId);
                student.setMajor(major);

                studentList.add(student);
            } while (cursor.moveToNext()); // Move to the next record
            cursor.close(); // Close cursor to free resources
        }

        return studentList; // Return the list of students
    }

    public int deleteStudent(int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STUDENT, "ID = ?", new String[]{String.valueOf(studentId)});
    }


    // Delete a major
    public int deleteMajor(int idMajor) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MAJOR, COLUMN_ID_MAJOR + " = ?", new String[]{String.valueOf(idMajor)});
    }

    // Get all majors
    public List<Major> getAllMajors() {
        List<Major> majorList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MAJOR, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_MAJOR));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_MAJOR));
                majorList.add(new Major(id, name));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return majorList;
    }

    public Student getStudentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT Student.ID, Student.name, Student.gender, Student.email, Student.address, " +
                "Student.date, Major.nameMajor AS major " +
                "FROM " + TABLE_STUDENT + " AS Student " +
                "JOIN " + TABLE_MAJOR + " AS Major " +
                "ON Student.idMajor = Major.IDMajor " +
                "WHERE Student.ID = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            Student student = new Student(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAJOR)) // Retrieved from Major table
            );
            cursor.close();
            return student;
        }
        return null;
    }

    public boolean updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_GENDER, student.getGender());
        values.put(COLUMN_EMAIL, student.getEmail());
        values.put(COLUMN_DATE, student.getDate());
        values.put(COLUMN_ADDRESS, student.getAddress());
        values.put(COLUMN_ID_MAJOR, student.getMajorId());

        int rows = db.update(TABLE_STUDENT, values, "ID = ?", new String[]{String.valueOf(student.getId())});
        return rows > 0;
    }

    public boolean addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_GENDER, student.getGender());
        values.put(COLUMN_EMAIL, student.getEmail());
        values.put(COLUMN_ADDRESS, student.getAddress());
        values.put(COLUMN_DATE, student.getDate());
        values.put(COLUMN_ID_MAJOR, student.getMajorId()); // Assuming you modified your Student class to include major ID

        long result = db.insert(TABLE_STUDENT, null, values);
        return result != -1; // Return true if insertion was successful
    }

    public boolean addMajor(Major major) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_MAJOR, major.getName());

        long result = db.insert(TABLE_MAJOR, null, values);
        return result != -1; // Return true if insertion was successful
    }

    public Major getMajorById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MAJOR, null, "IDMajor = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("nameMajor"));
            cursor.close();
            return new Major(id, name); // Assuming your Major class has a constructor that takes ID and name
        }
        return null;
    }

    public boolean updateMajor(Major major) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nameMajor", major.getName());

        int rowsAffected = db.update(TABLE_MAJOR, values, "IDMajor = ?", new String[]{String.valueOf(major.getId())});
        return rowsAffected > 0; // Return true if update was successful
    }
}

