package vn.edu.fpt.fa24;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

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

        hideSplashView();
    }

    public void hideSplashView() {
        Handler handler = new Handler();
        handler.postDelayed(() -> binding.splashLayout.setVisibility(View.GONE), 1500);
        handleBottomNav();
    }

    private void handleBottomNav() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.home:
                    binding.viewpager.setCurrentItem(0);
                    binding.title.setText("Student");
                    binding.plusBtn.setOnClickListener(v -> {

                    });
                    break;

                case R.id.profile:
                    binding.viewpager.setCurrentItem(2);
                    binding.title.setText("Major");
                    binding.plusBtn.setOnClickListener(v -> {

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
                        binding.title.setText("Student");
                        break;

                    case R.id.profile:
                        binding.viewpager.setCurrentItem(2);
                        binding.title.setText("Major");
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}