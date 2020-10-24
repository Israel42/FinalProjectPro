package com.example.finalprojectpro;

import android.content.Context;
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
        holder.hotelname.setText(hoteldetails.get(position).getHotelName());
        holder.ratingBar.setRating(hoteldetails.get(position).getRating());
        Picasso.get().load(hoteldetails.get(position).getHotelImageurl()).transform(new CropSquareTransformation()).fit().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        RatingBar ratingBar;
        ImageButton imageButton;
        Button book;
        TextView hotelname,review;
        public viewholder(@NonNull View itemView){
            super(itemView);
            hotelname=itemView.findViewById(R.id.hotelname);
            imageView=itemView.findViewById(R.id.hotelimage);
            ratingBar=itemView.findViewById(R.id.hotelrate);
            imageButton=itemView.findViewById(R.id.direction);
            book=itemView.findViewById(R.id.bookbutton);
            review=itemView.findViewById(R.id.reviews);
        }
    }

}
