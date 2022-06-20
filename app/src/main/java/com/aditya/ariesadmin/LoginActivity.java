package com.aditya.ariesadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aditya.ariesadmin.Utils.DBHelper;
import com.aditya.ariesadmin.Utils.PopupUtils;
import com.aditya.ariesadmin.Utils.SharedPreferences;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    DBHelper dbHelper;
    PopupUtils popupUtils;
    SharedPreferences pref;

    EditText etUserName;
    TextInputEditText tietPassword;
    Button btnLogin;
    TextView tvRegister,tvLupaPasswordLogin;
    LinearLayout llDaftarAkun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View toastsucces = getLayoutInflater().inflate(R.layout.toast_success, null);
        View toastinfo = getLayoutInflater().inflate(R.layout.toast_information, null);
        View toastfail = getLayoutInflater().inflate(R.layout.toast_failed, null);

        popupUtils = new PopupUtils(this, toastsucces, toastinfo, toastfail);
        pref = new SharedPreferences(this);
        dbHelper = new DBHelper(this);

        if (pref.isLoggedIn()){
            startActivity(new Intent(this, DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        Log.i("ISI LOG"," == "+pref.isLoggedIn());

        etUserName = findViewById(R.id.etUserName);
        tietPassword = findViewById(R.id.tietPassword);
        tvRegister = findViewById(R.id.tvRegister);
        tvLupaPasswordLogin = findViewById(R.id.tvLupaPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        llDaftarAkun = findViewById(R.id.llDaftarAkun);

        if(dbHelper.Count_Admin()!=0){
            llDaftarAkun.setVisibility(View.GONE);
        }

        tvLupaPasswordLogin.setOnClickListener(view -> {
            Intent i = new Intent(this,LupaPasswordActivity.class);
            startActivity(i);
            finish();
        });

        btnLogin.setOnClickListener(view -> {
            if(dbHelper.Count_Admin()!=0){
                if(CekUsername() && CekPassword()){
                    long status = dbHelper.Login_Admin(""+etUserName.getText(), ""+tietPassword.getText());

                    if(status == 1){
                        pref.createSessionLogin();

                        Intent i = new Intent(this,DashboardActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        popupUtils.showtoastfailed("Username atau Password tidak sesuai");
                    }
                }
            }else {
                popupUtils.showtoastfailed("Pastikan anda sudah mendaftarkan akun anda");
            }


        });

        tvRegister.setOnClickListener(view -> {
            Intent i = new Intent(this,SignUpActivity.class);
            startActivity(i);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        finish();
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
            tietPassword.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            etUserName.setError(null);
            etUserName.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
        }
        return true;
    }
}