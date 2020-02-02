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
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.festy.MainActivity;
import com.codepath.festy.R;
import com.codepath.festy.models.Act;
import com.google.firebase.database.DatabaseReference;

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
        public void onButtonShowPopupWindowClick(View view) {

            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_window, null);

            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            // dismiss the popup window when touched
            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();
                    return true;
                }
            });
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
                    Toast.makeText(context,"longClicked",Toast.LENGTH_LONG);
                    onButtonShowPopupWindowClick(v);
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
