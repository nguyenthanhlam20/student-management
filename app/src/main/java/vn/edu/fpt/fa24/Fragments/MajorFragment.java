package vn.edu.fpt.fa24.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

        List<Major> majors = databaseHelper.getAllMajors();
        majorAdapter = new MajorAdapter(getActivity(), majors);
        majorListView.setAdapter(majorAdapter);

        return view;
    }
}