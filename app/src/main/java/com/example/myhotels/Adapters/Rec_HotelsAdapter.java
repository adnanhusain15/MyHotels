package com.example.myhotels.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myhotels.R;

import java.util.List;

import com.example.myhotels.HotelViewer;
import com.example.myhotels.Util.HotelView;

public class Rec_HotelsAdapter extends RecyclerView.Adapter<Rec_HotelsAdapter.MyViewHolder> {

    private List<HotelView> hotelsList;
    private Context rec_Context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView rec_title, rec_location,rec_rating,rec_features;
        public ImageView rec_thumbnail;
        public Button rec_view;
        public MyViewHolder(View view) {
            super(view);
            rec_thumbnail = view.findViewById(R.id.rec_thumbnail);
            rec_title =  view.findViewById(R.id.rec_title);
            rec_location =  view.findViewById(R.id.rec_location);
            rec_rating =  view.findViewById(R.id.rec_rating);
            rec_features = view.findViewById(R.id.rec_features);
            rec_view = view.findViewById(R.id.rec_viewbutton);
        }
    }


    public Rec_HotelsAdapter(Context rec_Context,List<HotelView> hotelsList) {
        this.rec_Context = rec_Context;
        this.hotelsList = hotelsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rec_hotel_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        HotelView hotel = hotelsList.get(position);
        holder.rec_title.setText(hotel.getName());
        holder.rec_location.setText(hotel.getLocation());
        holder.rec_features.setText(hotel.getFeatures());
        holder.rec_rating.setText(String.valueOf(hotel.getRating()));
        Glide.with(rec_Context).load(hotel.getThumbnail()).into(holder.rec_thumbnail);
        holder.rec_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rec_Context, HotelViewer.class);
                intent.putExtra("hotelname",holder.rec_title.getText().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                rec_Context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelsList.size();
    }
}
