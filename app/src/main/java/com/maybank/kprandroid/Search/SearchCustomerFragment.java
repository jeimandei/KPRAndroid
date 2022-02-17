package com.maybank.kprandroid.Search;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.maybank.kprandroid.Configuration.ConfigCustomer;
import com.maybank.kprandroid.Customer.DetailCustomerFragment;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchCustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchCustomerFragment extends Fragment {
    ViewGroup viewGroup;
    String JSON_STRING;
    EditText search;
    ListView lv_custsearch;
    ArrayAdapter<String> adapter;
    String id_emp;

    public SearchCustomerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchCustomerFragment newInstance(String param1, String param2) {
        SearchCustomerFragment fragment = new SearchCustomerFragment();
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_search_customer, container, false);

        id_emp = this.getArguments().getString("id_emp_1");
        search = viewGroup.findViewById(R.id.custSearch);
        lv_custsearch = viewGroup.findViewById(R.id.lv_search_customer);

        getJSON();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {

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

                displayAllParticipant();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayAllParticipant() {
        JSONObject jsonObject = null;
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<HashMap<String, String>> arrayList1 = new ArrayList<HashMap<String, String>>();

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

                arrayList.add(name);
                Log.d("DataArr: ", String.valueOf(name));
            }

        }catch (Exception e){
            e.printStackTrace();
        }



        adapter = new ArrayAdapter(getContext(), R.layout.lv_search_cust, R.id.lv_search_customer_name, arrayList);

        Log.d("DataArray: ", String.valueOf(adapter));
        lv_custsearch.setAdapter(adapter);

//        lv_custsearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                DetailCustomerFragment detailCustomerFragment = new DetailCustomerFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.framelayout,detailCustomerFragment);
//
//                HashMap<String, String> map = (HashMap) adapterView.getItemAtPosition(i);
//                String nsbid = map.get(ConfigCustomer.TAG_JSON_CST_ID).toString();
//                Bundle args = new Bundle();
//                args.putString("id", nsbid);
//                args.putString("id_emp_1", id_emp);
//                detailCustomerFragment.setArguments(args);
//            }
//        });


//        lv_custsearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                DetailCustomerFragment detailCustomerFragment = new DetailCustomerFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.framelayout,detailCustomerFragment);
//
//
//                ArrayList<String> map = (ArrayList<String>) adapterView.getItemAtPosition(i);
//                String nsbid = map.get(Integer.parseInt(ConfigCustomer.TAG_JSON_CST_ID)).toString();
//                Bundle args = new Bundle();
//                args.putString("id", nsbid);
//                args.putString("id_emp_1", id_emp);
//                detailCustomerFragment.setArguments(args);
//
//
//
//                Log.d("Par: ", String.valueOf(args));
//                fragmentTransaction.commit();
//            }
//        });
    }
}