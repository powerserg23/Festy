package com.codepath.festy.fragments;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.codepath.festy.MainActivity;
import com.codepath.festy.R;
import com.codepath.festy.adapters.ActAdapter;
import com.codepath.festy.models.Act;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    final String TAG = "ListFragment";
    RecyclerView rvArts;
    List<Act> actData;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mFestivalRef = mRootRef.child("festival");
    DatabaseReference mCoachellaRef = mFestivalRef.child("0");
    DatabaseReference mScheduleRef = mCoachellaRef.child("schedule");

    SharedPreferences settings;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvArts = view.findViewById(R.id.rvArts);
        actData = new ArrayList<>();
        settings = getContext().getSharedPreferences("prefs", MODE_PRIVATE);

        addData();

    }

    protected void addData(){
        mScheduleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot actSnapshot : dataSnapshot.getChildren()) {
                    Act tempAct = new Act(actSnapshot.child("artist").getValue(String.class),
                            actSnapshot.child("time").getValue(String.class),
                            actSnapshot.child("stage").getValue(String.class),
                            actSnapshot.child("index").getValue(String.class),
                            actSnapshot.child("viewers").hasChild(settings.getString("name", "testing")));
                    actData.add(tempAct);
                    Log.d(TAG, "LOG- " + tempAct.getName());
                }

                final ActAdapter actAdapter = new ActAdapter(getContext(), actData,mScheduleRef);
                rvArts.setAdapter(actAdapter);
                rvArts.setLayoutManager(new LinearLayoutManager(getContext()));
                actAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void onResume() {
        super.onResume();

        final SharedPreferences.Editor editor = settings.edit();

        if (settings.getBoolean("FirstLaunch", true)) {
            // first time launching
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

            alert.setTitle("Hello! Welcome to Festy!");
            alert.setMessage("Please enter your full name");

            final EditText input = new EditText(getContext());
            alert.setView(input);

            alert.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String value = String.valueOf(input.getText());
                    editor.putString("name", value);
                    editor.apply();
                }
            });

            alert.show();

            editor.putBoolean("FirstLaunch", false);
            editor.apply();

        }
    }
}
