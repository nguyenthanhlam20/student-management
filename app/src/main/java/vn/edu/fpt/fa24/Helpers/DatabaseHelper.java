package vn.edu.fpt.fa24.Helpers;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentManagementApp.db";
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

    // Private constructor to prevent direct instantiation
    private DatabaseHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void insertSampleData(SQLiteDatabase db) {
        // Insert sample data into Major
        String[] majors = {
                "Computer Science", "Business", "Engineering",
                "Mathematics", "Psychology", "Biology",
                "Physics", "History", "Art", "Chemistry"
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
                {"David", "Male", "2000-01-04", "david@example.com", "101 Pine St.", "4"}
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

    // CRUD Operations for Student Table

    // Insert a new student
    public long insertStudent(String name, String gender, String date, String email, String address, int idMajor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_GENDER, gender);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_ID_MAJOR, idMajor);
        return db.insert(TABLE_STUDENT, null, contentValues);
    }

    // Update a student
    public int updateStudent(int id, String name, String gender, String date, String email, String address, int idMajor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_GENDER, gender);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_ID_MAJOR, idMajor);
        return db.update(TABLE_STUDENT, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Delete a student
    public int deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STUDENT, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Get a single student by ID
    public Cursor getStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_STUDENT, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
    }

    // Get all students
    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_STUDENT, null, null, null, null, null, COLUMN_NAME + " ASC");
    }

    // CRUD Operations for Major Table

    // Insert a new major
    public long insertMajor(String nameMajor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_MAJOR, nameMajor);
        return db.insert(TABLE_MAJOR, null, contentValues);
    }

    // Update a major
    public int updateMajor(int idMajor, String nameMajor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_MAJOR, nameMajor);
        return db.update(TABLE_MAJOR, contentValues, COLUMN_ID_MAJOR + " = ?", new String[]{String.valueOf(idMajor)});
    }

    // Delete a major
    public int deleteMajor(int idMajor) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MAJOR, COLUMN_ID_MAJOR + " = ?", new String[]{String.valueOf(idMajor)});
    }

    // Get a single major by ID
    public Cursor getMajor(int idMajor) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_MAJOR, null, COLUMN_ID_MAJOR + " = ?", new String[]{String.valueOf(idMajor)}, null, null, null);
    }

    // Get all majors
    public Cursor getAllMajors() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_MAJOR, null, null, null, null, null, COLUMN_NAME_MAJOR + " ASC");
    }
}

