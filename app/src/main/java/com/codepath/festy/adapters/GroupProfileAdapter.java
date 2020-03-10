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
    private onMemberListener mOnMemberListener;

    public GroupProfileAdapter(Context context, List<Profile> profiles,DatabaseReference reference,onMemberListener onMemberListener)
    {
        this.context=context;
        this.profiles=profiles;
        this.reference=reference;
        this.mOnMemberListener=onMemberListener;
    }
    @NonNull
    @Override


    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View profView= LayoutInflater.from(context).inflate(R.layout.group_member,parent,false);
        return new ViewHolder(profView,mOnMemberListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile profile = profiles.get(position);
        holder.bind(profile);

    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView ivProfilePic;
        TextView tvUserName;
        onMemberListener onMemberListener;

        public ViewHolder(@NonNull View itemView,onMemberListener onMemberListener) {
            super(itemView);
            ivProfilePic=itemView.findViewById(R.id.ivProfilePic);
            tvUserName=itemView.findViewById(R.id.tvUserName);
            this.onMemberListener=onMemberListener;

            itemView.setOnClickListener(this);


        }

        public void bind(Profile profile) {
        tvUserName.setText(profile.getName());
        ivProfilePic.setImageResource(R.drawable.ic_group_black_24dp);

        }

        @Override
        public void onClick(View v) {
        onMemberListener.onMemberClick(getAdapterPosition());
        }
    }
    public interface onMemberListener
    {
        void onMemberClick(int position);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }
}
