package vn.edu.fpt.fa24.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vn.edu.fpt.fa24.Models.Student;
import vn.edu.fpt.fa24.R;

public class StudentAdapter extends ArrayAdapter<Student> {

    public StudentAdapter(Context context, List<Student> students) {
        super(context, 0, students);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_list_item, parent, false);
        }

        Student student = getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView genderTextView = convertView.findViewById(R.id.genderTextView);
        TextView emailTextView = convertView.findViewById(R.id.emailTextView);

        nameTextView.setText(student.getName());
        genderTextView.setText(student.getGender());
        emailTextView.setText(student.getEmail());

        return convertView;
    }
}

