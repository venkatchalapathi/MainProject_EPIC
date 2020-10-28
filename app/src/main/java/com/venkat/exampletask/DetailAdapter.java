package com.venkat.exampletask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    List<Sheet1> list;
    Context context;

    public DetailAdapter( Context context,List<Sheet1> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sheet1 item = list.get(position);
        holder.name.setText(item.getName());
        holder.epic.setText(item.getePICNo());
        holder.address.setText(item.getHouseNo());
        holder.booth.setText(item.getBooth());
        holder.age.setText(item.getAge());
        holder.gender.setText(item.getGender());
        holder.spouse.setText(item.getSpouseOrFatherName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,epic,booth,address,age,gender,spouse;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            epic = itemView.findViewById(R.id.epic);
            booth = itemView.findViewById(R.id.booth);
            address = itemView.findViewById(R.id.address);
            age = itemView.findViewById(R.id.age);
            gender = itemView.findViewById(R.id.gender);
            spouse = itemView.findViewById(R.id.spouse);
        }
    }
}
