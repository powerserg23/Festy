package com.codepath.festy;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.festy.adapters.ActAdapter;
import com.codepath.festy.models.Act;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvArts;
    List<Act> actData;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mFestivalRef = mRootRef.child("festival");
    DatabaseReference mCoachellaRef = mFestivalRef.child("0");
    DatabaseReference mScheduleRef = mCoachellaRef.child("schedule");

    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvArts = findViewById(R.id.rvArts);
        actData = new ArrayList<>();
    }

    protected void onStart() {
        super.onStart();

        mScheduleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot actSnapshot : dataSnapshot.getChildren()) {
                    Act tempAct = new Act(actSnapshot.child("artist").getValue(String.class), actSnapshot.child("time").getValue(String.class), actSnapshot.child("stage").getValue(String.class));
                    actData.add(tempAct);
                    Log.d(TAG, "LOG- " + tempAct.getName());
                }

                final ActAdapter actAdapter = new ActAdapter(MainActivity.this, actData);
                rvArts.setAdapter(actAdapter);
                rvArts.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                actAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}
