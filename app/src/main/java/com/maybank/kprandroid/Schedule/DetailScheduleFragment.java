package com.maybank.kprandroid.Schedule;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maybank.kprandroid.Configuration.ConfigSchedule;
import com.maybank.kprandroid.Customer.CustomerFragment;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.MainActivity;
import com.maybank.kprandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


public class DetailScheduleFragment extends Fragment implements View.OnClickListener {
    private String JSON_STRING;
    private ViewGroup viewGroup;
    private FloatingActionButton floatingActionButton;
    String id_sch, dates, id_emp;
    EditText sch_date, sch_msg;
    Button update, delete;
    TextView sch_name;
    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;

    final Calendar calendar = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_detail_schedule, container, false);
        id_sch = this.getArguments().getString("id");
        id_emp = this.getArguments().getString("id_emp_1");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Detail Schedule Customer");


        sch_date = viewGroup.findViewById(R.id.edit_tgl_bertemu);
        sch_msg = viewGroup.findViewById(R.id.edit_isi_memo);
        sch_name = viewGroup.findViewById(R.id.sch_nama_nsb);

        update = viewGroup.findViewById(R.id.btn_update_sch);
        delete = viewGroup.findViewById(R.id.btn_delete_sch);

        getJSON();

        update.setOnClickListener(this);
        delete.setOnClickListener(this);

        DatePickerDialog.OnDateSetListener date_start = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calendar.set(Calendar.YEAR, y);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.DAY_OF_MONTH, d);
                updateText();
            }
        };

        sch_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), R.style.DialogDatePicker_Theme,date_start, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
            }
        });
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
                String result = handler.sendGetResp(ConfigSchedule.URL_GET_DETAIL_SCHEDULE, id_sch);
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
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(ConfigSchedule.TAG_JSON_ARRAY);
            JSONObject object = jsonArray.getJSONObject(0);

            String name = object.getString(ConfigSchedule.TAG_JSON_SCH_NAMA_NSB);
            String date = object.getString(ConfigSchedule.TAG_JSON_SCH_TGL);
            String message = object.getString(ConfigSchedule.TAG_JSON_SCH_PESAN);

            sch_name.setText(name);
            sch_date.setText(date);
            sch_msg.setText(message);

            Log.d("CekNama", json);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateText() {
        String date = "YYYY-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(date, Locale.ENGLISH);
        sch_date.setText(dateFormat.format(calendar.getTime()));
        dates = dateFormat.format(calendar.getTime());
    }

    @Override
    public void onClick(View view) {
        if (view == update){
            confirmUpdate();
        }else if (view == delete){
            confirmDelete();
        }
    }

    private void confirmUpdate() {

        final String tanggal = sch_date.getText().toString().trim();
        final String memo = sch_msg.getText().toString().trim();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        alertDialogBuilder.setMessage("Are you sure want to update this data? " +
                "\n Meeting Date\t\t:  " + tanggal +
                "\n Message\t\t\t\t\t:  " + memo );

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateSch();
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

    private void updateSch() {

        final String tanggal_temu = sch_date.getText().toString().trim();
        final String memo = sch_msg.getText().toString().trim();

        class UpdateSch extends AsyncTask<Void, Void, String>{
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
                hashMap.put(ConfigSchedule.KEY_SCH_ID, id_sch);
                hashMap.put(ConfigSchedule.KEY_SCH_TGL, tanggal_temu);
                hashMap.put(ConfigSchedule.KEY_SCH_PESAN, memo);

                HttpHandler handler = new HttpHandler();
                String s = handler.sendPostReq(ConfigSchedule.URL_UPDATE_SCHEDULE , hashMap);
                Log.d("Cek_Hasil", String.valueOf(hashMap));
                Log.d("Cek_Hasil", s);
                return s;
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if (s.equals("Berhasil Update Data Janji Temu")){
                    showAlertDialog(R.layout.alert_update);
                } else {
                    showAlertDialog(R.layout.alert_field);
                }

                ScheduleFragment scheduleFragment = new ScheduleFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, scheduleFragment);
                Bundle arg = new Bundle();
                arg.putString("id_emp_1", id_emp);
                scheduleFragment.setArguments(arg);
                fragmentTransaction.commit();
            }
        }

        UpdateSch ue = new UpdateSch();
        ue.execute();

    }

    private void showAlertDialog(int alert_update) {

        builderDialog = new AlertDialog.Builder(getContext());
        View layoutView = getLayoutInflater().inflate(alert_update, null);

        AppCompatButton dialogButton = layoutView.findViewById(R.id.buttonOk);
        builderDialog.setView(layoutView);
        alertDialog = builderDialog.create();
        alertDialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

            }
        }, 4000);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    private void confirmDelete() {

        final String tanggal_temu = sch_date.getText().toString().trim();
        final String memo = sch_msg.getText().toString().trim();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        alertDialogBuilder.setMessage("Are you sure want to delete this data? " +
                "\n Meeting Date\t\t:  " + tanggal_temu +
                "\n Message\t\t\t\t\t:  " + memo );

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

            class DeleteSch extends AsyncTask<Void, Void, String>{
                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(getContext(),
                            "Updating...", "Tunggu...",
                            false, false);
                }

                @Override
                protected String doInBackground(Void... voids) {
                    HttpHandler handler = new HttpHandler();
                    String s = handler.sendGetResp(ConfigSchedule.URL_DELETE_SCHEDULE, id_sch);
                    return s;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();

                    if (s.equals("Berhasil Menghapus Data Janji Temu")){
                        showAlertDialog(R.layout.alert_field);
                    } else {
                        showAlertDialog(R.layout.alert_delete);
                    }

                    ScheduleFragment scheduleFragment = new ScheduleFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout, scheduleFragment);
                    Bundle arg = new Bundle();
                    arg.putString("id_emp_1", id_emp);
                    scheduleFragment.setArguments(arg);
                    fragmentTransaction.commit();


                }

            }

        DeleteSch deleteSch = new DeleteSch();
            deleteSch.execute();

    }
}