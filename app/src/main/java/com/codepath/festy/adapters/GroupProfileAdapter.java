package com.codepath.festy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.festy.R;
import com.codepath.festy.models.Profile;
import com.google.firebase.database.DatabaseReference;


import java.util.List;

public class GroupProfileAdapter extends RecyclerView.Adapter<GroupProfileAdapter.ViewHolder>
{
    Context context;
    List<Profile> profiles;
    DatabaseReference reference;

    public GroupProfileAdapter(Context context, List<Profile> profiles,DatabaseReference reference)
    {
        this.context=context;
        this.profiles=profiles;
        this.reference=reference;
    }
    @NonNull
    @Override


    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View profView= LayoutInflater.from(context).inflate(R.layout.group_member,parent,false);
        return new ViewHolder(profView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile profile = profiles.get(position);
        holder.bind(profile);

    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivProfilePic;
        TextView tvUserName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePic=itemView.findViewById(R.id.ivProfilePic);
            tvUserName=itemView.findViewById(R.id.ivProfilePic);

        }

        public void bind(Profile profile) {

        }
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }
}
