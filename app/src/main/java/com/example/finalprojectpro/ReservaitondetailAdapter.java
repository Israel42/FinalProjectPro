package com.example.finalprojectpro;

import android.content.Context;
import android.content.Intent;
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
        holder.passcode.setText(reservationdetails.get(position).getGeneratedcode());
    holder.reserved_hotel.setText(reservationdetails.get(position).getReservedhotel());
    holder.room_type.setText(reservationdetails.get(position).getReservedroomtype());
    holder.detailres.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String reshot=holder.passcode.getText().toString();
            Intent intent= new Intent(view.getContext(),Reservedetail.class);
            intent.putExtra("codepass",reshot);
            view.getContext().startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView reserved_hotel,room_type,detailres,passcode;

        public viewholder(View itemView){
            super(itemView);
        reserved_hotel=itemView.findViewById(R.id.reservedhotel_names);
        room_type=itemView.findViewById(R.id.reservedroomtype);
        detailres=itemView.findViewById(R.id.detailreserv);
        passcode=itemView.findViewById(R.id.invistext);
        }

    }
}
