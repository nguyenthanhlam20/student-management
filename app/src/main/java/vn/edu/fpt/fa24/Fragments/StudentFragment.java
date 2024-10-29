package vn.edu.fpt.fa24.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mMainView = inflater.inflate(R.layout.fragment_student, container, false);
        studentListView = mMainView.findViewById(R.id.studentListView);
        databaseHelper = DatabaseHelper.getInstance(getActivity());
        Log.e("a", "run here");

        loadStudents();
        return mMainView;
    }

    private void loadStudents() {
        List<Student> students = databaseHelper.getAllStudents();
        if (studentAdapter == null) {
            studentAdapter = new StudentAdapter(getActivity(), students);
            studentListView.setAdapter(studentAdapter);
        } else {
            studentAdapter.clear();
            studentAdapter.addAll(students);
            studentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}


