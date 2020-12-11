package com.example.finalprojectpro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Hotelkindadapter extends RecyclerView.Adapter<Hotelkindadapter.viewholder> {
    Context context;
    List<HotelKindGS> hotelKindGSList;
    String hkind;

    public Hotelkindadapter(Context context, List<HotelKindGS> hotelKindGSList) {
        this.context = context;
        this.hotelKindGSList = hotelKindGSList;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_hotelkind_list,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.hotelkind.setText(hotelKindGSList.get(position).getKind());
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),Hotellist.class);
                intent.putExtra("hkindpass",holder.hotelkind.getText().toString());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelKindGSList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView hotelkind;
        Button select;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            hotelkind=itemView.findViewById(R.id.hotelkindname);
            select=itemView.findViewById(R.id.selecthotelkind);
        }
    }
}
