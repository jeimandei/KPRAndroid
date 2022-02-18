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
import android.widget.ListView;

import com.maybank.kprandroid.Configuration.ConfigCustomer;
import com.maybank.kprandroid.Configuration.ConfigManager;
import com.maybank.kprandroid.Customer.DetailCustomerFragment;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchCustManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchCustManagerFragment extends Fragment {

    ViewGroup viewGroup;
    String JSON_STRING;
    EditText search;
    ListView lv_custMansearch;
    ArrayAdapter<String> adapter1;
    String id_emp;

    public SearchCustManagerFragment() {
        // Required empty public constructor
    }

    public static SearchCustManagerFragment newInstance(String param1, String param2) {
        SearchCustManagerFragment fragment = new SearchCustManagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_search_cust_manger, container, false);

        id_emp = this.getArguments().getString("id_emp_1");
        search = viewGroup.findViewById(R.id.custManSearch);
        lv_custMansearch = viewGroup.findViewById(R.id.lv_search_man_customer);

        getJSON();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter1.getFilter().filter(charSequence);
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
                String result = handler.sendGetResp(ConfigManager.URL_GET_ALL_MANAGER);
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
        ArrayList<HashMap<String, String>> arrayList3 = new ArrayList<HashMap<String, String>>();
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList1 = new ArrayList<String>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(ConfigCustomer.TAG_JSON_ARRAY);
            Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


            for (int i=0;i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString(ConfigManager.TAG_JSON_MNG_NSB_ID);
                String name = object.getString(ConfigManager.TAG_JSON_MNG_NAME);
                String emp = object.getString(ConfigManager.TAG_JSON_MNG_NAME_EMP);

                HashMap<String, String> customer = new HashMap<>();
                customer.put(ConfigManager.TAG_JSON_MNG_NSB_ID, id);
                customer.put(ConfigManager.TAG_JSON_MNG_NAME, name);
                customer.put(ConfigManager.TAG_JSON_MNG_NAME_EMP, emp);

                arrayList.add(name);
                arrayList1.add(id);
                Log.d("DataArr: ", String.valueOf(name));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

//        adapter = new SimpleAdapter(
//                viewGroup.getContext(), arrayList3, R.layout.lv_search_cust,
//                new String[] {ConfigCustomer.TAG_JSON_CST_NAME, ConfigCustomer.TAG_JSON_CST_KTP},
//                new int[] {R.id.lv_search_customer_name, R.id.lv_search_customer_ktp}
//        );

        adapter1 = new ArrayAdapter(getContext(), R.layout.lv_search_cust_man, R.id.lv_search_customer_man_name, arrayList);

        Log.d("DataArray: ", String.valueOf(adapter1));
        lv_custMansearch.setAdapter(adapter1);

        lv_custMansearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DetailCustomerFragment detailCustomerFragment = new DetailCustomerFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,detailCustomerFragment);

                String a = arrayList1.get(i);
                Bundle args = new Bundle();
                args.putString("id", a);
                Log.d("idm: ", String.valueOf(a));
                args.putString("id_emp_1", id_emp);
                detailCustomerFragment.setArguments(args);
                fragmentTransaction.commit();
            }
        });

    }
}