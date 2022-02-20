package com.maybank.kprandroid.Customer;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maybank.kprandroid.Configuration.ConfigCustomer;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String JSON_STRING;
    private ViewGroup viewGroup;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    String id_emp;
    String a = null;
    String b;
    String[] c;
    ArrayAdapter<String> adapter;
    ListAdapter adapter1;
    EditText search;
    ArrayList<String> arrayList1;
    Bundle args = new Bundle();

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
        search = viewGroup.findViewById(R.id.custEMPSearch);

        id_emp = this.getArguments().getString("id_emp");
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

        getJSON();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCustomerFragment addCustomerFragment = new AddCustomerFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,addCustomerFragment);
                Bundle arg = new Bundle();
                arg.putString("id_emp_1", id_emp);
                addCustomerFragment.setArguments(arg);
                callFragment(addCustomerFragment);
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
        ArrayList<HashMap<String, String>> arrayList3 = new ArrayList<HashMap<String, String>>();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<String>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(ConfigCustomer.TAG_JSON_ARRAY);
            Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


            for (int i=0;i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString(ConfigCustomer.TAG_JSON_CST_ID);
                String name = object.getString(ConfigCustomer.TAG_JSON_CST_NAME) +
                        "\n\n" + object.getString(ConfigCustomer.TAG_JSON_CST_KTP) +
                        "\n\n" + object.getString(ConfigCustomer.TAG_JSON_CST_ID) ;

                String phone = object.getString(ConfigCustomer.TAG_JSON_CST_HP );
                String address = object.getString(ConfigCustomer.TAG_JSON_CST_ALAMAT);
                String ktp = object.getString(ConfigCustomer.TAG_JSON_CST_KTP);

                HashMap<String, String> customer = new HashMap<>();
                customer.put(ConfigCustomer.TAG_JSON_CST_ID, id);
                customer.put(ConfigCustomer.TAG_JSON_CST_NAME, name);
                customer.put(ConfigCustomer.TAG_JSON_CST_HP, phone);
                customer.put(ConfigCustomer.TAG_JSON_CST_ALAMAT, address);

                arrayList3.add(customer);
                arrayList.add(name);
                arrayList1.add(id);

                Log.d("DataArr: ", String.valueOf(customer));
                Log.d("DataArr: ", String.valueOf(arrayList1));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        adapter1 = new SimpleAdapter(
                viewGroup.getContext(), arrayList3, R.layout.lv_customer,
                new String[] {ConfigCustomer.TAG_JSON_CST_NAME, ConfigCustomer.TAG_JSON_CST_HP, ConfigCustomer.TAG_JSON_CST_ALAMAT},
                new int[] {R.id.lv_customer_name}
        );

        Log.d("DataArray: ", String.valueOf(adapter));

        adapter = new ArrayAdapter(getContext(), R.layout.lv_customer, R.id.lv_customer_name, arrayList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DetailCustomerFragment detailCustomerFragment = new DetailCustomerFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,detailCustomerFragment);


//                HashMap<String, String> map = (HashMap) adapter1.getItem(b);
//                String nsbid = map.get(ConfigCustomer.TAG_JSON_CST_ID).toString();


                if (a == null) {
                    a = arrayList1.get(i);
                }

                b = adapter.getItem(i);
                c = b.split("\n\n");
                a = String.valueOf(c[2]);

                args.putString("id", a);
                args.putString("id_emp_1", id_emp);
                detailCustomerFragment.setArguments(args);
                Log.d("post", String.valueOf(a));
                Log.d("post", String.valueOf(a));
                Log.d("idddd", String.valueOf(arrayList1));




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