package com.codepath.festy.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.festy.models.Profile;

import java.util.List;

public class GroupProfileAdapter extends RecyclerView.Adapter<ActAdapter.ViewHolder>
{
    Context context;
    List<Profile> profiles;
    @NonNull
    @Override
    public ActAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ActAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
