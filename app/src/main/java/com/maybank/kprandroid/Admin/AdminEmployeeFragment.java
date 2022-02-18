package com.maybank.kprandroid.Admin;

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
import com.maybank.kprandroid.Configuration.ConfigAdmin;
import com.maybank.kprandroid.Configuration.ConfigCustomer;
import com.maybank.kprandroid.Customer.AddCustomerFragment;
import com.maybank.kprandroid.Customer.DetailCustomerFragment;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminEmployeeFragment extends Fragment {

    private String JSON_STRING;
    private ViewGroup viewGroup;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    ArrayAdapter<String> adapter1;
    String id_emp;
    EditText search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_admin_employee, container, false);

        floatingActionButton = viewGroup.findViewById(R.id.employee_add);
        listView = viewGroup.findViewById(R.id.lv_employee);
        search = viewGroup.findViewById(R.id.empSearch);

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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEmployeeFragment addEmployeeFragment = new AddEmployeeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,addEmployeeFragment);
                callFragment(addEmployeeFragment);
                fragmentTransaction.commit();
            }
        });

        return viewGroup;
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
                String result = handler.sendGetResp(ConfigAdmin.URL_GET_ALL_EMPLOYEE);
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
        ArrayList<String> arrayList1 = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();


        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(ConfigAdmin.TAG_JSON_ARRAY);
            Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


            for (int i=0;i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString(ConfigAdmin.TAG_JSON_EMP_ID);
                String role = object.getString(ConfigAdmin.TAG_JSON_EMP_ROLE );
                String name = object.getString(ConfigAdmin.TAG_JSON_EMP_NAME) + "\n\n"
                        + role;


                //String pass = object.getString(ConfigAdmin.TAG_JSON_EMP_PASS);

                HashMap<String, String> customer = new HashMap<>();
                customer.put(ConfigAdmin.TAG_JSON_EMP_ID, id);
                customer.put(ConfigAdmin.TAG_JSON_EMP_NAME, name);
                customer.put(ConfigAdmin.TAG_JSON_EMP_ROLE, role);
                //customer.put(ConfigAdmin.TAG_JSON_EMP_PASS, pass);
                arrayList1.add(name);
                arrayList2.add(id);
                Log.d("DataArr: ", String.valueOf(customer));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

//        ListAdapter adapter = new SimpleAdapter(
//                viewGroup.getContext(), arrayList, R.layout.lv_employee,
//                new String[] {ConfigAdmin.TAG_JSON_EMP_NAME, ConfigAdmin.TAG_JSON_EMP_ROLE},
//                new int[] {R.id.lv_employee_name, R.id.lv_employee_role}
//        );

        adapter1 = new ArrayAdapter(getContext(), R.layout.lv_employee, R.id.lv_employee_name, arrayList1);
        Log.d("DataArray: ", String.valueOf(adapter1));
        listView.setAdapter(adapter1);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AdminDetailEmployeeFragment adminDetailEmployeeFragment = new AdminDetailEmployeeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,adminDetailEmployeeFragment);


                //HashMap<String, String> map = (HashMap) adapterView.getItemAtPosition(i);
                //String empid = map.get(ConfigAdmin.TAG_JSON_EMP_ID).toString();
                String a = arrayList2.get(i);
                Bundle args = new Bundle();
                args.putString("id", a);
                adminDetailEmployeeFragment.setArguments(args);



                Log.d("Par: ", String.valueOf(args));
                fragmentTransaction.commit();
            }
        });
    }
}