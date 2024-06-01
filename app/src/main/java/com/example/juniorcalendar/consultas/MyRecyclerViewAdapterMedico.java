package com.example.juniorcalendar.consultas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.modelo.ConsultaMedica;

import java.util.List;

public class MyRecyclerViewAdapterMedico extends RecyclerView.Adapter<MyRecyclerViewAdapterMedico.ViewHolder> {

    private List<ConsultaMedica> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int posicionClick;

    public void setClick(int position) {
        this.posicionClick = position;
    }

    public int getPosicionClick() {
        return posicionClick;
    }

    public MyRecyclerViewAdapterMedico(Context context, List<ConsultaMedica> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycle_educacion, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ConsultaMedica consultaMedica = mData.get(position);
        holder.myTextView.setText(" " + consultaMedica.getEspecialidad() + "\n" +
                " a las " + consultaMedica.getHoraMedico() + "\n" +
                " en " + consultaMedica.getUbicacion() + "\n" +
                " Info adicional: " + consultaMedica.getObservaciones());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public ConsultaMedica getItem(int position) {
        return mData.get(position);
    }

    public void inserterData(ConsultaMedica consultaMedica) {
        mData.add(consultaMedica);
    }

    public List<ConsultaMedica> getmData() {
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
            myTextView = itemView.findViewById(R.id.textViewEducacion);
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
