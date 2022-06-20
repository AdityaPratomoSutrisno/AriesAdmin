package com.aditya.ariesadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aditya.ariesadmin.Utils.DBHelper;
import com.aditya.ariesadmin.Utils.PopupUtils;
import com.google.android.material.textfield.TextInputEditText;

public class PasswordBaruActivity extends AppCompatActivity {

    DBHelper dbHelper;
    PopupUtils popupUtils;

    TextInputEditText tietPassword,tietKonfirmasiPassword;
    LinearLayout llPassword,llKonfirmasiPassword;
    Button btnKonfimasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_baru);

        View toastsucces = getLayoutInflater().inflate(R.layout.toast_success, null);
        View toastinfo = getLayoutInflater().inflate(R.layout.toast_information, null);
        View toastfail = getLayoutInflater().inflate(R.layout.toast_failed, null);

        popupUtils = new PopupUtils(this, toastsucces, toastinfo, toastfail);
        dbHelper = new DBHelper(this);

        tietPassword = findViewById(R.id.tietPassword);
        tietKonfirmasiPassword = findViewById(R.id.tietKonfirmasiPassword);
        llPassword = findViewById(R.id.llPassword);
        llKonfirmasiPassword = findViewById(R.id.llKonfirmasiPassword);
        btnKonfimasi = findViewById(R.id.btnKonfimasi);

        btnKonfimasi.setOnClickListener(view -> {
            if(CekPassword() && CekPasswordKonfirmasi()){
                long status = dbHelper.U_Password_Admin(""+tietPassword.getText());
                if(status != -1){
                    popupUtils.showtoastsucces("Berhasil merubah password");
                    Intent i = new Intent(this,LoginActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    popupUtils.showtoastfailed("Gagal merubah password, silahkan cek data anda");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

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

    private boolean CekPasswordKonfirmasi(){
        String Pass = tietPassword.getText().toString().trim();
        String PassKon = tietKonfirmasiPassword.getText().toString().trim();

        if (PassKon.isEmpty()) {
            tietKonfirmasiPassword.setError("Password tidak boleh kosong");
            llKonfirmasiPassword.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        } else if(!PassKon.equals(Pass)){
            tietKonfirmasiPassword.setError("Password harus sama");
            llKonfirmasiPassword.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            tietKonfirmasiPassword.setError(null);
            llKonfirmasiPassword.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
        }
        return true;
    }
}