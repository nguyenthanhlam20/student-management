package vn.edu.fpt.fa24;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import vn.edu.fpt.fa24.Adapter.HomePageFragmentAdapter;
import vn.edu.fpt.fa24.databinding.ActivityMainBinding;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    HomePageFragmentAdapter mSectionsPagerAdapter;
    ActivityMainBinding binding;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mSectionsPagerAdapter = new HomePageFragmentAdapter(getSupportFragmentManager());
        binding.viewpager.setAdapter(mSectionsPagerAdapter);
        binding.viewpager.setOffscreenPageLimit(0);
        binding.plusBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
            startActivity(intent);
            finish();
        });

        handleBottomNav();
    }

    private void handleBottomNav() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.home:
                    binding.viewpager.setCurrentItem(0);
                    binding.title.setText("Students");
                    binding.plusBtn.setOnClickListener(v -> {
                        Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                        startActivity(intent);
                        finish();
                    });
                    break;

                case R.id.profile:
                    binding.viewpager.setCurrentItem(2);
                    binding.title.setText("Majors");
                    binding.plusBtn.setOnClickListener(v -> {
                        Intent intent = new Intent(MainActivity.this, AddMajorActivity.class);
                        startActivity(intent);
                        finish();
                    });
                    break;

            }
            return false;
        });
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    binding.bottomNavigation.getMenu().getItem(0).setChecked(false);

                binding.bottomNavigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = binding.bottomNavigation.getMenu().getItem(position);

                switch (prevMenuItem.getItemId()) {
                    case R.id.home:
                        binding.viewpager.setCurrentItem(0);
                        binding.title.setText("Students");
                        binding.plusBtn.setOnClickListener(v -> {
                            Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                            startActivity(intent);
                            finish();
                        });
                        break;

                    case R.id.profile:
                        binding.viewpager.setCurrentItem(2);
                        binding.title.setText("Majors");
                        binding.plusBtn.setOnClickListener(v -> {
                            Intent intent = new Intent(MainActivity.this, AddMajorActivity.class);
                            startActivity(intent);
                            finish();
                        });
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}