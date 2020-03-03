package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<PlaceOfInterest> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    ListAdapter(Context context, ArrayList<PlaceOfInterest> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = mData.get(position).name;
        String openingOHours = mData.get(position).openingHours;
        String rating = Double.toString(mData.get(position).rating);
        holder.myTextView.setText(name +"\n"+ openingOHours +"\n"+ rating);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvPlaceName);
        }

    }


}
