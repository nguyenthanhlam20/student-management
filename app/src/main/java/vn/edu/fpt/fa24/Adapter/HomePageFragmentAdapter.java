package vn.edu.fpt.fa24.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import vn.edu.fpt.fa24.Fragments.StudentFragment;
import vn.edu.fpt.fa24.Fragments.MajorFragment;


@SuppressWarnings("ALL")
public class HomePageFragmentAdapter extends FragmentPagerAdapter {

    public HomePageFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new StudentFragment();
            case 1:
                return new MajorFragment();
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
