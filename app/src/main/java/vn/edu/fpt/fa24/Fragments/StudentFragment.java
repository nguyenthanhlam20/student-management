package vn.edu.fpt.fa24.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.fa24.Adapter.StudentAdapter;
import vn.edu.fpt.fa24.Helpers.DatabaseHelper;
import vn.edu.fpt.fa24.Models.Student;
import vn.edu.fpt.fa24.R;

public class StudentFragment extends Fragment {
    private ListView studentListView;
    private StudentAdapter studentAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mMainView = inflater.inflate(R.layout.fragment_student, container, false);
        studentListView = mMainView.findViewById(R.id.studentListView);
        databaseHelper = DatabaseHelper.getInstance(getActivity());
        Log.e("a","run here");

        List<Student> students = getAllStudents();
        studentAdapter = new StudentAdapter(getActivity(), students);
        studentListView.setAdapter(studentAdapter);

        return mMainView;
    }

    private List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllStudents();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
                String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENDER));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));

                Student student = new Student(id, name, gender, email);
                Log.i("a", student.getEmail());

                studentList.add(student);
            } while (cursor.moveToNext());
            cursor.close();
        }


        return studentList;
    }
}


