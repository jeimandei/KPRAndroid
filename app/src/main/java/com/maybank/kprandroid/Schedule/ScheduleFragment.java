package com.maybank.kprandroid.Schedule;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maybank.kprandroid.Configuration.ConfigSchedule;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String JSON_STRING;
    private ViewGroup viewGroup;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    String id_emp;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_schedule, container, false);

        floatingActionButton = viewGroup.findViewById(R.id.schedule_add);
        listView = viewGroup.findViewById(R.id.lv_schedule);

        id_emp = this.getArguments().getString("id_emp_1");

        getJSON();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddScheduleFragment addScheduleFragment = new AddScheduleFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,addScheduleFragment);
                callFragment(addScheduleFragment);
                fragmentTransaction.commit();
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
                String result = handler.sendGetResp(ConfigSchedule.URL_GET_ALL_SCHEDULE, id_emp);
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

                displayAllParticipant();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayAllParticipant() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(ConfigSchedule.TAG_JSON_ARRAY);
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
                Log.d("DataArr: ", String.valueOf(schedule));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                viewGroup.getContext(), arrayList, R.layout.lv_schedule,
                new String[] {ConfigSchedule.TAG_JSON_SCH_NAMA_NSB, ConfigSchedule.TAG_JSON_SCH_TGL, ConfigSchedule.TAG_JSON_SCH_PESAN},
                new int[] {R.id.lv_schedule_customer_name, R.id.lv_tgl_schedule, R.id.lv_message}
        );
        Log.d("DataArray: ", String.valueOf(adapter));
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                DetailScheduleFragment detailScheduleFragment = new DetailScheduleFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,detailScheduleFragment);


                HashMap<String, String> map = (HashMap) adapterView.getItemAtPosition(i);
                String schid = map.get(ConfigSchedule.TAG_JSON_SCH_ID).toString();
                Bundle args = new Bundle();
                args.putString("id", schid);
                detailScheduleFragment.setArguments(args);

                Log.d("Par: ", String.valueOf(args));
                fragmentTransaction.commit();
            }
        });
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