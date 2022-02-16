package com.maybank.kprandroid.Customer;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maybank.kprandroid.Configuration.ConfigCustomer;
import com.maybank.kprandroid.Configuration.ConfigSchedule;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;
import com.maybank.kprandroid.Schedule.AddScheduleFragment;
import com.maybank.kprandroid.Schedule.ScheduleFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


public class AddCustomerFragment extends Fragment implements View.OnClickListener {
    private String JSON_STRING, id_emp;
    private ViewGroup viewGroup;
    private Button tambah_customer;
    private EditText tambah_nama_nsb, tambah_ktp_nsb, tambah_tlahir_nsb, tambah_dob_nsb,
            tambah_hp_nsb, tambah_alamat_nsb;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    final Calendar calendar = Calendar.getInstance();
    boolean isAllFieldsChecked = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_add_customer, container, false);

        id_emp = this.getArguments().getString("id_emp_1");

        floatingActionButton = viewGroup.findViewById(R.id.customer_add);
        listView = viewGroup.findViewById(R.id.lv_customer);

        tambah_nama_nsb = viewGroup.findViewById(R.id.tambah_nama_nsb);
        tambah_ktp_nsb = viewGroup.findViewById(R.id.tambah_ktp_nsb);
        tambah_tlahir_nsb = viewGroup.findViewById(R.id.tambah_tlahir_nsb);
        tambah_dob_nsb = viewGroup.findViewById(R.id.tambah_dob_nsb);
        tambah_hp_nsb = viewGroup.findViewById(R.id.tambah_HP_nsb);
        tambah_alamat_nsb = viewGroup.findViewById(R.id.tambah_alamat_nsb);

        tambah_customer = viewGroup.findViewById(R.id.btn_tambah_nsb);

        //GET DATA SHARED

        DatePickerDialog.OnDateSetListener date_start = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calendar.set(Calendar.YEAR, y);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.DAY_OF_MONTH, d);
                updateTextStart();
            }
        };

        tambah_dob_nsb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(view.getContext(), date_start, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tambah_customer.setOnClickListener(this);
        return viewGroup;

    }


    @Override
    public void onClick(View view) {
        if (view == tambah_customer) {
            confirmAdd();
        }


    }

    private void confirmAdd() {

        final String nama = tambah_nama_nsb.getText().toString().trim();
        final String tgl_lahir = tambah_dob_nsb.getText().toString().trim();
        final String alamat = tambah_alamat_nsb.getText().toString().trim();
        final String hp = tambah_hp_nsb.getText().toString().trim();
        final String tempat_lahir = tambah_tlahir_nsb.getText().toString().trim();
        final String ktp = tambah_ktp_nsb.getText().toString().trim();


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Are you sure want to add this data? " +
                "\n Nama: " + nama +
                "\n No E-KTP: " + ktp +
                "\n Tempat Lahir: " + tempat_lahir +
                "\n Tanggal Lahir: " + tgl_lahir +
                "\n No Telephone: " + hp +
                "\n Alamat: " + alamat
        );

        alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isAllFieldsChecked = CheckAllFields();
            }
        });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Tidak ngapa-ngapain
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private boolean CheckAllFields() {

        if (tambah_nama_nsb.length() == 0) {
            tambah_nama_nsb.setError("This field is required");
            return false;
        } else if (tambah_ktp_nsb.length() != 16) {
            tambah_ktp_nsb.setError("This field must be 16 digit");
            return false;
        } else if (tambah_tlahir_nsb.length() == 0) {
            tambah_tlahir_nsb.setError("This field is required");
            return false;
        } else if (tambah_hp_nsb.length() !=10 && tambah_hp_nsb.length() !=12 ) {
            tambah_hp_nsb.setError("This field must between 10 or 12 digit  ");
            return false;
        } else if (tambah_alamat_nsb.length() == 0) {
            tambah_alamat_nsb.setError("This field is required");
            return false;
        } else {
            addCustomer();
        }

        // after all validation return true.
        return true;
    }

    private void addCustomer() {

        String nama_nsb = tambah_nama_nsb.getText().toString().trim();
        String ktp_nsb = tambah_ktp_nsb.getText().toString().trim();
        String tlahir_nsb = tambah_tlahir_nsb.getText().toString().trim();
        String dob_nsb = tambah_dob_nsb.getText().toString().trim();
        String hp_nsb = tambah_hp_nsb.getText().toString().trim();
        String alamat_nsb = tambah_alamat_nsb.getText().toString().trim();


        class AddCustomer extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Saving Data", "Please Wait...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(ConfigCustomer.KEY_CST_NAME, nama_nsb);
                params.put(ConfigCustomer.KEY_CST_KTP, ktp_nsb);
                params.put(ConfigCustomer.KEY_CST_TMPT_LAHIR, tlahir_nsb);
                params.put(ConfigCustomer.KEY_CST_TGL_LAHIR, dob_nsb);
                params.put(ConfigCustomer.KEY_CST_HP, hp_nsb);
                params.put(ConfigCustomer.KEY_CST_ALAMAT, alamat_nsb);
                params.put(ConfigCustomer.KEY_CST_ID_EMP, id_emp);
                Log.d("cost: ", String.valueOf(params));

                HttpHandler handler = new HttpHandler();
                String res = handler.sendPostReq(ConfigCustomer.URL_ADD_CUSTOMER, params);
                return res;
            }

            @Override
            protected void onPostExecute(String messsage) {
                super.onPostExecute(messsage);
                loading.dismiss();
                Toast.makeText(getContext(), messsage, Toast.LENGTH_LONG).show();
                Log.d("m:", messsage);
                clearText();

                CustomerFragment customerFragment = new CustomerFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,customerFragment);
                Bundle arg = new Bundle();
                arg.putString("id_emp_1", id_emp);
                customerFragment.setArguments(arg);
                fragmentTransaction.commit();
            }
        }

        AddCustomer addCustomer = new AddCustomer();
        addCustomer.execute();
    }


    private void updateTextStart() {

        String date = "YYYY-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(date, Locale.ENGLISH);
        tambah_dob_nsb.setText(dateFormat.format(calendar.getTime()));

    }

    private void clearText() {
        tambah_nama_nsb.setText("");
        tambah_dob_nsb.setText("");
        tambah_alamat_nsb.setText("");
        tambah_hp_nsb.setText("");
        tambah_tlahir_nsb.setText("");
        tambah_ktp_nsb.setText("");
        tambah_nama_nsb.requestFocus();
    }
}