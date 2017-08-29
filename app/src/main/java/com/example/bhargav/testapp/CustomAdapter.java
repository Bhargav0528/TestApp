package com.example.bhargav.testapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.List;

/**
 * Created by Bhargav on 15-08-2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    private Context c;
    private List<ModelClass> models;


    public CustomAdapter(Context c, List<ModelClass> models) {
        this.c = c;
        this.models = models;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model, parent, false);
             ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {

        ModelClass model = models.get(position);

        holder.nameTxt.setText(model.getName());
        holder.description.setText(model.getDescription());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTxt;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.nameTxt);
            description = itemView.findViewById(R.id.descTxt);
        }
    }
}
