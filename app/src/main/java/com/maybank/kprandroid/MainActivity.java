package com.maybank.kprandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.maybank.kprandroid.Configuration.ConfigLogin;
import com.maybank.kprandroid.Customer.CustomerFragment;
import com.maybank.kprandroid.Schedule.ScheduleFragment;
import com.maybank.kprandroid.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra(ConfigLogin.EMP_ID);
        Log.d("id_main:", id);


        initView();
    }


    private void initView() {
        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setTitle("Meeting Schedule");
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new ScheduleFragment()).commit();
        binding.navbarView.setCheckedItem(R.id.nav_agenda);

        toggle = new ActionBarDrawerToggle(this, binding.navDrawer, binding.toolbar, R.string.open, R.string.close);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));

        toggle.syncState();

        final Fragment[] fragments = {null};

        binding.navbarView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_agenda:
                        fragments[0] = new ScheduleFragment();
                        getSupportActionBar().setTitle("Schedule");
                        binding.navDrawer.closeDrawer(GravityCompat.START);

                        callFragment(fragments[0]);
                        break;
                    case R.id.nav_nasabah:
                        fragments[0] = new CustomerFragment();
                        getSupportActionBar().setTitle("Customer");
                        binding.navDrawer.closeDrawer(GravityCompat.START);
                        Bundle arg = new Bundle();
                        arg.putString("id_emp", id);
                        fragments[0].setArguments(arg);
                        callFragment(fragments[0]);
                        break;
                }
                return true;
            }
        });
    }
    public void callFragment(Fragment fragment) {
        FragmentManager man = getSupportFragmentManager();
        FragmentTransaction trans = man.beginTransaction();
        trans.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
        );
        trans.replace(R.id.framelayout, fragment);
        trans.addToBackStack(null);
        trans.commit();
    }
}