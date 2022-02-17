package com.maybank.kprandroid.Configuration;

public class ConfigSchedule {
    public static final String IP = "https://jeimandei.com/";

    // url dimana web API berada
    public static final String URL_GET_ALL_SCHEDULE = IP + "kpr/schedule/lihat_schedule.php?id_emp=";
    public static final String URL_GET_DETAIL_SCHEDULE = IP + "kpr/schedule/tampilSchedule.php?id_jantem=";
    public static final String URL_ADD_SCHEDULE = IP + "kpr/schedule/tambahSchedule.php";
    public static final String URL_UPDATE_SCHEDULE = IP + "kpr/schedule/updateSchedule.php";
    public static final String URL_DELETE_SCHEDULE = IP + "kpr/schedule/hapusSchedule.php?id_sch=";

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
