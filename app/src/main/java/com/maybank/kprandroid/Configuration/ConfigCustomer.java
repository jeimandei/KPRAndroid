package com.maybank.kprandroid.Configuration;

public class ConfigCustomer {
    // url dimana web API berada
    public static final String URL_GET_ALL_CUSTOMER = "http://192.168.30.26/kpr/customer/lihat_customer.php?id_emp=";
    public static final String URL_GET_DETAIL_CUSTOMER = "http://127.0.0.1/training/peserta/tr_detail_peserta.php?id_pst=";
    public static final String URL_ADD_CUSTOMER = "http://127.0.0.1/training/peserta/tr_add_peserta.php";
    public static final String URL_UPDATE_CUSTOMER = "http://127.0.0.1/training/peserta/tr_update_peserta.php";
    public static final String URL_DELETE_CUSTOMER = "http://127.0.0.1/training/peserta/tr_delete_peserta.php?id_pst=";

    // key and value JSON yang muncul di browser
    public static final String KEY_CST_ID = "id_nsb";
    public static final String KEY_CST_NAME = "nama_nsb";
    public static final String KEY_CST_KTP = "ktp_nsb";
    public static final String KEY_CST_TMPT_LAHIR = "tmpt_lahir_nsb";
    public static final String KEY_CST_TGL_LAHIR = "tgl_lahir_nsb";
    public static final String KEY_CST_ALAMAT = "alamat_nsb";
    public static final String KEY_CST_HP = "hp_nsb";
    public static final String KEY_CST_ID_EMP = "id_emp";

    // flag JSON
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_CST_ID = "id_nsb";
    public static final String TAG_JSON_CST_NAME = "nama_nsb";
    public static final String TAG_JSON_CST_KTP = "ktp_nsb";
    public static final String TAG_JSON_CST_HP = "hp_nsb";
    public static final String TAG_JSON_CST_TMPT_LAHIR = "tmpt_lahir_nsb";
    public static final String TAG_JSON_CST_TGL_LAHIR = "tgl_lahir_nsb";
    public static final String TAG_JSON_CST_ALAMAT = "alamat_nsb";
    public static final String TAG_JSON_CST_ID_EMP = "id_emp";

    // variabel ID peserta
    public static final String NSB_ID = "id_nsb";
}
