package com.example.juniorcalendar.consultas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.modelo.ConsultaMedica;
import com.example.juniorcalendar.modelo.SolicitudVisita;

import java.util.List;

public class MyRecyclerViewAdapterHorario  extends RecyclerView.Adapter<MyRecyclerViewAdapterHorario.ViewHolder> {

    private List<SolicitudVisita> mData;
    private LayoutInflater mInflater;
    private MyRecyclerViewAdapterHorario.ItemClickListener mClickListener;
    private int posicionClick;

    public void setClick(int position) {
        this.posicionClick = position;
    }

    public int getPosicionClick() {
        return posicionClick;
    }

    public MyRecyclerViewAdapterHorario(Context context, List<SolicitudVisita> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public MyRecyclerViewAdapterHorario.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycle_hoarario, parent, false);
        return new MyRecyclerViewAdapterHorario.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapterHorario.ViewHolder holder, int position) {
        SolicitudVisita solicitudVisita = mData.get(position);


        holder.myTextView.setText("Solicito" +solicitudVisita.getPeriodoCambio() + "\n" +
                " para " + solicitudVisita.getNota() + "\n Respuesta: " + solicitudVisita.getRespuesta());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setClickListener(MyRecyclerViewAdapterHorario.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public SolicitudVisita getItem(int position) {
        return mData.get(position);
    }

    public void inserterData(SolicitudVisita solicitudVisita) {
        mData.add(solicitudVisita);
    }

    public List<SolicitudVisita> getmData() {
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
            myTextView = itemView.findViewById(R.id.textViewHorario);
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