package com.maybank.kprandroid.Schedule;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maybank.kprandroid.Configuration.ConfigSchedule;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class DetailScheduleFragment extends Fragment {
    private String JSON_STRING;
    private ViewGroup viewGroup;
    private FloatingActionButton floatingActionButton;
    String id_sch, dates;
    EditText sch_date, sch_msg;
    Button update, delete;
    TextView sch_name;

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

        sch_date = viewGroup.findViewById(R.id.edit_tgl_bertemu);
        sch_msg = viewGroup.findViewById(R.id.edit_isi_memo);
        sch_name = viewGroup.findViewById(R.id.sch_nama_nsb);

        update = viewGroup.findViewById(R.id.btn_update_sch);
        delete = viewGroup.findViewById(R.id.btn_delete_sch);

        getJSON();

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
                new DatePickerDialog(view.getContext(), date_start, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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
}