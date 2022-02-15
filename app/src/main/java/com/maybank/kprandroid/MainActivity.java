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

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.maybank.kprandroid.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);

//        getSupportActionBar().setTitle("Account");
//        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new AccountFragment()).commit();
//        binding.navbarView.setCheckedItem(R.id.nav_account);

        toggle = new ActionBarDrawerToggle(this, binding.navDrawer, binding.toolbar, R.string.open, R.string.close);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        toggle.syncState();

        final Fragment[] fragments = {null};

        binding.navbarView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
//                    case R.id.nav_account:
//                        fragments[0] = new AccountFragment();
//                        getSupportActionBar().setTitle("Account");
//                        mainBinding.navDrawer.closeDrawer(GravityCompat.START);
//                        callFragment(fragments[0]);
//                        break;
//                    case R.id.nav_participant:
//                        fragments[0] = new ParticipantFragment();
//                        getSupportActionBar().setTitle("Participant");
//                        mainBinding.navDrawer.closeDrawer(GravityCompat.START);
//                        callFragment(fragments[0]);
//                        break;
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