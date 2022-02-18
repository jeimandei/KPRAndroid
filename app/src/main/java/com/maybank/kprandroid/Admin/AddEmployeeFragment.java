package com.maybank.kprandroid.Admin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maybank.kprandroid.Configuration.ConfigAdmin;
import com.maybank.kprandroid.Configuration.ConfigCustomer;
import com.maybank.kprandroid.Customer.CustomerFragment;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AddEmployeeFragment extends Fragment implements View.OnClickListener {

    private String JSON_STRING, id_emp;
    private ViewGroup viewGroup;
    private Button tambah_emp;
    private EditText tambah_nama_emp, tambah_confpass_emp, tambah_pass_emp;
    Spinner tambah_role_emp;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    final Calendar calendar = Calendar.getInstance();
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_add_employee, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Employee");
        tambah_nama_emp = viewGroup.findViewById(R.id.tambah_nama_emp);
        tambah_role_emp = viewGroup.findViewById(R.id.spinner_emp_role);
        tambah_confpass_emp = viewGroup.findViewById(R.id.tambah_pass_conf);
        tambah_pass_emp = viewGroup.findViewById(R.id.tambah_pass_emp);

        tambah_emp = viewGroup.findViewById(R.id.btn_tambah_emp);

        String[] role = new String[]{"KPR", "MANAGER"};
        List<String>  roleList = new ArrayList<>(Arrays.asList(role));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, roleList);
        tambah_role_emp.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tambah_emp.setOnClickListener(this);

        return viewGroup;
    }


    @Override
    public void onClick(View view) {
//        if (view == tambah_emp){
//            isAllFieldsChecked = CheckAllFields();
//        }
        addEmployee();
    }

//    private boolean CheckAllFields() {
//        if (message.length() == 0) {
//            message.setError("This field is required");
//            return false;
//        } else {
//            confirmAdd();
//        }
//
//        // after all validation return true.
//        return true;
//    }

    private void addEmployee() {

        String nama_emp = tambah_nama_emp.getText().toString().trim();
        String role_emp = tambah_role_emp.getSelectedItem().toString().toLowerCase();
        String pass_emp = tambah_confpass_emp.getText().toString().trim();


        class AddCustomer extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Saving Data", "Please Wait...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(ConfigAdmin.KEY_EMP_NAME, nama_emp);
                params.put(ConfigAdmin.KEY_EMP_ROLE, role_emp);
                params.put(ConfigAdmin.KEY_EMP_PASS, pass_emp);
                Log.d("cost: ", String.valueOf(params));

                HttpHandler handler = new HttpHandler();
                String res = handler.sendPostReq(ConfigAdmin.URL_ADD_EMPLOYEE, params);
                return res;
            }

            @Override
            protected void onPostExecute(String messsage) {
                super.onPostExecute(messsage);
                loading.dismiss();
//                Toast.makeText(getContext(), messsage, Toast.LENGTH_LONG).show();
//                Log.d("m:", messsage);
                //clearText();

//                if (messsage.equals("Berhasil Menambahkan Data Nasabah")){
//                    showAlertDialog(R.layout.alert_succes);
//                } else {
//                    showAlertDialog(R.layout.alert_field);
//                }

                AdminEmployeeFragment adminEmployeeFragment = new AdminEmployeeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, adminEmployeeFragment);
                fragmentTransaction.commit();

            }
        }

        AddCustomer addCustomer = new AddCustomer();
        addCustomer.execute();
    }
}