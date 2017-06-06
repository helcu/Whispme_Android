package com.whisper.whispme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.whisper.whispme.R;
import com.whisper.whispme.models.Whisp;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    List<Whisp> whisps;

    public NewsAdapter() {

    }

    public NewsAdapter(List<Whisp> whisps) {
        this.whisps = whisps;
    }

    public NewsAdapter setWhisps(List<Whisp> whisps) {
        this.whisps = whisps;
        return this;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.content_new, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        holder.usernameTextView.setText(String.valueOf(whisps.get(position).getUserId()));
        holder.titleTextView.setText(whisps.get(position).getTitle());
        holder.placeTextView.setText(whisps.get(position).getPlace());
        holder.dateCreationTextView.setText(whisps.get(position).getDateCreation().toString());
        holder.userPhotoImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        holder.userPhotoImageView.setErrorImageResId(R.mipmap.ic_launcher);
        holder.userPhotoImageView.setImageUrl("");
    }

    @Override
    public int getItemCount() {
        return whisps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ANImageView userPhotoImageView;
        TextView usernameTextView, titleTextView, placeTextView, dateCreationTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            userPhotoImageView = (ANImageView) itemView.findViewById(R.id.userPhotoImageView);
            usernameTextView = (TextView) itemView.findViewById(R.id.usernameTextView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            placeTextView = (TextView) itemView.findViewById(R.id.placeTextView);
            dateCreationTextView = (TextView) itemView.findViewById(R.id.dateCreationTextView);
        }
    }
}
