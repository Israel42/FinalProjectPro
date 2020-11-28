package com.example.finalprojectpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecentHotels extends RecyclerView.Adapter<RecentHotels.viewholder> {
    Context context;
    List<Reservationdetail> reservationdetailList;
    Hoteldetail hoteldetail;

    public RecentHotels() {
    }

    public RecentHotels(Context context, List<Reservationdetail> reservationdetailList) {
        this.context = context;
        this.reservationdetailList = reservationdetailList;
    }

    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customrecenthotels,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.hn.setText(reservationdetailList.get(position).getReservedhotel());

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    private class viewholder extends RecyclerView.ViewHolder {
        TextView hn;
        ImageView hi;
        public viewholder(View itemView){
            super(itemView);
            hn=itemView.findViewById(R.id.recenthotelnameview);
            hi=itemView.findViewById(R.id.recenthotelimageview);
        }
    }
}
