package com.aditya.ariesadmin.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import com.aditya.ariesadmin.Model.IuranModel;
import com.aditya.ariesadmin.Model.KKModel;
import com.aditya.ariesadmin.Model.PeriodeModel;
import com.aditya.ariesadmin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_Version = 1;
    public static final String DB_Name = "ariesadmin.db";
    String[] PERIODE = {"Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

    //Tabel Admin
    public static final String TB_Admin = "tb_admin";
    public static final String CL_AI = "auto_increment";
    public static final String CL_Nama_Lengkap = "nama_lengkap";
    public static final String CL_NIK = "nik";
    public static final String CL_Username = "username";
    public static final String CL_Password = "password";

    //Tabel Periode
    public static final String TB_Periode = "tb_periode";
    public static final String CL_ID_Periode = "id_periode";
    public static final String CL_Periode = "periode";

    //Tabel Iuran
    public static final String TB_Iuran = "tb_iuran";
    public static final String CL_ID_Iuran = "id_iuran";
    public static final String CL_ID_Warga_Iuran = "id_warga_iuran";
    public static final String CL_Nominal_Iuran = "nominal_iuran";
    public static final String CL_ID_Periode_Iuran = "id_periode_iuran";
    public static final String CL_Tanggal_Iuran = "tanggal_iuran";

    //Tabel KK
    public static final String TB_KK = "tb_kk";
    public static final String CL_ID_KK = "id_KK";
    public static final String CL_Nomor_Iuran = "nomor_iuran";
    public static final String CL_NIK_KK = "nik_kk";
    public static final String CL_Nama_Lengkap_KK = "nama_lengkap_kk";
    public static final String CL_Tempat_Lahir = "tempat_lahir";
    public static final String CL_Tanggal_Lahir = "tanggal_lahir";
    public static final String CL_JK = "jk";
    public static final String CL_Nomor_HP = "nomor_hp";
    public static final String CL_Alamat= "alamat";
    public static final String CL_Status= "status";

    //Tabel Relasi Warga Nonaktif
    public static final String TB_Warga_Off= "tb_warga_off";
    public static final String CL_ID_OFF = "id_off";
    public static final String CL_ID_Warga_Off = "id_warga_off";
    public static final String CL_ID_Periode_Off = "id_periode_off";

    public DBHelper(Context context){
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_Admin = "CREATE TABLE " + TB_Admin + " ( " +
                CL_AI + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CL_Nama_Lengkap + " TEXT NOT NULL, " +
                CL_NIK + " TEXT NOT NULL, " +
                CL_Username + " TEXT NOT NULL, " +
                CL_Password + " TEXT NOT NULL " +
                ")";
        db.execSQL(SQL_Admin);

        String SQL_Periode = "CREATE TABLE " + TB_Periode + " ( " +
                CL_ID_Periode + " INTEGER PRIMARY KEY, " +
                CL_Periode + " TEXT NOT NULL )";
        db.execSQL(SQL_Periode);

        for(int i=0;i<PERIODE.length;i++){
            ContentValues value = new ContentValues();
            value.put(CL_ID_Periode, ""+(i+1));
            value.put(CL_Periode, PERIODE[i]);

            db.insert(TB_Periode,null,value);
        }

        String SQL_Iuran = "CREATE TABLE " + TB_Iuran + " ( " +
                CL_ID_Iuran + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CL_ID_Warga_Iuran + " TEXT NOT NULL, " +
                CL_Nominal_Iuran + " INT NOT NULL, " +
                CL_ID_Periode_Iuran + " TEXT NOT NULL, " +
                CL_Tanggal_Iuran + " TEXT NOT NULL " +
                ")";
        db.execSQL(SQL_Iuran);

        String SQL_KK= "CREATE TABLE " + TB_KK + " ( " +
                CL_ID_KK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CL_Nomor_Iuran + " TEXT NOT NULL, " +
                CL_NIK_KK + " TEXT NOT NULL, " +
                CL_Nama_Lengkap_KK + " TEXT NOT NULL, " +
                CL_Tempat_Lahir + " TEXT NOT NULL, " +
                CL_Tanggal_Lahir + " TEXT NOT NULL, " +
                CL_JK + " TEXT NOT NULL, " +
                CL_Nomor_HP + " TEXT NOT NULL, " +
                CL_Alamat + " TEXT NOT NULL, " +
                CL_Status + " INTEGER NOT NULL DEFAULT 1" +
                ")";
        db.execSQL(SQL_KK);

//        String SQL_Warga_Off = "CREATE TABLE " + TB_Warga_Off + " ( " +
//                CL_ID_OFF + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                CL_ID_Warga_Off + " INTEGER NOT NULL, " +
//                CL_ID_Periode_Off + " TEXT NOT NULL )";
//        db.execSQL(SQL_Warga_Off);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+TB_Admin);
        db.execSQL("DROP TABLE IF EXISTS "+TB_Periode);
        db.execSQL("DROP TABLE IF EXISTS "+TB_Iuran);
        db.execSQL("DROP TABLE IF EXISTS "+TB_KK);
        onCreate(db);

    }

    public long I_Admin(String Nama_Lengkap, String NIK, String Username, String Password){
        SQLiteDatabase database = this.getWritableDatabase();

//        String insert_admin = "INSERT INTO " + TB_Admin + "( " +
//                CL_Nama_Lengkap +","+ CL_NIK +","+ CL_Username +","+ CL_Password+
//                ") VALUES('" +Nama_Lengkap + "', '"+ NIK +"', '"+ Username +"', '"+ Password +"')";
//
//        database.execSQL(insert_admin);
        ContentValues value = new ContentValues();
        value.put(CL_Nama_Lengkap, Nama_Lengkap);
        value.put(CL_NIK, NIK);
        value.put(CL_Username, Username);
        value.put(CL_Password, Password);

        long status = database.insert(TB_Admin,null,value);
        database.close();

        return status;
    }

    public int Count_Admin(){
        SQLiteDatabase database = this.getReadableDatabase();

        String selectSubPay = "SELECT COUNT(*) FROM "+TB_Admin;
        Cursor cursor = database.rawQuery(selectSubPay, null);

        try{
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }else {
                return  0;
            }
        }finally {
            database.close();
        }
    }

    public String Get_Admin(){
        SQLiteDatabase database = this.getReadableDatabase();

        String selectSubPay = "SELECT "+CL_Nama_Lengkap+" FROM "+TB_Admin;
        Cursor cursor = database.rawQuery(selectSubPay, null);

        try{
            if (cursor.moveToFirst()) {
                return cursor.getString(0);
            }else {
                return  "-";
            }
        }finally {
            database.close();
        }
    }

    public int Count_Admin_Password(String UsernameNIK){
        SQLiteDatabase database = this.getReadableDatabase();

        String selectSubPay = "SELECT COUNT(*) FROM "+TB_Admin+" WHERE " + CL_Username +" = '"+UsernameNIK+ "' or "+ CL_NIK +" = '"+UsernameNIK+ "'";
        Cursor cursor = database.rawQuery(selectSubPay, null);

        try{
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }else {
                return  0;
            }
        }finally {
            database.close();
        }
    }

    public long U_Password_Admin(String Password){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(CL_Password, Password);

        String where = CL_AI+"=?";
        String[] whereArgs = new String[] {"1"};
        long status = database.update(TB_Admin,value,where,whereArgs);
        database.close();

        return status;
    }

    public long I_KK(String Nomor_Iuran, String NIK, String Nama_Lengkap, String Tempat, String Tanggal, String JK, String Nomor_HP, String Alamat){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(CL_Nomor_Iuran, Nomor_Iuran);
        value.put(CL_NIK_KK, NIK);
        value.put(CL_Nama_Lengkap_KK, Nama_Lengkap);
        value.put(CL_Tempat_Lahir, Tempat);
        value.put(CL_Tanggal_Lahir, Tanggal);
        value.put(CL_JK, JK);
        value.put(CL_Nomor_HP, Nomor_HP);
        value.put(CL_Alamat, Alamat);

        long status = database.insert(TB_KK,null,value);
        database.close();

        return status;
    }

    public long U_KK(String ID_KK,String Nomor_Iuran,String NIK, String Nama_Lengkap, String Tempat, String Tanggal, String JK, String Nomor_HP, String Alamat, int Status){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(CL_Nomor_Iuran, Nomor_Iuran);
        value.put(CL_NIK_KK, NIK);
        value.put(CL_Nama_Lengkap_KK, Nama_Lengkap);
        value.put(CL_Tempat_Lahir, Tempat);
        value.put(CL_Tanggal_Lahir, Tanggal);
        value.put(CL_JK, JK);
        value.put(CL_Nomor_HP, Nomor_HP);
        value.put(CL_Alamat, Alamat);
        value.put(CL_Status, Status);

        String where = CL_ID_KK+"=?";
        String[] whereArgs = new String[] {String.valueOf(ID_KK)};
        long status = database.update(TB_KK,value,where,whereArgs);
        database.close();

        return status;
    }

    public int C_KK(String Nomor_Iuran, String NIK){
        SQLiteDatabase database = this.getReadableDatabase();

        String selectSubPay = "SELECT COUNT(*) FROM "+TB_KK+" WHERE " + CL_NIK_KK +" = '"+NIK+ "' and "+ CL_Nomor_Iuran +" = '"+Nomor_Iuran+ "'";
        Cursor cursor = database.rawQuery(selectSubPay, null);

        try{
            if (cursor.moveToFirst()) {
                Log.i("Data cursor"," == "+cursor.getInt(0));
                return cursor.getInt(0);
            }else {
                Log.i("Data null"," == null");
                return  0;
            }
        }finally {
            database.close();
        }
    }

    public List<KKModel> KK_Data(){
        List<KKModel> KK_Data = new ArrayList<>();
        KK_Data.clear();

        String selectQuery = "SELECT "+CL_ID_KK+", "+CL_Nomor_Iuran+", "+CL_Nama_Lengkap_KK+", "+CL_Alamat+" FROM " + TB_KK;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                KK_Data.add(new KKModel(""+cursor.getString(0),
                        ""+cursor.getString(1),
                        ""+cursor.getString(2),
                        ""+cursor.getString(3)));
            } while (cursor.moveToNext());
        }

        database.close();
        return KK_Data;
    }

    public List<KKModel> KK_Data_Spinner(){
        List<KKModel> KK_Data = new ArrayList<>();
        KK_Data.clear();

        String selectQuery = "SELECT "+CL_ID_KK+", "+CL_Nomor_Iuran+", "+CL_Nama_Lengkap_KK+", "+CL_Alamat+" FROM " + TB_KK+" where "+CL_Status+" = 1";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                KK_Data.add(new KKModel(""+cursor.getString(0),
                        ""+cursor.getString(1),
                        ""+cursor.getString(2),
                        ""+cursor.getString(3)));
            } while (cursor.moveToNext());
        }

        database.close();
        return KK_Data;
    }

    public List<KKModel> KK_Data_Detail(String ID_KK){
        List<KKModel> KK_Data = new ArrayList<>();
        KK_Data.clear();

        String selectQuery = "SELECT * FROM " + TB_KK + " Where "+CL_ID_KK+" ='"+ID_KK+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                KK_Data.add(new KKModel(""+cursor.getString(0),
                        ""+cursor.getString(1),
                        ""+cursor.getString(2),
                        ""+cursor.getString(3),
                        ""+cursor.getString(4),
                        ""+cursor.getString(5),
                        ""+cursor.getString(6),
                        ""+cursor.getString(7),
                        ""+cursor.getString(8),
                        cursor.getInt(9)));
            } while (cursor.moveToNext());
        }

        database.close();
        return KK_Data;
    }

    public int Count_KK(){
        SQLiteDatabase database = this.getReadableDatabase();

        String selectSubPay = "SELECT COUNT(*) FROM "+TB_KK+" where "+CL_Status+" = 1";
        Cursor cursor = database.rawQuery(selectSubPay, null);

        try{
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }else {
                return  0;
            }
        }finally {
            database.close();
        }
    }

    public long I_Iuran(String ID_Warga, String Nominal, String ID_Periode, String Tanggal){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(CL_ID_Warga_Iuran, ID_Warga);
        value.put(CL_Nominal_Iuran, Nominal);
        value.put(CL_ID_Periode_Iuran, ID_Periode);
        value.put(CL_Tanggal_Iuran, Tanggal);

        long status = database.insert(TB_Iuran,null,value);
        database.close();

        return status;
    }

    public int C_Iuran(String ID_Warga, String Periode){
        SQLiteDatabase database = this.getReadableDatabase();

        String selectSubPay = "SELECT COUNT(*) FROM "+TB_Iuran+" WHERE " + CL_ID_Warga_Iuran +" = '"+ID_Warga+ "' and "+ CL_ID_Periode_Iuran +" = '"+Periode+ "'";
        Cursor cursor = database.rawQuery(selectSubPay, null);

        try{
            if (cursor.moveToFirst()) {
                Log.i("Data cursor"," == "+cursor.getInt(0));
                return cursor.getInt(0);
            }else {
                Log.i("Data null"," == null");
                return  0;
            }
        }finally {
            database.close();
        }
    }

    public long U_Iuran(String ID_Iuran,String ID_Warga, String Nominal, String ID_Periode, String Tanggal){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(CL_ID_Warga_Iuran, ID_Warga);
        value.put(CL_Nominal_Iuran, Nominal);
        value.put(CL_ID_Periode_Iuran, ID_Periode);
        value.put(CL_Tanggal_Iuran, Tanggal);

        String where = CL_ID_Iuran+"=?";
        String[] whereArgs = new String[] {String.valueOf(ID_Iuran)};
        long status = database.update(TB_Iuran,value,where,whereArgs);
        database.close();

        return status;
    }

    public List<IuranModel> Iuran_Data(String ID_Periode){ //10.ID_Periode fungsinya ngecek id - id iurannya.
        SQLiteDatabase database = this.getWritableDatabase();
        List<IuranModel> Iuran_Data = new ArrayList<>(); //16.dikirimkan ke list iuran semuanya
        //jadi list iuran pertama itu jelas masuk ke orang yang nunggak
        //terus ditambah lagi list yang kedua itu orang yang sudah bayar
        //jadi warga yang belum bayar selalu diatas
        Iuran_Data.clear();
//Query menampilkan nunggak
        String nungggakquery = "SELECT "+CL_Nomor_Iuran+", "+CL_Nama_Lengkap_KK+", 'Belum Lunas', "+CL_Nomor_HP+" FROM " + TB_KK+
                //11.query ini ngambil cl_nomor_iuran, cl_nama_lengkap, nilai default(belum lunas), cl_nomor_hp, dari tabel KK
                " Where "+CL_ID_KK+ //12. dimana id kk itu
                " not in (SELECT "+CL_ID_Warga_Iuran+" FROM "+TB_Iuran+" WHERE "+CL_ID_Periode_Iuran+" = '"+ID_Periode+"') and "+CL_Status+" = 1";
                //13. yang tidak ada pada list yang sudah bayar, dan cl_status (switch aktif non)
                //jadi pada bagian nunggak ini mencocokan id kk yang tidak terdapat pada id yang sudah bayar
        Cursor cursornunggak = database.rawQuery(nungggakquery, null);
        if (cursornunggak.moveToFirst()){
            do {
                Iuran_Data.add(new IuranModel("",
                        ""+cursornunggak.getString(0),
                        ""+cursornunggak.getString(1),
                        ""+cursornunggak.getString(2),
                        "",
                        ""+cursornunggak.getString(3)));
            } while (cursornunggak.moveToNext());
        }
//14.query menampilkan sudah bayar
        String selectQuery = "SELECT "+CL_ID_Iuran+", "+CL_Nomor_Iuran+", "+CL_Nama_Lengkap_KK+", "+CL_Nominal_Iuran+", "+CL_Tanggal_Iuran+" FROM " + TB_Iuran+ ", "+TB_KK+
                " Where "+CL_ID_Periode_Iuran+" ='"+ID_Periode+"' and "+CL_ID_KK+" = "+CL_ID_Warga_Iuran;

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                Iuran_Data.add(new IuranModel(""+cursor.getString(0),
                        ""+cursor.getString(1),
                        ""+cursor.getString(2),
                        ""+cursor.getString(3),
                        ""+cursor.getString(4),
                        ""));
            } while (cursor.moveToNext());
        }

        database.close();
        return Iuran_Data; //15. nanti dikirim ke....
    }

    public List<IuranModel> Iuran_Data_Detail(String ID_Iuran){
        List<IuranModel> Iuran_Data = new ArrayList<>();
        Iuran_Data.clear();

        String selectQuery = "SELECT * FROM " + TB_Iuran+ " Where "+CL_ID_Iuran+" ='"+ID_Iuran+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                Iuran_Data.add(new IuranModel(""+cursor.getString(0),
                        ""+cursor.getString(1),
                        ""+cursor.getString(2),
                        ""+cursor.getString(3),
                        ""+cursor.getString(4)));
            } while (cursor.moveToNext());
        }

        database.close();
        return Iuran_Data;
    }

    public int Count_Iuran(){
        SQLiteDatabase database = this.getReadableDatabase();

        String selectSubPay = "SELECT COUNT(*) FROM "+TB_Iuran;
        Cursor cursor = database.rawQuery(selectSubPay, null);

        try{
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }else {
                return  0;
            }
        }finally {
            database.close();
        }
    }

    public int Sum_Iuran(){
        SQLiteDatabase database = this.getReadableDatabase();

        String selectSubPay = "SELECT SUM("+CL_Nominal_Iuran+") FROM "+TB_Iuran;
        Cursor cursor = database.rawQuery(selectSubPay, null);

        try{
            cursor.moveToFirst();
            return cursor.getInt(0);
        }finally {
            database.close();
        }
    }

    public int Sum_Iuran(String Month){
        SQLiteDatabase database = this.getReadableDatabase();

        String selectSubPay = "SELECT SUM("+CL_Nominal_Iuran+") FROM "+TB_Iuran+" Where "+
                CL_ID_Periode_Iuran +" = (SELECT "+CL_ID_Periode+" FROM "+TB_Periode+" Where "+CL_Periode+" = '"+Month+"')";
        Cursor cursor = database.rawQuery(selectSubPay, null);

        try{
            cursor.moveToFirst();
            return cursor.getInt(0);
        }finally {
            database.close();
        }
    }

    public int Login_Admin(String Username,String Password){
        SQLiteDatabase database = this.getReadableDatabase();

        String selectSubPay = "SELECT COUNT(*) FROM "+TB_Admin+" WHERE " + CL_Username +" = '"+Username + "' AND " + CL_Password + " = '" +Password +"'";
        Cursor cursor = database.rawQuery(selectSubPay, null);

        try{
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }else {
                return  0;
            }
        }finally {
            database.close();
        }
    }

    public String Check_Periode(String ID_Periode){
        SQLiteDatabase database = this.getReadableDatabase();

        String periode = "SELECT "+CL_Periode+" FROM "+TB_Periode+" WHERE " + CL_ID_Periode +" = '"+ID_Periode+"'";
        Cursor cursor = database.rawQuery(periode, null);
        try{
            if (cursor.moveToFirst()) {
                return cursor.getString(0);
            }else {
                return "";
            }
        }finally {
            database.close();
        }
    }

    public List<PeriodeModel> Periode_Data(){
        List<PeriodeModel> Periode_Data = new ArrayList<>();
        Periode_Data.clear();

        String selectQuery = "SELECT * FROM " + TB_Periode; // 2.ngambil semua data di TB_Periode
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                Periode_Data.add(new PeriodeModel(""+cursor.getString(0),
                        ""+cursor.getString(1))); //3.ngambil ini semua
            } while (cursor.moveToNext());
        }

        database.close();
        return Periode_Data;
    }

}
