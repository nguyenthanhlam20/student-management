package vn.edu.fpt.fa24.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vn.edu.fpt.fa24.Models.Student;
import vn.edu.fpt.fa24.R;
import vn.edu.fpt.fa24.StudentDetailsActivity;

public class StudentAdapter extends ArrayAdapter<Student> {

    public StudentAdapter(Context context, List<Student> students) {
        super(context, 0, students);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_list_item, parent, false);
        }

        LinearLayout studentItemLayout = convertView.findViewById(R.id.studentItemLayout); // Root layout ID to be defined in XML

        Student student = getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView genderTextView = convertView.findViewById(R.id.genderTextView);
        TextView emailTextView = convertView.findViewById(R.id.emailTextView);
        TextView majorTextView = convertView.findViewById(R.id.majorTextView);

        nameTextView.setText(student.getName());
        genderTextView.setText(student.getGender());
        emailTextView.setText(student.getEmail());
        majorTextView.setText(student.getMajor());

        studentItemLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), StudentDetailsActivity.class);
            intent.putExtra("student_id", student.getId()); // Assuming getId() gives student ID
            getContext().startActivity(intent);

        });

        return convertView;
    }
}

