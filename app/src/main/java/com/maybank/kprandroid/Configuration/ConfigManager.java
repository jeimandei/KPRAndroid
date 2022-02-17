package com.maybank.kprandroid.Configuration;

public class ConfigManager {
    public static final String IP = "https://jeimandei.com/";
    // url dimana web API berada
    public static final String URL_GET_ALL_MANAGER = IP + "kpr/approval/lihat_customer_all.php";
    public static final String URL_GET_DETAIL_CUSTOMER = IP + "kpr/customer/tampilCustomer.php?id_nsb=";
    public static final String URL_ADD_CUSTOMER = IP + "kpr/customer/tambahCustomer.php";
    public static final String URL_UPDATE_CUSTOMER = IP + "kpr/customer/updateCustomer.php";
    public static final String URL_DELETE_CUSTOMER = IP + "kpr/customer/hapusCustomer.php?id=";

    // key and value JSON yang muncul di browser
    public static final String KEY_CST_ID = "id_nsb";
    public static final String KEY_CST_NAME = "nama_nsb";
    public static final String KEY_CST_KTP = "ktp_nsb";
    public static final String KEY_CST_TMPT_LAHIR = "tmp_lahir_nsb";
    public static final String KEY_CST_TGL_LAHIR = "tgl_lahir_nsb";
    public static final String KEY_CST_ALAMAT = "alamat_nsb";
    public static final String KEY_CST_HP = "hp_nsb";
    public static final String KEY_CST_ID_EMP = "id_emp";

    // flag JSON
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_MNG_NSB_ID = "id_nsb";
    public static final String TAG_JSON_MNG_NAME = "nama_nsb";
    public static final String TAG_JSON_CST_KTP = "ktp_nsb";
    public static final String TAG_JSON_CST_HP = "hp_nsb";
    public static final String TAG_JSON_CST_TMPT_LAHIR = "tmpt_lahir_nsb";
    public static final String TAG_JSON_CST_TGL_LAHIR = "tgl_lahir_nsb";
    public static final String TAG_JSON_CST_ALAMAT = "alamat_nsb";
    public static final String TAG_JSON_MNG_NAME_EMP = "nama_emp";

    // variabel ID peserta
    public static final String NSB_ID = "id_nsb";
}
