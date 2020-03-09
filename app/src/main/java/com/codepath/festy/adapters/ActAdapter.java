package com.codepath.festy.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.festy.MainActivity;
import com.codepath.festy.R;
import com.codepath.festy.models.Act;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class ActAdapter extends RecyclerView.Adapter<ActAdapter.ViewHolder> {


    Context context;
    List<Act> acts;
    DatabaseReference reference;
    View.OnClickListener longClickListener;

    public ActAdapter(Context context,List<Act> acts,DatabaseReference reference)
    {
        this.context = context;
        this.acts = acts;
        this.reference=reference;

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
            isGoing.setChecked(act.getGoing());
            container.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    reference.child(act.getIndex()).child("viewers").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<String> friends = new ArrayList<>();

                            Log.d("MainActivity", "getChildrenCount() of Viewers- " + dataSnapshot.getChildrenCount());
                            if(dataSnapshot.getChildrenCount() > 0) {
                                for (DataSnapshot actSnapshot : dataSnapshot.getChildren()) {
                                    friends.add(actSnapshot.getValue(String.class));
                                    Log.d("MainActivity", "LOG- " + actSnapshot.getValue(String.class));
                                }
                            }

                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle("Friends Coming to see " + act.getName());
                            String message = "";
                            if(dataSnapshot.getChildrenCount() > 0) {
                                for (String f : friends) {
                                    message += f + ", ";
                                }
                                message = message.substring(0, message.length() - 2);
                            }
                            alert.setMessage(message);
                            alert.show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });

                    return true;
                }
            });

            isGoing.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(isGoing.isChecked()) {
                        Log.d("MainActivity", act.getName());
                        DatabaseReference singer=reference.child(String.valueOf(act.getIndex()));
                        DatabaseReference viewers=singer.child("viewers");
                        SharedPreferences settings=context.getSharedPreferences("prefs",MODE_PRIVATE);
                        viewers.child(settings.getString("name", "")).setValue(settings.getString("name", ""));
                    }
                    else {
                        Log.d("MainActivity", act.getTime());
                        DatabaseReference singer=reference.child(String.valueOf(act.getIndex()));
                        DatabaseReference viewers=singer.child("viewers");
                        SharedPreferences settings=context.getSharedPreferences("prefs",MODE_PRIVATE);
                        viewers.child(settings.getString("name", "")).removeValue();
                    }
                }
            });


        }

    }
}
