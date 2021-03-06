package com.maybank.kprandroid.Manager;

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
import com.maybank.kprandroid.Configuration.ConfigManager;
import com.maybank.kprandroid.Customer.DetailCustomerFragment;
import com.maybank.kprandroid.Document.DetailDocumentFragment;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerFragment extends Fragment {

    private String JSON_STRING;
    private ViewGroup viewGroup;
    private ListView listView;
    String id_emp;
    EditText search;
    ArrayAdapter<String> adapter1, adapter2;
    String a = null;
    String b;
    String[] c;

    public ManagerFragment() {
        // Required empty public constructor
    }

    public static ManagerFragment newInstance(String param1, String param2) {
        ManagerFragment fragment = new ManagerFragment();
        Bundle args = new Bundle();
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_manager, container, false);
        search = viewGroup.findViewById(R.id.custAllSearch);

        listView = viewGroup.findViewById(R.id.lv_manager);

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
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
        ArrayList<String> arrayList1 = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(ConfigManager.TAG_JSON_ARRAY);
            Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


            for (int i=0;i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString(ConfigManager.TAG_JSON_MNG_NSB_ID);
                String name = object.getString(ConfigManager.TAG_JSON_MNG_NAME) +
                        "\n\n" + object.getString(ConfigManager.TAG_JSON_MNG_NAME_EMP) +
                        "\n\n" + object.getString(ConfigManager.TAG_JSON_MNG_NSB_ID);
                String emp = object.getString(ConfigManager.TAG_JSON_MNG_NAME_EMP);

                HashMap<String, String> customer = new HashMap<>();
                customer.put(ConfigManager.TAG_JSON_MNG_NSB_ID, id);
                customer.put(ConfigManager.TAG_JSON_MNG_NAME, name);
                customer.put(ConfigManager.TAG_JSON_MNG_NAME_EMP, emp);

                arrayList.add(customer);
                arrayList1.add(name);
                arrayList2.add(id);
                Log.d("DataArr: ", String.valueOf(customer));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        adapter1 = new ArrayAdapter(getContext(), R.layout.lv_approval, R.id.lv_approve_nsb, arrayList1);
//        ListAdapter adapter = new SimpleAdapter(
//                viewGroup.getContext(), arrayList, R.layout.lv_approval,
//                new String[] {ConfigManager.TAG_JSON_MNG_NAME, ConfigManager.TAG_JSON_MNG_NAME_EMP},
//                new int[] {R.id.lv_approve_nsb, R.id.lv_approve_emp}
//        );
        Log.d("DataArray: ", String.valueOf(adapter1));
        listView.setAdapter(adapter1);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                DetailDocumentFragment detailDocumentFragment = new DetailDocumentFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,detailDocumentFragment);
                callFragment(detailDocumentFragment);


//                HashMap<String, String> map = (HashMap) adapterView.getItemAtPosition(i);
//                String nsbid = map.get(ConfigManager.TAG_JSON_MNG_NSB_ID).toString();
                if (a == null) {
                    a = arrayList2.get(i);
                }

                b = adapter1.getItem(i);
                c = b.split("\n\n");
                a = String.valueOf(c[2]);
                Bundle args = new Bundle();
                args.putString("id_nsb", a);
                detailDocumentFragment.setArguments(args);

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