package com.whisper.whispme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.whisper.whispme.R;
import com.whisper.whispme.models.User;
import com.whisper.whispme.models.Whisp;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    List<User> users;

    public UsersAdapter() {

    }

    public UsersAdapter(List<User> users) {
        this.users = users;
    }

    public UsersAdapter setUsers(List<User> users) {
        this.users = users;
        return this;
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_user_world, parent, false));
    }

    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder holder, int position) {
        /*holder.usernameTextView.setText(String.valueOf(users.get(position).getUserId()));
        holder.titleTextView.setText(users.get(position).getTitle());
        holder.placeTextView.setText(users.get(position).getPlace());
        holder.dateCreationTextView.setText(users.get(position).getDateCreation().toString());
        holder.userPhotoImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        holder.userPhotoImageView.setErrorImageResId(R.mipmap.ic_launcher);
        holder.userPhotoImageView.setImageUrl("");*/
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /*ANImageView userPhotoImageView;
        TextView usernameTextView, titleTextView, placeTextView, dateCreationTextView;
        */
        public ViewHolder(View itemView) {
            super(itemView);
            /*userPhotoImageView = (ANImageView) itemView.findViewById(R.id.userPhotoImageView);
            usernameTextView = (TextView) itemView.findViewById(R.id.usernameTextView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            placeTextView = (TextView) itemView.findViewById(R.id.placeTextView);
            dateCreationTextView = (TextView) itemView.findViewById(R.id.dateCreationTextView);*/
        }
    }
}
