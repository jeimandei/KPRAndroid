package com.maybank.kprandroid.Admin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maybank.kprandroid.Configuration.ConfigAdmin;
import com.maybank.kprandroid.Configuration.ConfigCustomer;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AdminDetailEmployeeFragment extends Fragment {

    private String JSON_STRING, id_emp;
    private ViewGroup viewGroup;
    private Button update, delete;
    private EditText edit_nama_emp, edit_confpass_emp, edit_pass_emp;
    Spinner edit_role_emp;
    private ListView listView;
    boolean isAllFieldsChecked = false;
    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_admin_detail_employee, container, false);
        id_emp = this.getArguments().getString("id");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Employee Details");
        edit_nama_emp = viewGroup.findViewById(R.id.edit_nama_emp);
        edit_role_emp = viewGroup.findViewById(R.id.edit_spinner_emp_role);
        edit_confpass_emp = viewGroup.findViewById(R.id.edit_pass_conf);
        edit_pass_emp = viewGroup.findViewById(R.id.edit_pass_emp);

        String[] role = new String[]{"KPR", "MANAGER"};
        List<String> roleList = new ArrayList<>(Arrays.asList(role));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, roleList);
        edit_role_emp.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
                String result = handler.sendGetResp(ConfigAdmin.URL_GET_DETAIL_EMPLOYEE, id_emp);
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
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(ConfigAdmin.TAG_JSON_ARRAY);
            JSONObject object = jsonArray.getJSONObject(0);

            String name = object.getString(ConfigAdmin.TAG_JSON_EMP_NAME);
            String role = object.getString(ConfigAdmin.TAG_JSON_EMP_ROLE);

            edit_nama_emp.setText(name);

            if(role.equals("kpr")){
                edit_role_emp.setSelection(0, true);
            }else if(role.equals("manager")){
                edit_role_emp.setSelection(1, true);
            }

            Log.d("CekNama", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}