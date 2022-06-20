package com.aditya.ariesadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aditya.ariesadmin.Adapter.KKAdapter;
import com.aditya.ariesadmin.Utils.DBHelper;
import com.aditya.ariesadmin.Utils.PopupUtils;
import com.aditya.ariesadmin.Utils.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DaftarKKActivity extends AppCompatActivity {

    DBHelper dbHelper;
    PopupUtils popupUtils;

    ImageView ivBack;
    KKAdapter kkAdapter;
    RecyclerView rvDaftarKK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_k_k);

        View toastsucces = getLayoutInflater().inflate(R.layout.toast_success, null);
        View toastinfo = getLayoutInflater().inflate(R.layout.toast_information, null);
        View toastfail = getLayoutInflater().inflate(R.layout.toast_failed, null);

        popupUtils = new PopupUtils(this, toastsucces, toastinfo, toastfail);
        dbHelper = new DBHelper(this);

        ivBack = findViewById(R.id.ivBack);
        rvDaftarKK = findViewById(R.id.rvDaftarKK);

        kkAdapter = new KKAdapter(this,dbHelper.KK_Data());
        RecyclerView.LayoutManager mLayoutManagerPopuler = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rvDaftarKK.setLayoutManager(mLayoutManagerPopuler);
        rvDaftarKK.setAdapter(kkAdapter);

        ivBack.setOnClickListener(view -> {
            Dashboard();
        });
    }

    @Override
    public void onBackPressed() {
        Dashboard();
    }

    public void Dashboard(){
        Intent i = new Intent(this,DashboardActivity.class);
        startActivity(i);
        finish();
    }
}