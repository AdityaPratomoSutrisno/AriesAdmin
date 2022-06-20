package com.aditya.ariesadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aditya.ariesadmin.Model.IuranModel;
import com.aditya.ariesadmin.Model.KKModel;
import com.aditya.ariesadmin.Model.PeriodeModel;
import com.aditya.ariesadmin.Utils.DBHelper;
import com.aditya.ariesadmin.Utils.PopupUtils;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TambahIuranActivity extends AppCompatActivity {

    DBHelper dbHelper;
    PopupUtils popupUtils;

    ImageView ivBack;
    TextView tvTitleIuran,tvTanggalPelunasan;
    EditText etNamaLengkapIuran,etJumlahSetoran;
    Spinner spNomorIuran,spPeriode;
    LinearLayout llSpinnerNomorIuran,llTanggalPelunasan;
    Button btnBatalIuran,btnTambahIuran;

    String Tanggal_Lunas="";
    Calendar Tanggal;
    DatePickerDialog.OnDateSetListener date;

    List<KKModel> kkModels;
    List<PeriodeModel> periodeModels;
    List<IuranModel> iuranModels;
    String[] ID_KK,Nomor_Iuran,Nama_Lengkap, ID_Periode, Periode;
    String ID_S_KK="";
    String ID_S_Periode="";
    String ID_Iuran="";
    int Pos_Nomor=0;
    int Pos_Periode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_iuran);

        View toastsucces = getLayoutInflater().inflate(R.layout.toast_success, null);
        View toastinfo = getLayoutInflater().inflate(R.layout.toast_information, null);
        View toastfail = getLayoutInflater().inflate(R.layout.toast_failed, null);

        popupUtils = new PopupUtils(this, toastsucces, toastinfo, toastfail);
        dbHelper = new DBHelper(this);

        if(getIntent().getStringExtra("ID_Iuran")!=null){
            ID_Iuran = getIntent().getStringExtra("ID_Iuran");
        }

        ivBack = findViewById(R.id.ivBack);
        tvTitleIuran = findViewById(R.id.tvTitleIuran);
        tvTanggalPelunasan = findViewById(R.id.tvTanggalPelunasan);
        etNamaLengkapIuran = findViewById(R.id.etNamaLengkapIuran);
        etJumlahSetoran = findViewById(R.id.etJumlahSetoran);
        spNomorIuran = findViewById(R.id.spNomorIuran);
        spPeriode = findViewById(R.id.spPeriode);
        llSpinnerNomorIuran = findViewById(R.id.llSpinnerNomorIuran);
        llTanggalPelunasan = findViewById(R.id.llTanggalPelunasan);
        btnBatalIuran = findViewById(R.id.btnBatalIuran);
        btnTambahIuran = findViewById(R.id.btnTambahIuran);

        if(!ID_Iuran.equals("")){
            btnBatalIuran.setVisibility(View.VISIBLE);
            btnTambahIuran.setText("Ubah");
            tvTitleIuran.setText("Ubah Data Iuran");
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

        tvTanggalPelunasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Year = Tanggal.get(Calendar.YEAR);
                int Month = Tanggal.get(Calendar.MONTH);
                int Day = Tanggal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(TambahIuranActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,date,Year,Month,Day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        spNomorIuran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ID_S_KK = ID_KK[position];
                Pos_Nomor=position;
                etNamaLengkapIuran.setText(Nama_Lengkap[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spPeriode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ID_S_Periode = ID_Periode[position];
                Pos_Periode=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnTambahIuran.setOnClickListener(view -> {
            if(CekNomorIuran() && CekJumlah() && CekTanggal()){
                int status_data = dbHelper.C_Iuran(""+ID_S_KK,""+ID_S_Periode);

                if(status_data == 0 && ID_Iuran.equals("")){
                    long status = dbHelper.I_Iuran(""+ID_S_KK,
                            ""+etJumlahSetoran.getText(),
                            ""+ID_S_Periode,
                            ""+Tanggal_Lunas);

                    if(status != -1){
                        popupUtils.showtoastsucces("Berhasil menambahkan data iuran");
                        Reset();
                    } else {
                        popupUtils.showtoastfailed("Gagal menambahkan data iuran, silahkan cek data anda");
                    }
                } else {
                    if(!ID_Iuran.equals("")){
                        long status = dbHelper.U_Iuran(""+ID_Iuran,
                                ""+ID_S_KK,
                                ""+etJumlahSetoran.getText(),
                                ""+ID_S_Periode,
                                ""+Tanggal_Lunas);
                        if(status != -1){
                            popupUtils.showtoastsucces("Berhasil merubah data iuran");
                            Daftar();
                        } else {
                            popupUtils.showtoastfailed("Gagal merubah data iuran, silahkan cek data anda");
                        }
                    }else {
                        popupUtils.showtoastfailed("Gagal menambahkan data iuran, data duplikat");
                    }
                }
            }
        });

        btnBatalIuran.setOnClickListener(view -> {
            Daftar();
        });

        ivBack.setOnClickListener(view -> {
            if(!ID_Iuran.equals("")){
                Daftar();
            }else {
                Dashboard();
            }
        });

        Data_Spinner();
    }

    @Override
    public void onBackPressed() {
        if(!ID_Iuran.equals("")){
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
        Intent i = new Intent(this,DaftarIuranActivity.class);
        startActivity(i);
        finish();
    }

    private void updateLabel() {
        String sendFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf2 = new SimpleDateFormat(sendFormat, Locale.US);
        Tanggal_Lunas = sdf2.format(Tanggal.getTime());

        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tvTanggalPelunasan.setText(sdf.format(Tanggal.getTime()));
    }

    void Data_Spinner(){
        kkModels = new ArrayList<>();
        kkModels.clear();

        kkModels = dbHelper.KK_Data_Spinner();

        ID_KK = new String[kkModels.size()+1];
        Nomor_Iuran = new String[kkModels.size()+1];
        Nama_Lengkap = new String[kkModels.size()+1];

        ID_KK[0] = "0";
        Nomor_Iuran[0] = "Pilih Nomor Iuran";
        Nama_Lengkap[0] = "";

        for (int i = 0; i< kkModels.size();i++) {
            KKModel km = kkModels.get(i);

            ID_KK[i + 1] = km.getId_kk();
            Nomor_Iuran[i + 1] = km.getNomor_iuran_kk();
            Nama_Lengkap[i + 1] = km.getNama_kk();

            if(!ID_S_KK.isEmpty()){
                if(ID_S_KK.equals(ID_KK[i+1])){
                    Pos_Nomor = i+1;
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TambahIuranActivity.this, R.layout.item_spinner, Nomor_Iuran);
        spNomorIuran.setAdapter(adapter);
        if(Pos_Nomor!=0){
            spNomorIuran.setSelection(Pos_Nomor);
        }

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

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(TambahIuranActivity.this, R.layout.item_spinner, Periode);
        spPeriode.setAdapter(adapter2);
        if(Pos_Periode==0){
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("L", new Locale("in", "ID"));
            String month = df.format(c);

            spPeriode.setSelection(Integer.parseInt(month)-1);
        }else {
            spPeriode.setSelection(Pos_Periode-1);
        }

    }

    void Set_Data(){
        iuranModels = new ArrayList<>();
        iuranModels.clear();

        iuranModels = dbHelper.Iuran_Data_Detail(ID_Iuran);

        ID_S_KK = iuranModels.get(0).getId_warga_iuran();
        ID_S_Periode = iuranModels.get(0).getId_periode_iuran();
        Pos_Periode = Integer.parseInt(ID_S_Periode);
        etJumlahSetoran.setText(iuranModels.get(0).getNominal_iuran());

        Tanggal_Lunas = iuranModels.get(0).getTanggal_iuran();
        SimpleDateFormat sendFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sendFormat.parse(Tanggal_Lunas);
            Log.i("Data_Date_Format"," == "+date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tvTanggalPelunasan.setText(sdf.format(date));
    }

    void Reset(){
        spNomorIuran.setSelection(0);

        etJumlahSetoran.setText("");
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("L", new Locale("in", "ID"));
        String month = df.format(c);

        spPeriode.setSelection(Integer.parseInt(month)-1);

        tvTanggalPelunasan.setText("");
        Tanggal_Lunas="";
    }

    private boolean CekNomorIuran(){
        if (ID_S_KK.isEmpty() || ID_S_KK.equals("0")) {
            llSpinnerNomorIuran.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            llSpinnerNomorIuran.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
        }
        return true;
    }

    private boolean CekJumlah(){
        String Jumlah = etJumlahSetoran.getText().toString().trim();

        if (Jumlah.isEmpty()) {
            etJumlahSetoran.setError("Jumlah setoran lengkap tidak boleh kosong");
            etJumlahSetoran.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            etJumlahSetoran.setError(null);
            etJumlahSetoran.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
        }
        return true;
    }

    private boolean CekTanggal(){
        String Tanggal = tvTanggalPelunasan.getText().toString().trim();

        if (Tanggal.isEmpty()) {
            llTanggalPelunasan.setBackground(getResources().getDrawable(R.drawable.rounded_red_border));
            return false;
        }else {
            llTanggalPelunasan.setBackground(getResources().getDrawable(R.drawable.rounded_gray_border));
        }
        return true;
    }

}