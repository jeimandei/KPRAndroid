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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
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
import java.util.regex.Pattern;

public class AddEmployeeFragment extends Fragment implements View.OnClickListener {

    private String role_save, id_emp;
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
    private Switch showPass, showPassConf;

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
        showPass = viewGroup.findViewById(R.id.showPass1);
        showPassConf = viewGroup.findViewById(R.id.showPass4);

        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPass.isChecked()) {
                    tambah_pass_emp.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    tambah_pass_emp.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        showPassConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPassConf.isChecked()) {
                    tambah_confpass_emp.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    tambah_confpass_emp.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        String[] role = new String[]{"KPR", "MANAGER"};
        List<String> roleList = new ArrayList<>(Arrays.asList(role));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, roleList);
        tambah_role_emp.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tambah_role_emp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                role_save = String.valueOf(roleList.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tambah_emp.setOnClickListener(this);

        return viewGroup;
    }

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");


    @Override
    public void onClick(View view) {
        if (view == tambah_emp) {
            isAllFieldsChecked = CheckAllFields();
        }
    }

    private boolean CheckAllFields() {

        String pass = tambah_pass_emp.getText().toString();
        String passConf = tambah_confpass_emp.getText().toString();

        if (tambah_nama_emp.length() == 0) {
            tambah_nama_emp.setError("Please insert this field");
            return false;
        } else if (tambah_pass_emp.length() < 8) {
            tambah_pass_emp.setError("Password minimum 8 characters");
            return false;
        } else if (tambah_confpass_emp.length() == 0) {
            tambah_confpass_emp.setError("Please confirm password ");
            return false;
        } else if (tambah_confpass_emp.length() == 0) {
            tambah_confpass_emp.setError("Please insert this field");
        } else if (!pass.equals(passConf)) {
            tambah_confpass_emp.setError("Password don't match");
        } else if (tambah_pass_emp.equals(tambah_confpass_emp)) {
            Toast.makeText(getContext(), "Password matches", Toast.LENGTH_SHORT).show();
            TextInputLayout cekLayout = viewGroup.findViewById(R.id.edit_pass_conf);
            cekLayout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
            cekLayout.setEndIconDrawable(R.drawable.check);
            return false;
        } else if (!PASSWORD_PATTERN.matcher(pass).matches())
        {
            tambah_pass_emp.setError("Password is too weak");
            return false;
        }   else
        {
            confirmAdd();
        }
        // after all validation return true.
        return true;

    }

    private void confirmAdd() {

        final String nama = tambah_nama_emp.getText().toString().trim();
        final String role1 = String.valueOf(role_save);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        alertDialogBuilder.setMessage("Are you sure want to add this data? " +
                "\n Employee Name\t: " + nama +
                "\n Role\t\t\t\t\t\t\t\t\t\t\t\t: " + role1
        );

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addEmployee();
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
////                Toast.makeText(getContext(), messsage, Toast.LENGTH_LONG).show();
////                Log.d("m:", messsage);
//                clearText();

                if (messsage.equals("Berhasil Menambahkan Data Pegawai")) {
                    showAlertDialog(R.layout.alert_field);
                } else {
                    showAlertDialog(R.layout.alert_succes);
                }

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

    private void showAlertDialog(int alert_succes) {

        builderDialog = new AlertDialog.Builder(getContext());
        View layoutView = getLayoutInflater().inflate(alert_succes, null);

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