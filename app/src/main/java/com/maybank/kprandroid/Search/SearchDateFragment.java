package com.maybank.kprandroid.Search;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.maybank.kprandroid.Configuration.ConfigSchedule;
import com.maybank.kprandroid.Customer.DetailCustomerFragment;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;
import com.maybank.kprandroid.Schedule.DetailScheduleFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchDateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchDateFragment extends Fragment {

    ViewGroup viewGroup;
    String JSON_STRING, start, end;
    EditText sch_start, sch_end;
    ListView lv_sch_search;
    ArrayAdapter<String> adapter;
    Button search;
    String id_emp;

    final Calendar calendar = Calendar.getInstance();

    public static SearchDateFragment newInstance(String param1, String param2) {
        SearchDateFragment fragment = new SearchDateFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_search_date, container, false);
        id_emp = this.getArguments().getString("id_emp_1");

        sch_start = viewGroup.findViewById(R.id.datestartSearch);
        sch_end = viewGroup.findViewById(R.id.dateendSearch);
        search = viewGroup.findViewById(R.id.search_date_button);

        lv_sch_search = viewGroup.findViewById(R.id.lv_date_search_sch);

        DatePickerDialog.OnDateSetListener date_start = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calendar.set(Calendar.YEAR, y);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.DAY_OF_MONTH, d);
                updateTextStart();
            }
        };
        DatePickerDialog.OnDateSetListener date_end = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calendar.set(Calendar.YEAR, y);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.DAY_OF_MONTH, d);
                updateTextEnd();
            }
        };

        sch_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(view.getContext(),R.style.DialogDatePicker_Theme, date_start, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        sch_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(view.getContext(),R.style.DialogDatePicker_Theme, date_end, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJSONDate();


            }
        });

        return viewGroup;
    }

    private void getJSONDate() {
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
                String result = handler.sendGetRespDate(ConfigSchedule.URL_SEARCH_DATE, start, end);
                Log.d("res:", result);
                Log.d("start:", start);
                Log.d("end:", end);
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
                }, 1500);

                JSON_STRING = s;
                Log.d("Data_JSON", JSON_STRING);

                JSONObject jsonObject = null;
                ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
                ArrayList<String> arrayList1 = new ArrayList<>();
                ArrayList<String> arrayList2 = new ArrayList<String>();

                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    JSONArray jsonArray = jsonObject.getJSONArray(ConfigSchedule.TAG_JSON_ARRAY);
                    Log.d("ass", String.valueOf(jsonArray));
                    Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


                    for (int i=0;i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString(ConfigSchedule.TAG_JSON_SCH_ID);
                        String id_nsb = object.getString(ConfigSchedule.TAG_JSON_SCH_ID_NSB);
                        String nama_nsb = object.getString(ConfigSchedule.TAG_JSON_SCH_NAMA_NSB);
                        String pesan = object.getString(ConfigSchedule.TAG_JSON_SCH_PESAN);
                        String tgl = object.getString(ConfigSchedule.TAG_JSON_SCH_TGL);

                        HashMap<String, String> schedule = new HashMap<>();
                        schedule.put(ConfigSchedule.TAG_JSON_SCH_ID, id);
                        schedule.put(ConfigSchedule.TAG_JSON_SCH_ID_NSB, id_nsb);
                        schedule.put(ConfigSchedule.TAG_JSON_SCH_NAMA_NSB, nama_nsb);
                        schedule.put(ConfigSchedule.TAG_JSON_SCH_PESAN, pesan);
                        schedule.put(ConfigSchedule.TAG_JSON_SCH_TGL, tgl);

                        arrayList.add(schedule);
                        arrayList1.add(nama_nsb);
                        arrayList2.add(id);
                        Log.d("DataArr: ", String.valueOf(schedule));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                ListAdapter adapter = new SimpleAdapter(
                        viewGroup.getContext(), arrayList, R.layout.lv_search_date,
                        new String[] {ConfigSchedule.TAG_JSON_SCH_NAMA_NSB, ConfigSchedule.TAG_JSON_SCH_TGL},
                        new int[] {R.id.lv_search_schedule_customer_name, R.id.lv_search_tgl_schedule, }
                );

                lv_sch_search.setAdapter(adapter);
                Log.d("spin", String.valueOf(arrayList));

                lv_sch_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        DetailScheduleFragment detailScheduleFragment = new DetailScheduleFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout,detailScheduleFragment);

                        String a = arrayList2.get(i);
                        Bundle args = new Bundle();
                        args.putString("id", a);
                        Log.d("idm: ", String.valueOf(a));
                        args.putString("id_emp_1", id_emp);
                        detailScheduleFragment.setArguments(args);
                        fragmentTransaction.commit();
                    }
                });

            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();

    }

    private void updateTextStart() {
        String date = "YYYY-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(date, Locale.ENGLISH);
        sch_start.setText(dateFormat.format(calendar.getTime()));
        start = dateFormat.format(calendar.getTime());
    }

    private void updateTextEnd() {
        String date = "YYYY-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(date, Locale.ENGLISH);
        sch_end.setText(dateFormat.format(calendar.getTime()));
        end = dateFormat.format(calendar.getTime());
    }
}