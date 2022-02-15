package com.maybank.kprandroid.Configuration;

public class ConfigSchedule {

    // url dimana web API berada
    public static final String URL_GET_ALL_SCHEDULE = "http://192.168.31.103/kpr/schedule/lihat_schedule.php";
    public static final String URL_GET_DETAIL_SCHEDULE = "http://127.0.0.1/training/peserta/tr_detail_peserta.php?id_pst=";
    public static final String URL_ADD_SCHEDULE = "http://127.0.0.1/training/peserta/tr_add_peserta.php";
    public static final String URL_UPDATE_SCHEDULE = "http://127.0.0.1/training/peserta/tr_update_peserta.php";
    public static final String URL_DELETE_SCHEDULE = "http://127.0.0.1/training/peserta/tr_delete_peserta.php?id_pst=";

    // key and value JSON yang muncul di browser
    public static final String KEY_SCH_ID = "id_jantem";
    public static final String KEY_SCH_ID_NSB = "id_nsb";
    public static final String KEY_SCH_PESAN = "pesan_jantem";
    public static final String KEY_SCH_TGL = "tgl_jantem";

    // flag JSON
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_SCH_ID = "id_jantem";
    public static final String TAG_JSON_SCH_ID_NSB = "id_nsb";
    public static final String TAG_JSON_SCH_NAMA_NSB = "nama_nsb";
    public static final String TAG_JSON_SCH_PESAN = "pesan_jantem";
    public static final String TAG_JSON_SCH_TGL = "tgl_jantem";

    // variabel ID peserta
    public static final String SCH_ID = "id_jantem";
}
