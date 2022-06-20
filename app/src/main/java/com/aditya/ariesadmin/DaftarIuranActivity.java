package com.aditya.ariesadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aditya.ariesadmin.Adapter.IuranAdapter;
import com.aditya.ariesadmin.Adapter.KKAdapter;
import com.aditya.ariesadmin.Model.IuranModel;
import com.aditya.ariesadmin.Model.PeriodeModel;
import com.aditya.ariesadmin.Utils.DBHelper;
import com.aditya.ariesadmin.Utils.PopupUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DaftarIuranActivity extends AppCompatActivity {

    DBHelper dbHelper;
    PopupUtils popupUtils;

    ImageView ivBack;
    IuranAdapter iuranAdapter;
    RecyclerView rvDaftarIuran;
    Spinner spIuran;
    TextView tvDatakosong;

    List<PeriodeModel> periodeModels;
    String[] ID_Periode, Periode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_iuran);

        View toastsucces = getLayoutInflater().inflate(R.layout.toast_success, null);
        View toastinfo = getLayoutInflater().inflate(R.layout.toast_information, null);
        View toastfail = getLayoutInflater().inflate(R.layout.toast_failed, null);

        popupUtils = new PopupUtils(this, toastsucces, toastinfo, toastfail);
        dbHelper = new DBHelper(this);

        ivBack = findViewById(R.id.ivBack);
        rvDaftarIuran = findViewById(R.id.rvDaftarIuran);
        spIuran = findViewById(R.id.spIuran);
        tvDatakosong = findViewById(R.id.tvDatakosong);

        periodeModels = new ArrayList<>();
        periodeModels.clear();

        periodeModels = dbHelper.Periode_Data();

        ID_Periode = new String[periodeModels.size()];
        Periode = new String[periodeModels.size()];

        for (int i = 0; i< periodeModels.size();i++) {
            PeriodeModel km = periodeModels.get(i);

            ID_Periode[i] = km.getId_periode();
            Periode[i] = km.getPeriode();
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(DaftarIuranActivity.this, R.layout.item_spinner_12, Periode);
        spIuran.setAdapter(adapter2);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("L", new Locale("in", "ID"));
        String month = df.format(c);
        spIuran.setSelection(Integer.parseInt(month)-1);

        spIuran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Data(ID_Periode[position], Periode[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Data(month,Periode[Integer.parseInt(month)-1]);
            }
        });

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

    void Data(String month, String Bulan){
        List<IuranModel> iuranModels = new ArrayList<>();
        iuranModels.clear();

        iuranModels = dbHelper.Iuran_Data(month);

        if(iuranModels.size()==0){
            tvDatakosong.setVisibility(View.VISIBLE);
            rvDaftarIuran.setVisibility(View.GONE);
        }else {
            tvDatakosong.setVisibility(View.GONE);
            rvDaftarIuran.setVisibility(View.VISIBLE);
        }

        iuranAdapter = new IuranAdapter(this,iuranModels, Bulan);
        RecyclerView.LayoutManager mLayoutManagerPopuler = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rvDaftarIuran.setLayoutManager(mLayoutManagerPopuler);
        rvDaftarIuran.setAdapter(iuranAdapter);
    }
}