package vn.edu.fpt.fa24.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vn.edu.fpt.fa24.Models.Major;
import vn.edu.fpt.fa24.R;

public class MajorAdapter extends ArrayAdapter<Major> {

    public MajorAdapter(Context context, List<Major> majors) {
        super(context, 0, majors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.major_list_item, parent, false);
        }

        Major major = getItem(position);
        TextView majorNameTextView = convertView.findViewById(R.id.majorNameTextView);
        majorNameTextView.setText(major.getNameMajor());

        return convertView;
    }
}

