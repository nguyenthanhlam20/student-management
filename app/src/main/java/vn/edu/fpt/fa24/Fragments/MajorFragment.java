package vn.edu.fpt.fa24.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.fa24.Adapter.MajorAdapter;
import vn.edu.fpt.fa24.Helpers.DatabaseHelper;
import vn.edu.fpt.fa24.Models.Major;
import vn.edu.fpt.fa24.R;

public class MajorFragment extends Fragment {
    private ListView majorListView;
    private MajorAdapter majorAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_major, container, false);

        majorListView = view.findViewById(R.id.majorListView);
        databaseHelper = DatabaseHelper.getInstance(getActivity());

        List<Major> majors = getAllMajors();
        majorAdapter = new MajorAdapter(getActivity(), majors);
        majorListView.setAdapter(majorAdapter);
        return view;
    }

    private List<Major> getAllMajors() {
        List<Major> majorList = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllMajors(); // This should be implemented in DatabaseHelper

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idMajor = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_MAJOR));
                String nameMajor = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME_MAJOR));

                Log.d("MajorData", "ID: " + idMajor + ", Name: " + nameMajor); // Debug log

                Major major = new Major(idMajor, nameMajor);
                majorList.add(major);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Log.d("MajorData", "No majors found in database.");
        }

        return majorList;
    }
}