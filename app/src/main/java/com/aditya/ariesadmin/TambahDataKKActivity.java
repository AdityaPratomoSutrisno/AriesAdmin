package com.aditya.ariesadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.aditya.ariesadmin.Model.KKModel;
import com.aditya.ariesadmin.Utils.DBHelper;
import com.aditya.ariesadmin.Utils.PopupUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TambahDataKKActivity extends AppCompatActivity {

    DBHelper dbHelper;
    PopupUtils popupUtils;

    ImageView ivBack;
    EditText etNomorIuran,etNIK,etNamaLengkap,etTempatLahir,etAlamat,etNomorHP;
    TextView tvTanggalLahir,tvTitleKK;
    RadioButton rbPerempuan,rbLakilaki;
    RadioGroup rgJenisKelamin;
    Button btnTambahKK,btnResetKK;
    LinearLayout llTanggalLahir,llNomorHP,llSwitchStatus;
    Switch swStatus;

    String ID_KK="";
    String JK="";
    String Tanggal_Lahir="";
    Calendar Tanggal;
    DatePickerDialog.OnDateSetListener date;
    int Status = 1;

    List<KKModel> kkModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_k_k);

        View toastsucces = getLayoutInflater().inflate(R.layout.toast_success, null);
        View toastinfo = getLayoutInflater().inflate(R.layout.toast_information, null);
        View toastfail = getLayoutInflater().inflate(R.layout.toast_failed, null);

        popupUtils = new PopupUtils(this, toastsucces, toastinfo, toastfail);
        dbHelper = new DBHelper(this);

        if(getIntent().getStringExtra("ID_KK")!=null){
            ID_KK = getIntent().getStringExtra("ID_KK");
            Log.i("ID_KK"," == "+ID_KK);
        }

        ivBack = findViewById(R.id.ivBack);
        etNomorIuran = findViewById(R.id.etNomorIuran);
        etNIK = findViewById(R.id.etNIK);
        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etTempatLahir = findViewById(R.id.etTempatLahir);
        tvTanggalLahir = findViewById(R.id.tvTanggalLahir);
        etAlamat = findViewById(R.id.etAlamat);
        etNomorHP = findViewById(R.id.etNomorHP);
        rbPerempuan = findViewById(R.id.rbPerempuan);
        rbLakilaki = findViewById(R.id.rbLakilaki);
        rgJenisKelamin = findViewById(R.id.rgJenisKelamin);
        btnTambahKK = findViewById(R.id.btnTambahKK);
        btnResetKK = findViewById(R.id.btnResetKK);
        llTanggalLahir = findViewById(R.id.llTanggalLahir);
        llNomorHP = findViewById(R.id.llNomorHP);
        tvTitleKK = findViewById(R.id.tvTitleKK);
        swStatus = findViewById(R.id.swStatus);
        llSwitchStatus = findViewById(R.id.llSwitchStatus);

        if(!ID_KK.equals("")){
            btnResetKK.setVisibility(View.VISIBLE);
            btnTambahKK.setText("Ubah");
            tvTitleKK.setText("Ubah Data KK");
            llSwitchStatus.setVisibility(View.VISIBLE);
            Set_Data();
        }

        Tanggal = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                Tanggal.set(Calendar.YEAR, year);
                Tanggal.set(Calendar.MONTH, monthOfYear);
                Tanggal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        tvTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Year = Tanggal.get(Calendar.YEAR);
                int Month = Tanggal.get(Calendar.MONTH);
                int Day = Tanggal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(TambahDataKKActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,date,Year,Month,Day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        rbPerempuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    JK = "P";
                }
            }
        });

        rbLakilaki.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    JK = "L";
                }
            }
        });

        ivBack.setOnClickListener(view -> {
            Dashboard();
        });

        swStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Status = 1;
                    swStatus.setText("Aktif");
                }else {
                    Status = 0;
                    swStatus.setText("Nonaktif");
                }
            }
        });

        btnResetKK.setOnClickListener(view -> {
            Daftar();
        });

        btnTambahKK.setOnClickListener(view -> {
            if(CekNomorIuran() && CekNIK() && CekNama() && CekTL() && CekTanggal() && CekJK() && CekTNomorHP() && CekAlamat()){
                int status_data = dbHelper.C_KK(""+etNomorIuran.getText(),""+etNIK.getText());

                if(status_data == 0 && ID_KK.equals("")){
                    long status = dbHelper.I_KK(""+etNomorIuran.getText(),
                            ""+etNIK.getText(),
                            ""+etNamaLengkap.getText(),
                            ""+etTempatLahir.getText(),
                            ""+Tanggal_Lahir,
                            ""+JK,
                            ""+etNomorHP.getText(),
                            ""+etAlamat.getText());

                    if(status != -1){
                        popupUtils.showtoastsucces("Berhasil menambahkan KK");
                        Reset();
                    } else {
                        popupUtils.showtoastfailed("Gagal menambahkan KK, silahkan cek data anda");
                    }
                } else {
                    if(!ID_KK.equals("")){
                        long status = dbHelper.U_KK(""+ID_KK,
                                ""+etNomorIuran.getText(),
                                ""+etNIK.getText(),
                                ""+etNamaLengkap.getText(),
                                ""+etTempatLahir.getText(),
                                ""+Tanggal_Lahir,
                                ""+JK,
                                ""+etNomorHP.getText(),
                                ""+etAlamat.getText(),
                                Status);
                        if(status != -1){
                            popupUtils.showtoastsucces("Berhasil merubah KK");
                            Daftar();
                        } else {
                            popupUtils.showtoastfailed("Gagal merubah KK, silahkan cek data anda");
                        }
                    }else {
                        popupUtils.showtoastfailed("Gagal menambahkan KK, data duplikat");
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(!ID_KK.isEmpty()){
            Daftar();
        }else {
            Dashboard();
        }

    }

    public void Dashboard(){
        Intent i = new Intent(this,DashboardActivity.class);
        startActivity(i);
        finish();
    }

    public void Daftar(){
        Intent i = new Intent(this,DaftarKKActivity.class);
        startActivity(i);
        finish();
    }

    private void updateLabel() {
        String sendFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf2 = new SimpleDateFormat(sendFormat, Locale.US);
        Tanggal_Lahir = sdf2.format(Tanggal.getTime());

        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tvTanggalLahir.setText(sdf.format(Tanggal.getTime()));
    }

    void Set_Data(){
        kkModels = new ArrayList<>();
        kkModels.clear();

        kkModels = dbHelper.KK_Data_Detail(ID_KK);
        Log.i("kkModels"," == "+kkModels.size());
        etNomorIuran.setText(kkModels.get(0).getNomor_iuran_kk());
        etNIK.setText(kkModels.get(0).getNik_kk());
        etNamaLengkap.setText(kkModels.get(0).getNama_kk());
        etTempatLahir.setText(kkModels.get(0).getTempat_lahir());
        etNomorHP.setText(kkModels.get(0).getNomo_hp());

        Tanggal_Lahir = kkModels.get(0).getTanggal_lahir();
        SimpleDateFormat sendFormat = new SimpleDateFormat("yyyy-MM-dd");
        Log.i("Data_Date"," == "+Tanggal_Lahir);
        Date date = null;
        try {
            date = sendFormat.parse(Tanggal_Lahir);
            Log.i("Data_Date_Format"," == "+date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tvTanggalLahir.setText(sdf.format(date));

        etAlamat.setText(kkModels.get(0).getAlamat_kk());

        JK = kkModels.get(0).getJk();
        if(JK.equals("P")){
            rbPerempuan.setChecked(true);
        }else {
            rbLakilaki.setChecked(true);
        }

        Status = kkModels.get(0).getStatus();
        if(Status == 1){
            swStatus.setChecked(true);
            swStatus.setText("Aktif");
        }else {
            swStatus.setChecked(false);
            swStatus.setText("Nonaktif");
        }
    }

    void Reset(){
        etNomorIuran.setText("");
        etNIK.setText("");
        etNamaLengkap.setText("");
        etTempatLahir.setText("");
        tvTanggalLahir.setText("");
        etNomorHP.setText("");
        etAlamat.setText("");
        rbPerempuan.setChecked(false);
        rbLakilaki.setChecked(false);
        JK="";
        Tanggal_Lahir="";
    }

    private boolean CekNomorIuran(){
        String NIK = etNomorIuran.getText().toString().trim();

        if (NIK.isEmpty()) {
            etNomorIuran.setError("Nomor iuran tidak boleh kosong");
            etNomorIuran.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            etNomorIuran.setError(null);
            etNomorIuran.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
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

    private boolean CekTL(){
        String Tempat = etTempatLahir.getText().toString().trim();

        if (Tempat.isEmpty()) {
            etTempatLahir.setError("Tempat lahir tidak boleh kosong");
            etTempatLahir.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            etTempatLahir.setError(null);
            etTempatLahir.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
        }
        return true;
    }

    private boolean CekTanggal(){
        String Tanggal = tvTanggalLahir.getText().toString().trim();

        if (Tanggal.isEmpty()) {
            llTanggalLahir.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            llTanggalLahir.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
        }
        return true;
    }

    private boolean CekJK(){
        if (JK.isEmpty()) {
            rgJenisKelamin.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            rgJenisKelamin.setBackground(null);
        }
        return true;
    }

    private boolean CekTNomorHP(){
        String Tanggal = etNomorHP.getText().toString().trim();

        if (Tanggal.isEmpty()) {
            llNomorHP.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        } else if (Tanggal.substring(0,1).equals("0")) {
            llNomorHP.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        } else {
            llNomorHP.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
        }
        return true;
    }

    private boolean CekAlamat(){
        String Alamat = etAlamat.getText().toString().trim();

        if (Alamat.isEmpty()) {
            etAlamat.setError("Alamat tidak boleh kosong");
            etAlamat.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            etAlamat.setError(null);
            etAlamat.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
        }
        return true;
    }
}