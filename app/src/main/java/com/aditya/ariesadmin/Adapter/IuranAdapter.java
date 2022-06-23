    package com.aditya.ariesadmin.Adapter;

    import android.content.Context;
    import android.content.Intent;
    import android.net.Uri;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.cardview.widget.CardView;
    import androidx.recyclerview.widget.RecyclerView;

    import com.aditya.ariesadmin.DaftarIuranActivity;
    import com.aditya.ariesadmin.DaftarKKActivity;
    import com.aditya.ariesadmin.Model.IuranModel;
    import com.aditya.ariesadmin.Model.KKModel;
    import com.aditya.ariesadmin.R;
    import com.aditya.ariesadmin.TambahDataKKActivity;
    import com.aditya.ariesadmin.TambahIuranActivity;
    import com.bumptech.glide.Glide;

    import java.net.URLEncoder;
    import java.text.NumberFormat;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.List;
    import java.util.Locale;

    public class IuranAdapter extends RecyclerView.Adapter<IuranAdapter.Holder> {

        Context context; //context untuk menyimpan activity / java yang dituju oleh adapter.
        public List<IuranModel> iuranModels; //Iuran model untuk menyimpan model dengan struktur data IuranModel.
        NumberFormat formatRupiah; //NumberFormat untuk merubah format number
        String Bulan;

        public IuranAdapter(Context context, List<IuranModel> iuranModels, String Bulan) { //memberikan nilai awal
            this.context = context;
            this.iuranModels = iuranModels;
            this.Bulan = Bulan;
            //nilai awal nya disini context, iuranModel dan Bulan
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_iuran_warga,
                    parent, false);

            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            //berfungsi memberikan value / nilai pada setiap komponen dan disetiap list/view memiliki nilai yang berbeda
            IuranModel dm = iuranModels.get(position);

            Locale localeID = new Locale("in", "ID");
            formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            formatRupiah.setMaximumFractionDigits(0);

            holder.tvNomorIuran.setText(dm.getNomor_iuran());
            holder.tvNamaIuran.setText(dm.getNama_lengkap_iuran());
            Log.i("Data Nominal"," == "+dm.getNominal_iuran());

            if(dm.getNominal_iuran().equals("Belum Lunas")){
                holder.tvNominalIuran.setText(dm.getNominal_iuran());
                holder.tvNominalIuran.setTextColor(context.getResources().getColor(R.color.RedFF));
                holder.tvTanggalPelunasan.setText("Ingatkan");
                holder.tvTanggalPelunasan.setTextColor(context.getResources().getColor(R.color.Green66));
                Glide.with(context).load(context.getResources().getDrawable(R.drawable.whatsapp)).into(holder.ivArrow);
            }else {
                holder.tvNominalIuran.setText(formatRupiah.format(Integer.parseInt(dm.getNominal_iuran())));
            }

            if(!dm.getTanggal_iuran().isEmpty()){
                SimpleDateFormat sendFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = sendFormat.parse(dm.getTanggal_iuran());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                holder.tvTanggalPelunasan.setText(sdf.format(date));
            }

            if(!dm.getId_iuran().isEmpty()){
                holder.cvIuranWarga.setOnClickListener(view -> {
                    Intent i = new Intent(context, TambahIuranActivity.class);
                    i.putExtra("ID_Iuran",dm.getId_iuran());
                    context.startActivity(i);
                    ((DaftarIuranActivity)context).finish();
                });
            }else {
                holder.cvIuranWarga.setOnClickListener(view -> {
                    try{
                        String message="";

                        message = "Diharapkan Bpk/Ibu "+dm.getNama_lengkap_iuran()+" untuk melunasi pembayaran iuran sampah bulan "+Bulan+", terima kasih";

                        String url = "https://api.whatsapp.com/send?phone=+62"+ dm.getNomor_hp()  +"&text=" + URLEncoder.encode(message, "UTF-8");
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        context.startActivity(i);
                    } catch(Exception e) {
                        Log.e("ERROR WHATSAPP",e.toString());
                        Toast.makeText(context, "Terjadi Kesalahan",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        @Override
        public int getItemCount() { //menampilkan list/view sesuai dengan jumlah yang ditentukan.
            return iuranModels.size();
        }

        class Holder extends RecyclerView.ViewHolder{
//mendeklarasikan id komponen dari sebuah view yang akan digunakan, untuk view dideklarasikan di holder yang satunya
            CardView cvIuranWarga;
            ImageView ivArrow;
            TextView tvNomorIuran,tvNamaIuran,tvNominalIuran,tvTanggalPelunasan;
            public Holder(View itemView) {
                super(itemView);

                cvIuranWarga = itemView.findViewById(R.id.cvIuranWarga);
                tvNomorIuran = itemView.findViewById(R.id.tvNomorIuran);
                tvNamaIuran = itemView.findViewById(R.id.tvNamaIuran);
                tvNominalIuran = itemView.findViewById(R.id.tvNominalIuran);
                tvTanggalPelunasan = itemView.findViewById(R.id.tvTanggalPelunasan);
                ivArrow = itemView.findViewById(R.id.ivArrow);
            }
        }
    }
