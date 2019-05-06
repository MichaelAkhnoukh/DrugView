package com.google.android.gms.samples.vision.ocrreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SimilarsRVAdapter extends RecyclerView.Adapter<SimilarsRVAdapter.ViewHolder> {

    Context context;
    Medicine medicine;

    public SimilarsRVAdapter(Context context, Medicine medicine) {
        this.context = context;
        this.medicine = medicine;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.nameTV.setText(medicine.Similars.get(position));
        holder.priceTV.setText(medicine.SimilarsPrices.get(position) + " EGP");

    }

    @Override
    public int getItemCount() {
        return medicine.Similars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV, priceTV;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.nameTV);
            priceTV = itemView.findViewById(R.id.priceTV);

        }
    }
}
