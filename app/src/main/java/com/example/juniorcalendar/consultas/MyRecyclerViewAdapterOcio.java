package com.example.juniorcalendar.consultas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.modelo.ConsultaMedica;
import com.example.juniorcalendar.modelo.Ocio;

import java.util.List;

public class MyRecyclerViewAdapterOcio extends RecyclerView.Adapter<MyRecyclerViewAdapterOcio.ViewHolder> {

    private List<Ocio> mData;
    private LayoutInflater mInflater;
    private MyRecyclerViewAdapterOcio.ItemClickListener mClickListener;
    private int posicionClick;

    public void setClick(int position) {
        this.posicionClick = position;
    }

    public int getPosicionClick() {
        return posicionClick;
    }

    public MyRecyclerViewAdapterOcio(Context context, List<Ocio> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public MyRecyclerViewAdapterOcio.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycle_ocio, parent, false);
        return new MyRecyclerViewAdapterOcio.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ocio ocio = mData.get(position);
        holder.myTextView.setText(" " + ocio.getNombreActividad() + "\n" +
                " a las " + ocio.getHoraActividad() + "\n" +
                " en " + ocio.getUbicacionActividad() + "\n" +
                " Info adicional: " + ocio.getDescripcion());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setClickListener(MyRecyclerViewAdapterOcio.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public Ocio getItem(int position) {
        return mData.get(position);
    }

    public void inserterData(Ocio ocio) {
        mData.add(ocio);
    }

    public List<Ocio> getmData() {
        return mData;
    }

    public void removedAll() {
        mData.clear();
    }

    public void removedOne(int pos) {
        mData.remove(pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.textViewOcio);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
