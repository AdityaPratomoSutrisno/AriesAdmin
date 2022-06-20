package com.aditya.ariesadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.aditya.ariesadmin.Utils.DBHelper;
import com.aditya.ariesadmin.Utils.PopupUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    DBHelper dbHelper;
    PopupUtils popupUtils;

    EditText etNamaLengkap,etNIK,etUserName;
    TextInputEditText tietPassword;
    LinearLayout llPassword;
    Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        View toastsucces = getLayoutInflater().inflate(R.layout.toast_success, null);
        View toastinfo = getLayoutInflater().inflate(R.layout.toast_information, null);
        View toastfail = getLayoutInflater().inflate(R.layout.toast_failed, null);

        popupUtils = new PopupUtils(this, toastsucces, toastinfo, toastfail);
        dbHelper = new DBHelper(this);

        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etNIK = findViewById(R.id.etNIK);
        etUserName = findViewById(R.id.etUserName);
        tietPassword = findViewById(R.id.tietPassword);
        llPassword = findViewById(R.id.llPassword);
        btnDaftar = findViewById(R.id.btnDaftar);

        btnDaftar.setOnClickListener(view -> {
            if(CekNama() && CekNIK() && CekUsername() && CekPassword()){
                long status = dbHelper.I_Admin(""+etNamaLengkap.getText(),
                        ""+etNIK.getText(),
                        ""+etUserName.getText(),
                        ""+tietPassword.getText());
                if(status != -1){
                    popupUtils.showtoastsucces("Berhasil mendaftarkan akun");
                    Login();
                } else {
                    popupUtils.showtoastfailed("Gagal mendaftarkan akun, silahkan cek data anda");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Login();
    }

    public void Login(){
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    private boolean CekNama(){
        String Nama = etNamaLengkap.getText().toString().trim();

        if (Nama.isEmpty()) {
            etNamaLengkap.setError("Nama lengkap tidak boleh kosong");
            etNamaLengkap.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            etNamaLengkap.setError(null);
            etNamaLengkap.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
        }
        return true;
    }

    private boolean CekNIK(){
        String NIK = etNIK.getText().toString().trim();

        if (NIK.isEmpty()) {
            etNIK.setError("NIK tidak boleh kosong");
            etNIK.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            etNIK.setError(null);
            etNIK.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
        }
        return true;
    }

    private boolean CekUsername(){
        String Username = etUserName.getText().toString().trim();

        if (Username.isEmpty()) {
            etUserName.setError("Username tidak boleh kosong");
            etUserName.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            etUserName.setError(null);
            etUserName.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
        }
        return true;
    }

    private boolean CekPassword(){
        String Pass = tietPassword.getText().toString().trim();

        if (Pass.isEmpty()) {
            tietPassword.setError("Password tidak boleh kosong");
            llPassword.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        } else if(Pass.length()<8){
            tietPassword.setError("Password tidak boleh kurang dari 8 karakter");
            llPassword.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            tietPassword.setError(null);
            llPassword.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
        }
        return true;
    }
}