    package com.aditya.ariesadmin.Adapter;

    import android.content.Context;
    import android.content.Intent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.TextView;

    import androidx.cardview.widget.CardView;
    import androidx.recyclerview.widget.RecyclerView;

    import com.aditya.ariesadmin.DaftarKKActivity;
    import com.aditya.ariesadmin.Model.KKModel;
    import com.aditya.ariesadmin.R;
    import com.aditya.ariesadmin.TambahDataKKActivity;

    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.List;

    public class KKAdapter extends RecyclerView.Adapter<KKAdapter.Holder> {

        Context context;
        public List<KKModel> kkModels;

        public KKAdapter(Context context, List<KKModel> kkModels) {
            this.context = context;
            this.kkModels = kkModels;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kk_warga,
                    parent, false);

            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            KKModel dm = kkModels.get(position);

            holder.tvNIKKK.setText(dm.getNomor_iuran_kk());
            holder.tvNamaKK.setText(dm.getNama_kk());
            holder.tvAlamatKK.setText(dm.getAlamat_kk());

            holder.cvKKWarga.setOnClickListener(view -> {
                Intent i = new Intent(context, TambahDataKKActivity.class);
                i.putExtra("ID_KK",dm.getId_kk());
                context.startActivity(i);
                ((DaftarKKActivity)context).finish();
            });
        }

        @Override
        public int getItemCount() {
            return kkModels.size();
        }

        class Holder extends RecyclerView.ViewHolder{

            CardView cvKKWarga;
            TextView tvNIKKK,tvNamaKK,tvAlamatKK;
            public Holder(View itemView) {
                super(itemView);

                cvKKWarga = itemView.findViewById(R.id.cvKKWarga);
                tvNIKKK = itemView.findViewById(R.id.tvNIKKK);
                tvNamaKK = itemView.findViewById(R.id.tvNamaKK);
                tvAlamatKK = itemView.findViewById(R.id.tvAlamatKK);
            }
        }
    }
