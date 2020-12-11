package com.example.finalprojectpro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.viewholder> {
    Context context;
    List<RoomGS> roomGSList;
    String hkind,hname,rkind;

    public RoomAdapter(Context context, List<RoomGS> roomGSList, String hkind, String hname, String rkind) {
        this.context = context;
        this.roomGSList = roomGSList;
        this.hkind = hkind;
        this.hname = hname;
        this.rkind = rkind;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.customroom,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
    Picasso.get().load(roomGSList.get(position).getImagepath()).fit().into(holder.imageView);
    holder.roomnum.setText(roomGSList.get(position).getNumber().toString());
    holder.pricer.setText(String.format("%s %s",String.valueOf(roomGSList.get(position).getPrice()),"ETB"));
    holder.book.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Intent intent=new Intent(view.getContext(),FinalPayment.class);
        intent.putExtra("hkindpass",hkind);
        intent.putExtra("hotelpass",hname);
        intent.putExtra("rkindpass",rkind);
        intent.putExtra("pricepass",holder.pricer.getText().toString());
        intent.putExtra("roompass",holder.roomnum.getText().toString());
        view.getContext().startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return roomGSList.size();
    }



    public class viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView roomnum,pricer;
        Button book;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageroom);
            roomnum=itemView.findViewById(R.id.selectedroom);
            pricer=itemView.findViewById(R.id.priceofroom);
            book=itemView.findViewById(R.id.roombook);
        }
    }
}