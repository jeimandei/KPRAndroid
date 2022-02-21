package com.maybank.kprandroid.Admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.maybank.kprandroid.Configuration.ConfigAdmin;
import com.maybank.kprandroid.Configuration.ConfigCustomer;
import com.maybank.kprandroid.Configuration.ConfigSchedule;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.R;
import com.maybank.kprandroid.Schedule.ScheduleFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class AdminDetailEmployeeFragment extends Fragment implements View.OnClickListener {

    private String role_save, id_emp;
    private ViewGroup viewGroup;
    private Button updateEmp, deleteEmp;
    private EditText edit_nama_emp, edit_confpass_emp, edit_pass_emp, edit_id;
    Spinner edit_role_emp;
    private ListView listView;
    boolean isAllFieldsChecked = false;
    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;
    private Switch showPass, showPassConf;


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
        edit_id = viewGroup.findViewById(R.id.edit_id_emp);

        showPass = viewGroup.findViewById(R.id.showPass);
        showPassConf = viewGroup.findViewById(R.id.showPass1);

        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPass.isChecked()) {
                    edit_pass_emp.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    edit_pass_emp.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        showPassConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPassConf.isChecked()) {
                    edit_confpass_emp.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edit_confpass_emp.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        updateEmp = viewGroup.findViewById(R.id.btn_update_emp);
        deleteEmp = viewGroup.findViewById(R.id.btn_delete_emp);

        String[] role = new String[]{"KPR", "MANAGER"};
        List<String> roleList = new ArrayList<>(Arrays.asList(role));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, roleList);
        edit_role_emp.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_role_emp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                role_save = String.valueOf(roleList.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        updateEmp.setOnClickListener(this);
        deleteEmp.setOnClickListener(this);

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
            edit_id.setText(id_emp);

            if (role.equals("kpr")) {
                edit_role_emp.setSelection(0, true);
            } else if (role.equals("manager")) {
                edit_role_emp.setSelection(1, true);
            }

            Log.d("CekNama", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == updateEmp) {
            isAllFieldsChecked = CheckAllFields();
//            confirmUpdate();
        } else {
            confirmDelete();
        }
    }

    // DELETE
    private void confirmDelete() {

        final String nama = edit_nama_emp.getText().toString().trim();
        final String role1 = String.valueOf(role_save);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        alertDialogBuilder.setMessage("Are you sure want to delete this data? " +
                "\n Name\t\t\t:  " + nama +
                "\n Role\t\t\t\t\t:  " + role1);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteEmpSys();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Tidak ngapa-ngapain
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void deleteEmpSys() {

        class DeleteEmp extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),
                        "Updating...", "Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String s = handler.sendGetResp(ConfigAdmin.URL_DELETE_EMPLOYEE, id_emp);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if (s.equals("Berhasil Menghapus Pegawai")) {
                    showAlertDialog(R.layout.alert_field);
                } else {
                    showAlertDialog(R.layout.alert_delete);
                }

                AdminEmployeeFragment adminEmployeeFragment = new AdminEmployeeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, adminEmployeeFragment);
//                Bundle arg = new Bundle();
//                arg.putString("id_emp_1", id_emp);
//                adminEmployeeFragment.setArguments(arg);
                fragmentTransaction.commit();

            }

        }

        DeleteEmp deleteEmp = new DeleteEmp();
        deleteEmp.execute();
    }

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");


    // UPDATE
    private boolean CheckAllFields() {

        String pass = edit_pass_emp.getText().toString();
        String passConf = edit_confpass_emp.getText().toString();

        if (edit_pass_emp.length() == 0) {
            edit_pass_emp.setError("Please insert this field");
            return false;
        } else if (edit_pass_emp.length() < 8) {
            edit_pass_emp.setError("Password minimum 8 characters");
            return false;
        } else if (edit_pass_emp.equals(edit_confpass_emp)) {
            Toast.makeText(getContext(), "Password matches", Toast.LENGTH_SHORT).show();
            TextInputLayout cekLayout = viewGroup.findViewById(R.id.edit_pass_conf);
            cekLayout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
            cekLayout.setEndIconDrawable(R.drawable.check);
            return false;
        } else if (edit_confpass_emp.length() == 0) {
            edit_confpass_emp.setError("Please confirm password ");
            return false;
        } else if (!passConf.equals(pass)) {
            edit_confpass_emp.setError("Password don't match");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(pass).matches()){
            edit_pass_emp.setError("Password is too weak");
            return false;
        } else {
            confirmUpdate();
        }
        // after all validation return true.
        return true;

    }

    private void confirmUpdate() {

        final String nama = edit_nama_emp.getText().toString().trim();
        final String role1 = String.valueOf(role_save);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        alertDialogBuilder.setMessage("Are you sure want to update this data? " +
                "\n Name\t\t\t:  " + nama +
                "\n Role\t\t\t\t\t:  " + role1
        );

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateEmpSys();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Tidak ngapa-ngapain
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void updateEmpSys() {

        final String name = edit_nama_emp.getText().toString().trim();
        final String role1 = String.valueOf(role_save);
        final String password = edit_pass_emp.getText().toString().trim();


        class UpdateSch extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),
                        "Updating...", "Wait...", false, false);

            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(ConfigAdmin.KEY_EMP_ID, id_emp);
                hashMap.put(ConfigAdmin.KEY_EMP_NAME, name);
                hashMap.put(ConfigAdmin.KEY_EMP_ROLE, role1);
                hashMap.put(ConfigAdmin.KEY_EMP_PASS, password);

                HttpHandler handler = new HttpHandler();
                String s = handler.sendPostReq(ConfigAdmin.URL_UPDATE_EMPLOYEE, hashMap);
                Log.d("Cek_Hasil", String.valueOf(hashMap));
                Log.d("Cek_Hasil", s);
                return s;
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if (s.equals("Berhasil Update Data Pegawai")) {
                    showAlertDialog(R.layout.alert_update);
                } else {
                    showAlertDialog(R.layout.alert_field);
                }

                AdminEmployeeFragment adminEmployeeFragment = new AdminEmployeeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, adminEmployeeFragment);
//                Bundle arg = new Bundle();
//                arg.putString("id_emp_1", id_emp);
//                adminEmployeeFragment.setArguments(arg);
                fragmentTransaction.commit();
            }
        }

        UpdateSch ue = new UpdateSch();
        ue.execute();

    }

    private void showAlertDialog(int alert_update) {


        builderDialog = new AlertDialog.Builder(getContext());
        View layoutView = getLayoutInflater().inflate(alert_update, null);

        AppCompatButton dialogButton = layoutView.findViewById(R.id.buttonOk);
        builderDialog.setView(layoutView);
        alertDialog = builderDialog.create();
        alertDialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

            }
        }, 4000);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}