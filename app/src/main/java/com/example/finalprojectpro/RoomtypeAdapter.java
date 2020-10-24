package com.example.finalprojectpro;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
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

    public RoomtypeAdapter(Context context, List<Roomtypegettersetter> roomtypeactivityList) {
        this.context = context;
        this.roomtypeactivityList = roomtypeactivityList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
    holder.roomtypeN.setText(roomtypeactivityList.get(position).getRoomtypename());
    holder.availabelroom.setText(roomtypeactivityList.get(position).getAvailabelrooms());
        Picasso.get().load(roomtypeactivityList.get(position).getRoompictureuri()).transform(new CropSquareTransformation()).fit().into(holder.roompicture);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView roomtypeN,availabelroom;
        ImageView roompicture;
        public viewholder(@NonNull View itemView){
            super(itemView);
            roomtypeN=itemView.findViewById(R.id.roomtypeview);
            availabelroom=itemView.findViewById(R.id.roomsavailable);
            roompicture=itemView.findViewById(R.id.roompicview);
        }

    }
}