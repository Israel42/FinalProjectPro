package com.example.finalprojectpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservaitondetailAdapter extends RecyclerView.Adapter<ReservaitondetailAdapter.viewholder>{
    Context context;
    List<Reservationdetail> reservationdetails;

    public ReservaitondetailAdapter(Context context, List<Reservationdetail> reservationdetails) {
        this.context = context;
        this.reservationdetails = reservationdetails;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customreservationdetail,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
    holder.user_Name.setText(reservationdetails.get(position).getReserver());
    holder.phone_num.setText(reservationdetails.get(position).getReserverphoneNumber());
    holder.reserved_hotel.setText(reservationdetails.get(position).getReservedhotel());
    holder.room_type.setText(reservationdetails.get(position).getReservedroomtype());
    holder.noofrooms.setText(reservationdetails.get(position).getReservednoofroom());
    holder.duration.setText(reservationdetails.get(position).getBookingdate());
    holder.bill_num.setText(reservationdetails.get(position).getPaybill());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView user_Name,phone_num,reserved_hotel,room_type,noofrooms,duration,bill_num;

        public viewholder(View itemView){
            super(itemView);
        user_Name=itemView.findViewById(R.id.username);
        phone_num=itemView.findViewById(R.id.phone_N);
        reserved_hotel=itemView.findViewById(R.id.hotel_names);
        room_type=itemView.findViewById(R.id.room_type);
        noofrooms=itemView.findViewById(R.id.numberofrooms);
        duration=itemView.findViewById(R.id.reserveduration);
        bill_num=itemView.findViewById(R.id.bill);
        }

    }
}
