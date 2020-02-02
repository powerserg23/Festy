package com.codepath.festy.adapters;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.festy.MainActivity;
import com.codepath.festy.R;
import com.codepath.festy.models.Act;

import java.util.List;

public class ActAdapter extends RecyclerView.Adapter<ActAdapter.ViewHolder> {


    Context context;
    List<Act> acts;

    public ActAdapter(Context context,List<Act> acts)
    {
        this.context = context;
        this.acts = acts;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View actView = LayoutInflater.from(context).inflate(R.layout.item_act,parent,false);
        return new ViewHolder(actView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Act act = acts.get(position);
        holder.bind(act);
    }

    @Override
    public int getItemCount()
    {
        return acts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView actName;
        TextView stageName;
        TextView setTime;
        RelativeLayout container;
        CheckBox isGoing;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            actName = itemView.findViewById(R.id.actName);
            stageName = itemView.findViewById(R.id.stageName);
            setTime = itemView.findViewById(R.id.setTime);
            container = itemView.findViewById(R.id.container);
            isGoing = itemView.findViewById(R.id.selectAct);



        }
        public void bind(final Act act)
        {
            actName.setText(act.getName());
            stageName.setText(act.getStage());
            setTime.setText(act.getTime());

            isGoing.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(isGoing.isChecked()) {
                        Log.d("MainActivity", act.getName());
                    }
                    else {
                        Log.d("MainActivity", act.getIndex());
                    }
                }
            });
        }
    }
}
