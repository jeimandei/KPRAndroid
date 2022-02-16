package com.maybank.kprandroid.Schedule;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maybank.kprandroid.Configuration.ConfigCustomer;
import com.maybank.kprandroid.Configuration.ConfigSchedule;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class AddScheduleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private EditText tgl, message;
    private Spinner spin_nsb;
    private Button tambah_schedule;
    private ViewGroup viewGroup;
    private String JSON_STRING, c_nsb_id;
    String id_emp;

    final Calendar calendar = Calendar.getInstance();

    public AddScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_add_schedule, container, false);

        tgl = viewGroup.findViewById(R.id.tgljanjiketemu);
        message = viewGroup.findViewById(R.id.isimemo);
        tambah_schedule = viewGroup.findViewById(R.id.btn_tambah_Schedule);

        id_emp = this.getArguments().getString("id_emp");

        spin_nsb = viewGroup.findViewById(R.id.spinner_id_cust);

        DatePickerDialog.OnDateSetListener date_start = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calendar.set(Calendar.YEAR, y);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.DAY_OF_MONTH, d);
                updateTextStart();
            }
        };

        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(view.getContext(), date_start, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        getJSON();

        spin_nsb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                c_nsb_id = String.valueOf(spin_nsb.getSelectedItemId());
                Log.d("qwe:", c_nsb_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tambah_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tgl_jantem = tgl.getText().toString().trim();
                String pesan = message.getText().toString().trim();


                class SaveData extends AsyncTask<Void, Void, String> {
                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(getContext(), "Saving Data", "Please Wait...", false, false);
                    }

                    @Override
                    protected String doInBackground(Void... voids) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put(ConfigSchedule.KEY_SCH_TGL, tgl_jantem);
                        params.put(ConfigSchedule.KEY_SCH_PESAN, pesan);
                        params.put(ConfigSchedule.KEY_SCH_ID_NSB, String.valueOf(Integer.parseInt(c_nsb_id+1)));
                        Log.d("inputss", String.valueOf(params));
                        HttpHandler handler = new HttpHandler();
                        String res = handler.sendPostReq(ConfigSchedule.URL_ADD_SCHEDULE, params);
                        return res;
                    }

                    @Override
                    protected void onPostExecute(String messsage) {
                        super.onPostExecute(messsage);
                        loading.dismiss();
                        Toast.makeText(getContext(), messsage, Toast.LENGTH_LONG).show();
                        Log.d("m:", messsage);
                        clearText();

                        ScheduleFragment scheduleFragment = new ScheduleFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout,scheduleFragment);
                        Bundle arg = new Bundle();
                        arg.putString("id_emp_1", id_emp);
                        scheduleFragment.setArguments(arg);

                        fragmentTransaction.commit();
                    }
                }
                SaveData saveData = new SaveData();
                saveData.execute();

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
                String result = handler.sendGetResp(ConfigCustomer.URL_GET_ALL_CUSTOMER, id_emp);
                Log.d("GetData", result);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 1000);

                JSON_STRING = s;
                Log.d("Data_JSON", JSON_STRING);

                JSONObject jsonObject = null;
                ArrayList<String> arrayList = new ArrayList<>();

                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    JSONArray jsonArray = jsonObject.getJSONArray(ConfigCustomer.TAG_JSON_ARRAY);
                    Log.d("ass", String.valueOf(jsonArray));
                    Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


                    for (int i=0;i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString(ConfigCustomer.TAG_JSON_CST_ID);
                        String name = object.getString(ConfigCustomer.TAG_JSON_CST_NAME);

                        arrayList.add(name);
                        Log.d("DataArr: ", String.valueOf(name));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spin_nsb.setAdapter(adapter);
                Log.d("spin", String.valueOf(arrayList));

            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();

    }

    private void updateTextStart() {
        String date = "YYYY-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(date, Locale.ENGLISH);
        tgl.setText(dateFormat.format(calendar.getTime()));
    }

    private void clearText() {
        tgl.setText("");
        tgl.requestFocus();
    }
}