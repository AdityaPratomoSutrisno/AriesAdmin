package com.aditya.ariesadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aditya.ariesadmin.Utils.DBHelper;
import com.aditya.ariesadmin.Utils.PopupUtils;

public class LupaPasswordActivity extends AppCompatActivity {

    DBHelper dbHelper;
    PopupUtils popupUtils;

    EditText etUsernameNIK;
    Button btnKonfirmasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        View toastsucces = getLayoutInflater().inflate(R.layout.toast_success, null);
        View toastinfo = getLayoutInflater().inflate(R.layout.toast_information, null);
        View toastfail = getLayoutInflater().inflate(R.layout.toast_failed, null);

        popupUtils = new PopupUtils(this, toastsucces, toastinfo, toastfail);
        dbHelper = new DBHelper(this);

        etUsernameNIK = findViewById(R.id.etUsernameNIK);
        btnKonfirmasi = findViewById(R.id.btnKonfirmasi);

        btnKonfirmasi.setOnClickListener(view -> {
            if(CekUsernameNIK()){
                long status = dbHelper.Count_Admin_Password(""+etUsernameNIK.getText());

                if(status == 1){
                    Intent i = new Intent(this,PasswordBaruActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    popupUtils.showtoastfailed("Data tidak ditemukan");
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

    private boolean CekUsernameNIK(){
        String Nama = etUsernameNIK.getText().toString().trim();

        if (Nama.isEmpty()) {
            etUsernameNIK.setError("Field tidak boleh kosong");
            etUsernameNIK.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            etUsernameNIK.setError(null);
            etUsernameNIK.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
        }
        return true;
    }
}