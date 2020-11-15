package com.example.finalprojectpro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class RoomtypeAdapter extends RecyclerView.Adapter<RoomtypeAdapter.viewholder> {
    Context context;
    List<Roomtypegettersetter> roomtypeactivityList;
    String hotelname;

    public RoomtypeAdapter() {

    }

    public RoomtypeAdapter(Context context, List<Roomtypegettersetter> roomtypeactivityList, String hotelname) {
        this.context = context;
        this.roomtypeactivityList = roomtypeactivityList;
        this.hotelname = hotelname;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customroomtype,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
    holder.roomtype.setText(roomtypeactivityList.get(position).getType());
        holder.price.setText(String.format("%sETB", String.valueOf(roomtypeactivityList.get(position).getPrice())));
        Picasso.get().load(roomtypeactivityList.get(position).getImagepath()).fit().into(holder.roomimage);

        holder.roomavalibilty.setText(String.valueOf(roomtypeactivityList.get(position).getNumber()));
         holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomtypename=holder.roomtype.getText().toString();
                String roomprice=String.valueOf(roomtypeactivityList.get(position).getPrice());
                Intent intent=new Intent(v.getContext(),FinalPayment.class);
                intent.putExtra("hotelpass",hotelname);
                intent.putExtra("roompass",roomtypename);
                intent.putExtra("pricepass",roomprice);
                v.getContext().startActivity(intent);
            }});  }
    @Override
    public int getItemCount() {
        return roomtypeactivityList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        Button book;
        TextView roomtype,roomavalibilty,price;
        ImageView roomimage;

        public viewholder(@NonNull View itemView){
            super(itemView);
            book=itemView.findViewById(R.id.roombook);
            roomtype=itemView.findViewById(R.id.selectedroom);
            roomavalibilty=itemView.findViewById(R.id.Avalabile);
            price=itemView.findViewById(R.id.priceofroom);
            roomimage=itemView.findViewById(R.id.imageroom);
        }

    }
}