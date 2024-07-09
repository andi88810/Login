package com.andichia.login;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaViewHolder> {

    private List<MahasiswaModel> _mahasiswaModelList;

    public MahasiswaAdapter(List<MahasiswaModel> mahasiswaModelList){
        this._mahasiswaModelList = mahasiswaModelList;
    }

    public void filter(List<MahasiswaModel> filteredList){
        this._mahasiswaModelList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_mahasiswa, parent, false);
        return new MahasiswaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, int position) {
        int no = position + 1;
        holder._noTextView.setText(no + ".");

        MahasiswaModel nm = _mahasiswaModelList.get(position);

        holder._jkImageView.setImageResource(R.drawable.boy);
        if(nm.getJenisKelamin().toLowerCase().equals("perempuan")){
            holder._jkImageView.setImageResource(R.drawable.girl);
        }
        holder._nimTextView.setText(nm.getNIM());
        holder._namaTextView.setText(nm.getNama());
        holder._jkTextView.setText(nm.getJenisKelamin());

        String jp = nm.getJP();
        jp = jp.substring(0, 2);
        holder._jpTextView.setText(jp);

        if (jp.equals("TI")) {
            holder._jpTextView.setBackgroundColor(Color.parseColor("#0000FF"));
        } else if (jp.equals("SI")) {
            holder._jpTextView.setBackgroundColor(Color.parseColor("#FF0000"));
        }
    }

    @Override
    public int getItemCount() {
        int count = (_mahasiswaModelList != null)? _mahasiswaModelList.size():0;
        return count;
    }
}
