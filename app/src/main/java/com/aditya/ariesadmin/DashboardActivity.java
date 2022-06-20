package com.aditya.ariesadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aditya.ariesadmin.Utils.DBHelper;
import com.aditya.ariesadmin.Utils.PopupUtils;
import com.aditya.ariesadmin.Utils.SharedPreferences;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    SharedPreferences pref;
    DBHelper dbHelper;
    PopupUtils popupUtils;

    LinearLayout llTambahIuran,llLihatIuran,llTambahKK,llLihatKK;
    ImageView ivLogOut;
    TextView tvTitleIuran,tvQtyIuran,tvQtyKK,tvTotalQtyIuran,tvNamaAdmin;
    NumberFormat formatRupiah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        View toastsucces = getLayoutInflater().inflate(R.layout.toast_success, null);
        View toastinfo = getLayoutInflater().inflate(R.layout.toast_information, null);
        View toastfail = getLayoutInflater().inflate(R.layout.toast_failed, null);

        popupUtils = new PopupUtils(this, toastsucces, toastinfo, toastfail);
        pref = new SharedPreferences(this);
        dbHelper = new DBHelper(this);
        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        formatRupiah.setMaximumFractionDigits(0);

        llTambahIuran = findViewById(R.id.llTambahIuran);
        llLihatIuran = findViewById(R.id.llLihatIuran);
        llTambahKK = findViewById(R.id.llTambahKK);
        llLihatKK = findViewById(R.id.llLihatKK);
        ivLogOut = findViewById(R.id.ivLogOut);
        tvTitleIuran = findViewById(R.id.tvTitleIuran);
        tvQtyIuran = findViewById(R.id.tvQtyIuran);
        tvQtyKK = findViewById(R.id.tvQtyKK);
        tvTotalQtyIuran = findViewById(R.id.tvTotalQtyIuran);
        tvNamaAdmin = findViewById(R.id.tvNamaAdmin);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat monthFormat = new SimpleDateFormat("LLLL", new Locale("in", "ID"));
        String formattedDate = monthFormat.format(c);

        tvNamaAdmin.setText(""+dbHelper.Get_Admin());
        tvTitleIuran.setText("Iuran Sampah\n"+formattedDate);
        tvQtyKK.setText(dbHelper.Count_KK()+" KK");
        tvQtyIuran.setText(""+formatRupiah.format(dbHelper.Sum_Iuran(formattedDate)));
        tvTotalQtyIuran.setText(""+formatRupiah.format(dbHelper.Sum_Iuran()));

        llTambahIuran.setOnClickListener(view -> {
            if(dbHelper.Count_KK()==0){
                popupUtils.showtoastinfo("Pastikan anda sudah menambahkan data KK");
            }else {
                Intent i = new Intent(this,TambahIuranActivity.class);
                startActivity(i);
                finish();
            }
        });

        llLihatIuran.setOnClickListener(view -> {
            if(dbHelper.Count_Iuran()==0){
                popupUtils.showtoastinfo("Pastikan anda sudah menambahkan data Iuran");
            }else {
                Intent i = new Intent(this,DaftarIuranActivity.class);
                startActivity(i);
                finish();
            }
        });

        llTambahKK.setOnClickListener(view -> {
            Intent i = new Intent(this,TambahDataKKActivity.class);
            startActivity(i);
            finish();
        });

        llLihatKK.setOnClickListener(view -> {
            if(dbHelper.Count_KK()==0){
                popupUtils.showtoastinfo("Pastikan anda sudah menambahkan data KK");
            }else {
                Intent i = new Intent(this,DaftarKKActivity.class);
                startActivity(i);
                finish();
            }
        });

        ivLogOut.setOnClickListener(view -> {
            pref.logout();

            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
            finish();
        });
    }
}