package com.maybank.kprandroid.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;

import com.maybank.kprandroid.Configuration.ConfigLogin;
import com.maybank.kprandroid.HttpHandler;
import com.maybank.kprandroid.MainActivity;
import com.maybank.kprandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText id_emp, password;
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        id_emp = findViewById(R.id.id_emp);
        password = findViewById(R.id.password);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            }
//        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();
            }
        });

    }

    private boolean CheckAllFields() {


        if (id_emp.length() == 0) {
            id_emp.setError("This field is required");
            return false;
        }else if (password.length() == 0){
            password.setError("This field is required");
            return false;
        } else{
            validasi();

        }

        // after all validation return true.
        return true;

    }

    private void validasi() {

        getJsonData();

    }

    private void getJsonData() {

        final String id = id_emp.getText().toString().trim();
        final String pass = password.getText().toString().trim();

        class GetJson extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            // ctrl + o pilih OnPreExcetue

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this,
                        "Mengambil Data", "Harap Menunggu",
                        false, false);
            }

            // Saat proses ambil data terjadi

            @Override
            protected String doInBackground(Void... voids) {

                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResp2(ConfigLogin.URL_GET_LOGIN, id, pass );
                return result;

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                displayDetailLogin(s);
            }

        }

        GetJson getJson= new GetJson();
        getJson.execute();

    }

    private void displayDetailLogin(String s) {


        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray result = jsonObject.getJSONArray(ConfigLogin.TAG_JSON_ARRAY_LOGIN);
            JSONObject object = result.getJSONObject(0);
            Log.d("id_em:", String.valueOf(result));

            String id_emp = object.getString(ConfigLogin.TAG_LOGIN_EMP_ID);
            String role_emp = object.getString(ConfigLogin.TAG_LOGIN_EMP_ROLE);

            // SHARED PREFERENCED

            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("id_emp", id_emp);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class );
            intent.putExtra(ConfigLogin.TAG_LOGIN_EMP_ID, id_emp);
            intent.putExtra(ConfigLogin.TAG_LOGIN_EMP_ID, id_emp);
            intent.putExtra(ConfigLogin.TAG_LOGIN_EMP_ROLE, role_emp);
            startActivity(intent);

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }


}