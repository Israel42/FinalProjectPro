package com.example.finalprojectpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecentHotels extends RecyclerView.Adapter<RecentHotels.viewholder> {
    Context context;
    List<Reservationdetail> reservationdetailList;
    Hoteldetail hoteldetail;
    FirebaseDatabase database;

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
        String h=String.valueOf(reservationdetailList.get(position).getReservedhotel());
        holder.hn.setText(h);
        DatabaseReference reference=database.getReference().child("HotelDetails").child("Hotels").child(h);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hoteldetail=snapshot.getValue(Hoteldetail.class);
                Picasso.get().load(hoteldetail.getImagepath()).fit().into(holder.hi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return reservationdetailList.size();
    }


    public class viewholder extends RecyclerView.ViewHolder {
        TextView hn;
        ImageView hi;
        public viewholder(View itemView){
            super(itemView);
            hn=itemView.findViewById(R.id.recenthotelnameview);
            hi=itemView.findViewById(R.id.recenthotelimageview);
        }
    }
}
