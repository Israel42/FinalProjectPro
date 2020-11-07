package com.example.finalprojectpro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class HoteldetailAdapter extends RecyclerView.Adapter<HoteldetailAdapter.viewholder> {
    Context context;
    List<Hoteldetail> hoteldetails;

    public HoteldetailAdapter(Context context, List<Hoteldetail> hoteldetails) {
        this.context = context;
        this.hoteldetails = hoteldetails;
    }

    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customhoteldetail,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.hotelname.setText(hoteldetails.get(position).getName());
        holder.ratingBar.setRating(hoteldetails.get(position).getRating());
        Picasso.get().load(hoteldetails.get(position).getImagepath()).fit().into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passhotel=holder.hotelname.getText().toString();
                Intent intent=new Intent(view.getContext(),Booking.class);
                intent.putExtra("hotelpass",passhotel);
                view.getContext().startActivity(intent);
            }
        });
        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passhotel=holder.hotelname.getText().toString();
                Intent intent=new Intent(view.getContext(),Reviews.class);
                intent.putExtra("hotelpass",passhotel);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hoteldetails.size();
    }


    public class viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        RatingBar ratingBar;
        TextView hotelname,review;



        public viewholder(@NonNull View itemView){
            super(itemView);
            hotelname=itemView.findViewById(R.id.hotelname1);
            imageView=itemView.findViewById(R.id.hotelimage1);
            ratingBar=itemView.findViewById(R.id.hotelrate);
            review=itemView.findViewById(R.id.hotelreviews);

        }
    }

}
