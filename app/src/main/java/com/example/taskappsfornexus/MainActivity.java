package com.example.taskappsfornexus;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskappsfornexus.adapters.ViewPagerAdapter;
import com.example.taskappsfornexus.fragments.News1Fragment;
import com.example.taskappsfornexus.fragments.News2Fragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assuming you have a ViewPager with id viewPager in your activity_main.xml
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        // Assuming you have a TabLayout with id tabLayout in your activity_main.xml
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Assuming you have two tabs (News 1 and News 2) in your TabLayout
        TabLayout.Tab tabNews1 = tabLayout.getTabAt(0);
        TabLayout.Tab tabNews2 = tabLayout.getTabAt(1);

        // Assuming you have two layout files for the content of News 1 and News 2
        ViewPagerAdapter adapter = new ViewPagerAdapter(this); // Pass `this` (which is a FragmentActivity) instead of getSupportFragmentManager()
        adapter.addFragment(new News1Fragment(), "News 1");
        adapter.addFragment(new News2Fragment(), "News 2");

        viewPager.setAdapter(adapter);

        // Connect ViewPager with TabLayout
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Set the text for each tab
            if (position == 0) {
                tab.setText("News 1");
            } else if (position == 1) {
                tab.setText("News 2");
            }
        }).attach();

        // Add a TabLayout.OnTabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Handle tab unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle tab reselected
            }
        });

    }
}

