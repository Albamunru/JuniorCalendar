package com.example.juniorcalendar.consultas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.modelo.Educacion;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Educacion> mData;
    private LayoutInflater mInflater;
    private static ItemClickListener mClickListener;
    private int posicionClick;

    public void setClick(int position){
        this.posicionClick=position;
    }

    public int getPosicionClick() {
        return posicionClick;
    }

    public MyRecyclerViewAdapter(Context context, List<Educacion> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Educacion educacionActual=mData.get(position);

        holder.myTextView.setText(" "+educacionActual.getTipoEvento()+"\n"+ " a las "+educacionActual.getHora()+"\n en "+educacionActual.getUbicacion()+"\n Info adiccional: "+ educacionActual.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public Educacion getItem(int position) {
        return mData.get(position);
    }

    public void inserterData(Educacion educacion) {
        mData.add(educacion);
    }

    public List<Educacion> getmData() {
        return mData;
    }

    public void removedAll() {
        mData.clear();
    }

    public void removedOne(int pos) {
        mData.remove(pos);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.textView1);
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
