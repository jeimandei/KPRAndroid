package com.maybank.kprandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.maybank.kprandroid.Configuration.ConfigLogin;
import com.maybank.kprandroid.Customer.CustomerFragment;
import com.maybank.kprandroid.Login.LoginActivity;
import com.maybank.kprandroid.Manager.ManagerFragment;
import com.maybank.kprandroid.Schedule.ScheduleFragment;
import com.maybank.kprandroid.Search.SearchCustomerFragment;
import com.maybank.kprandroid.Search.SearchDateFragment;
import com.maybank.kprandroid.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    MenuItem manager;
    Toolbar toolbar;
    String id, role;
    EditText id_login,pass_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_login);

        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);

        id_login = findViewById(R.id.id_emp);
        pass_login = findViewById(R.id.password);


        setSupportActionBar(toolbar);

        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra(ConfigLogin.EMP_ID);
        role = receiveIntent.getStringExtra(ConfigLogin.EMP_ROLE);

        Log.d("id_main:", role);

        invalidateOptionsMenu();
        initView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_logout:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
                alertDialogBuilder.setMessage("Are you sure want to logout ? "
                );
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(1);
                    }
                });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Tidak ngapa-ngapain
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
//                clearText();
                break;
            case R.id.id_search_cus:
                SearchCustomerFragment searchCustomerFragment = new SearchCustomerFragment();
                getSupportActionBar().setTitle("Search Customer");
                binding.navDrawer.closeDrawer(GravityCompat.START);
                Bundle arg = new Bundle();
                arg.putString("id_emp_1", id);
                searchCustomerFragment.setArguments(arg);
                Log.d("cekIDS:", id);
                callFragment(searchCustomerFragment);
                break;
            case R.id.id_search_date:
                SearchDateFragment searchDateFragment = new SearchDateFragment();
                getSupportActionBar().setTitle("Search Schedule");
                binding.navDrawer.closeDrawer(GravityCompat.START);
                Bundle arg1 = new Bundle();
                arg1.putString("id_emp_1", id);
                searchDateFragment.setArguments(arg1);
                Log.d("cekIDS:", id);
                callFragment(searchDateFragment);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setTitle("Schedule");

        ScheduleFragment scheduleFragment = new ScheduleFragment();
        Bundle arg = new Bundle();
        arg.putString("id_emp_1", id);
        scheduleFragment.setArguments(arg);
        Log.d("cekIDS:", id);
        callFragment(scheduleFragment);

//        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new ScheduleFragment()).commit();
        binding.navbarView.setCheckedItem(R.id.nav_agenda);
        if (role.equals("kpr")){
            binding.navbarView.getMenu().removeItem(R.id.nav_manager);
        }

        toggle = new ActionBarDrawerToggle(this, binding.navDrawer, binding.toolbar, R.string.open, R.string.close);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));

        toggle.syncState();

        final Fragment[] fragments = {null};

        binding.navbarView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_agenda:
                        ScheduleFragment scheduleFragment = new ScheduleFragment();
                        getSupportActionBar().setTitle("Schedule");
                        binding.navDrawer.closeDrawer(GravityCompat.START);
                        Bundle arg = new Bundle();
                        arg.putString("id_emp_1", id);
                        scheduleFragment.setArguments(arg);
                        Log.d("cekIDS:", id);
                        callFragment(scheduleFragment);
                        break;
                    case R.id.nav_nasabah:
                        fragments[0] = new CustomerFragment();
                        getSupportActionBar().setTitle("Customer");
                        binding.navDrawer.closeDrawer(GravityCompat.START);
                        Bundle arg1 = new Bundle();
                        arg1.putString("id_emp", id);
                        Log.d("cekID:", id);
                        fragments[0].setArguments(arg1);
                        callFragment(fragments[0]);
                        break;
                    case R.id.nav_manager:
                        fragments[0] = new ManagerFragment();
                        getSupportActionBar().setTitle("Approval");
                        binding.navDrawer.closeDrawer(GravityCompat.START);
                        Bundle argw = new Bundle();
                        argw.putString("id_emp", id);
                        Log.d("cekID:", id);
                        fragments[0].setArguments(argw);
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