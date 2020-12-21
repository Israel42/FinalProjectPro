package com.example.finalprojectpro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class Roomkindadaptor extends RecyclerView.Adapter<Roomkindadaptor.viewholder> {
    Context context;
    List<RoomKindGS> roomKindGSList;
    String hkind,hname;

    public Roomkindadaptor(Context context, List<RoomKindGS> roomKindGSList, String hkind, String hname) {
        this.context = context;
        this.roomKindGSList = roomKindGSList;
        this.hkind = hkind;
        this.hname = hname;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_roomkind_list,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
    holder.rkind.setText(roomKindGSList.get(position).getType());
    holder.cardView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(view.getContext(),Roomlist.class);
            intent.putExtra("hkindpass",hkind);
            intent.putExtra("hotelpass",hname);
            intent.putExtra("rkindpass",holder.rkind.getText().toString());
            view.getContext().startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return roomKindGSList.size();
    }


    public class viewholder extends RecyclerView.ViewHolder{
            TextView rkind;
            CardView cardView;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            rkind=itemView.findViewById(R.id.roomkindname);
            cardView=itemView.findViewById(R.id.rkcard);

        }
    }
}
