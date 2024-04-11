package com.example.demo1.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1.R;
import com.example.demo1.bean.BlueToothData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kotlin.collections.ArrayDeque;

public class BlueToothListAdapter  extends RecyclerView.Adapter<BlueToothListAdapter.BlueToothViewHolder>  {
    private List<BlueToothData> blueToothDataList = new ArrayDeque<>();
    private Context context;

    public BlueToothListAdapter.OnItemClickListener listener;
    public BlueToothListAdapter( Context context){
        this.context = context;
    }
    public void setData(List<BlueToothData> data){
        if (data.size()>0){
            this.blueToothDataList.clear();
            this.blueToothDataList.addAll(data);
            notifyDataSetChanged();
        }
    }
    @NonNull
    @Override
    public BlueToothViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewHolder= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.blue_dervices_layout,viewGroup,false);
        return new BlueToothViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull BlueToothViewHolder blueToothViewHolder, @SuppressLint("RecyclerView") int i) {
        if (blueToothDataList.size()>0){
            blueToothViewHolder.blueToothName.setText(blueToothDataList.get(i).getName());
            blueToothViewHolder.blueToothAddress.setText(blueToothDataList.get(i).getAddress());


        }
        if (listener != null){
            blueToothViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentPosition = blueToothViewHolder.getAdapterPosition();
                    if (currentPosition != -1){
                        listener.onTemClick(view,i,blueToothDataList.get(blueToothViewHolder.getAdapterPosition()));
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return blueToothDataList.size();
    }

    public interface OnItemClickListener{
        void onTemClick(View view,int position,BlueToothData blueToothData);
    }
    public void setOnClickListener(OnItemClickListener onItemClickListener){
        this.listener = onItemClickListener;
    }
    public static class  BlueToothViewHolder extends RecyclerView.ViewHolder{
        private final TextView blueToothName,blueToothAddress;
        public BlueToothViewHolder(@NonNull View itemView) {
            super(itemView);
            // 初始化控件
            blueToothName = itemView.findViewById(R.id.bluetooth_name);
            blueToothAddress = itemView.findViewById(R.id.bluetooth_address);
        }
    }

}