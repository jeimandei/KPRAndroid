package com.maybank.kprandroid.Customer;

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
import com.maybank.kprandroid.Configuration.ConfigCustomer;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String JSON_STRING;
    private ViewGroup viewGroup;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    String id_emp;

    public CustomerFragment() {
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_customer, container, false);

        floatingActionButton = viewGroup.findViewById(R.id.customer_add);
        listView = viewGroup.findViewById(R.id.lv_customer);

        id_emp = this.getArguments().getString("id_emp");


        getJSON();


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
            JSONArray jsonArray = jsonObject.getJSONArray(ConfigCustomer.TAG_JSON_ARRAY);
            Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


            for (int i=0;i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString(ConfigCustomer.TAG_JSON_CST_ID);
                String name = object.getString(ConfigCustomer.TAG_JSON_CST_NAME);
                String phone = object.getString(ConfigCustomer.TAG_JSON_CST_HP );
                String address = object.getString(ConfigCustomer.TAG_JSON_CST_ALAMAT);

                HashMap<String, String> customer = new HashMap<>();
                customer.put(ConfigCustomer.TAG_JSON_CST_ID, id);
                customer.put(ConfigCustomer.TAG_JSON_CST_NAME, name);
                customer.put(ConfigCustomer.TAG_JSON_CST_HP, phone);
                customer.put(ConfigCustomer.TAG_JSON_CST_ALAMAT, address);

                arrayList.add(customer);
                Log.d("DataArr: ", String.valueOf(customer));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                viewGroup.getContext(), arrayList, R.layout.lv_customer,
                new String[] {ConfigCustomer.TAG_JSON_CST_NAME, ConfigCustomer.TAG_JSON_CST_HP, ConfigCustomer.TAG_JSON_CST_ALAMAT},
                new int[] {R.id.lv_customer_name, R.id.lv_customer_phone, R.id.lv_customer_address}
        );
        Log.d("DataArray: ", String.valueOf(adapter));
        listView.setAdapter(adapter);


//        lv_part.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Bundle bundle = new Bundle();
//                CompanyDetailFragment companyDetailFragment = new CompanyDetailFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.framelayout,companyDetailFragment);
//
//
//                HashMap<String, String> map = (HashMap) adapterView.getItemAtPosition(i);
//                String companyid = map.get(Config.TAG_JSON_ID_COMPANY).toString();
//                Bundle args = new Bundle();
//                args.putString("id", companyid);
//                companyDetailFragment.setArguments(args);
//
//
//
//                Log.d("Par: ", String.valueOf(args));
//                fragmentTransaction.commit();
//            }
//        });
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