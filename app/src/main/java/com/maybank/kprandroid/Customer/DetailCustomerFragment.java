package com.maybank.kprandroid.Customer;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maybank.kprandroid.Configuration.ConfigCustomer;
import com.maybank.kprandroid.Configuration.ConfigSchedule;
import com.maybank.kprandroid.Document.DocumentFragment;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;
import com.maybank.kprandroid.Schedule.ScheduleFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


public class DetailCustomerFragment extends Fragment implements View.OnClickListener {
    private String JSON_STRING;
    private ViewGroup viewGroup;
    String id_cust, dates, id_emp;
    EditText cust_name, cust_dob, cust_bp, cust_ph, cust_addr;
    Button update, delete, doc;
    TextView cust_ktp;

    final Calendar calendar = Calendar.getInstance();

    public static DetailCustomerFragment newInstance(String param1, String param2) {
        DetailCustomerFragment fragment = new DetailCustomerFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_detail_customer, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail Customer");
        id_cust = this.getArguments().getString("id");
        id_emp = this.getArguments().getString("id_emp_1");
        Toast.makeText(getContext(), ""+id_emp, Toast.LENGTH_SHORT).show();
        cust_name = viewGroup.findViewById(R.id.edit_nama_nsb);
        cust_ktp = viewGroup.findViewById(R.id.edit_ktp_nsb);
        cust_bp = viewGroup.findViewById(R.id.edit_tlahir_nsb);
        cust_dob = viewGroup.findViewById(R.id.edit_dob_nsb);
        cust_ph = viewGroup.findViewById(R.id.edit_HP_nsb);
        cust_addr = viewGroup.findViewById(R.id.edit_alamat_nsb);
        update = viewGroup.findViewById(R.id.btn_update_nsb);
        delete = viewGroup.findViewById(R.id.btn_delete_nsb);
        doc = viewGroup.findViewById(R.id.upload_doc);

        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentFragment documentFragment = new DocumentFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, documentFragment);
                Bundle arg = new Bundle();
                arg.putString("id_nsb", id_cust);
                documentFragment.setArguments(arg);
                callFragment(documentFragment);
                fragmentTransaction.commit();
            }
        });

        DatePickerDialog.OnDateSetListener datedob = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calendar.set(Calendar.YEAR, y);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.DAY_OF_MONTH, d);
                updateText();
            }
        };

        cust_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), datedob, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();


            }
        });

        getJSON();

        update.setOnClickListener(this);
        delete.setOnClickListener(this);


        return viewGroup;
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(getContext(), "Getting Data", "Please wait...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResp(ConfigCustomer.URL_GET_DETAIL_CUSTOMER, id_cust);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                displayDetailData(s);

                Log.d("CekNama", s);

            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(ConfigCustomer.TAG_JSON_ARRAY);
            JSONObject object = jsonArray.getJSONObject(0);

            String name = object.getString(ConfigCustomer.TAG_JSON_CST_NAME);
            String ktp = object.getString(ConfigCustomer.TAG_JSON_CST_KTP);
            String pb = object.getString(ConfigCustomer.TAG_JSON_CST_TMPT_LAHIR);
            String dob = object.getString(ConfigCustomer.TAG_JSON_CST_TGL_LAHIR);
            String ph = object.getString(ConfigCustomer.TAG_JSON_CST_HP);
            String addr = object.getString(ConfigCustomer.TAG_JSON_CST_ALAMAT);

            cust_name.setText(name);
            cust_ktp.setText(ktp);
            cust_bp.setText(pb);
            cust_dob.setText(dob);
            cust_ph.setText(ph);
            cust_addr.setText(addr);

            Log.d("CekNama", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateText() {
        String date = "YYYY-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(date, Locale.ENGLISH);
        cust_dob.setText(dateFormat.format(calendar.getTime()));
        dates = dateFormat.format(calendar.getTime());
    }

    @Override
    public void onClick(View view) {
        if (view == update) {
            confirmUpdate();
        } else if (view == delete) {
            confirmDelete();
        }
    }

    private void confirmUpdate() {
        final String name = cust_name.getText().toString().trim();
        final String ktp = cust_ktp.getText().toString().trim();
        final String pb = cust_bp.getText().toString().trim();
        final String dob = cust_dob.getText().toString().trim();
        final String ph = cust_ph.getText().toString().trim();
        final String addr = cust_addr.getText().toString().trim();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Are you sure want to update this data? " +
                "\n Name           :  " + name +
                "\n ID Card        :  " + ktp +
                "\n Place of Birth :  " + pb +
                "\n Date of Birth  :  " + dob +
                "\n Phone          :  " + ph +
                "\n Addres         :  " + addr);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updatensb();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Tidak ngapa-ngapain
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void updatensb() {

        final String name = cust_name.getText().toString().trim();
        final String ktp = cust_ktp.getText().toString().trim();
        final String pb = cust_bp.getText().toString().trim();
        final String dob = cust_dob.getText().toString().trim();
        final String ph = cust_ph.getText().toString().trim();
        final String addr = cust_addr.getText().toString().trim();

        class Updatensb extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),
                        "Updating...", "Wait...", false, false);

            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(ConfigCustomer.KEY_CST_ID, id_cust);
                hashMap.put(ConfigCustomer.KEY_CST_NAME, name);
                hashMap.put(ConfigCustomer.KEY_CST_KTP, ktp);
                hashMap.put(ConfigCustomer.KEY_CST_TMPT_LAHIR, pb);
                hashMap.put(ConfigCustomer.KEY_CST_TGL_LAHIR, dob);
                hashMap.put(ConfigCustomer.KEY_CST_HP, ph);
                hashMap.put(ConfigCustomer.KEY_CST_ALAMAT, addr);

                HttpHandler handler = new HttpHandler();
                String s = handler.sendPostReq(ConfigCustomer.URL_UPDATE_CUSTOMER, hashMap);
                Log.d("Cek_Hasil", String.valueOf(hashMap));
                Log.d("Cek_Hasil", s);
                return s;
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                CustomerFragment customerFragment = new CustomerFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, customerFragment);
                Bundle arg = new Bundle();
                arg.putString("id_emp", id_emp);
                customerFragment.setArguments(arg);
                callFragment(customerFragment);
                fragmentTransaction.commit();

            }
        }

        Updatensb ue = new Updatensb();
        ue.execute();

    }

    private void confirmDelete() {

        final String name = cust_name.getText().toString().trim();
        final String ktp = cust_ktp.getText().toString().trim();
        final String pb = cust_bp.getText().toString().trim();
        final String dob = cust_dob.getText().toString().trim();
        final String ph = cust_ph.getText().toString().trim();
        final String addr = cust_addr.getText().toString().trim();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Are you sure want to delete this data? " +
                "\n Name           :  " + name +
                "\n ID Card        :  " + ktp +
                "\n Place of Birth :  " + pb +
                "\n Date of Birth  :  " + dob +
                "\n Phone          :  " + ph +
                "\n Addres         :  " + addr);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteMemo();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Tidak ngapa-ngapain
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteMemo() {

        class DeleteSch extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),
                        "Deleting...", "Wait...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String s = handler.sendGetResp(ConfigCustomer.URL_DELETE_CUSTOMER, id_cust);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getContext(), "" + s,
                        Toast.LENGTH_SHORT).show();

                CustomerFragment customerFragment = new CustomerFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, customerFragment);
                Bundle arg = new Bundle();
                arg.putString("id_emp", id_emp);
                customerFragment.setArguments(arg);
                callFragment(customerFragment);
                fragmentTransaction.commit();


            }

        }

        DeleteSch deleteSch = new DeleteSch();
        deleteSch.execute();

    }

    public void callFragment(Fragment fragment) {
        FragmentManager man = getActivity().getSupportFragmentManager();
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