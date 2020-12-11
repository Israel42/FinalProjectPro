package com.example.finalprojectpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.viewholder> {
    Context context;
    List<Review> reviewList;


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customreview,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.stamp.setText(reviewList.get(position).getTimestamp());
        holder.reviewer.setText(reviewList.get(position).getReviewer());
        holder.review.setText(reviewList.get(position).getReview());
        holder.bar.setRating(reviewList.get(position).getReviewbar());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView reviewer,review, stamp;
        RatingBar bar;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            reviewer =itemView.findViewById(R.id.reviewer);
            stamp=itemView.findViewById(R.id.reviewtime);
            review=itemView.findViewById(R.id.review);
            bar=itemView.findViewById(R.id.hotelrate);
        }
    }
}
