package com.maybank.kprandroid.Configuration;

public class ConfigAdmin {
    public static final String IP = "https://jeimandei.com/";
    // url dimana web API berada
    public static final String URL_GET_ALL_EMPLOYEE = IP + "kpr/employee/tampilSemuaEmp.php";
    public static final String URL_GET_DETAIL_EMPLOYEE = IP + "kpr/employee/tampilEmp.php?id_emp=";
    public static final String URL_ADD_EMPLOYEE = IP + "kpr/employee/tambahEmp.php";
    public static final String URL_UPDATE_EMPLOYEE = IP + "kpr/employee/updateEmp.php?id_emp=";
    public static final String URL_DELETE_EMPLOYEE = IP + "kpr/employee/hapusEmp.php?id=";

    // key and value JSON yang muncul di browser
    public static final String KEY_EMP_ID = "id_emp";
    public static final String KEY_EMP_NAME = "nama_emp";
    public static final String KEY_EMP_ROLE = "role_emp";
    public static final String KEY_EMP_PASS = "pass_emp";


    // flag JSON
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_EMP_ID = "id_emp";
    public static final String TAG_JSON_EMP_NAME = "nama_emp";
    public static final String TAG_JSON_EMP_ROLE = "role_emp";
    public static final String TAG_JSON_EMP_PASS = "pass_emp";

    // variabel ID peserta
    public static final String EMP_ID = "id_emp";
}
