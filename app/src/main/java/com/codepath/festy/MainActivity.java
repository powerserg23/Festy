package com.codepath.festy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    List<Act> actData; //Artist+time+*add button*

    Button mButtonTest;
    TextView mConditionTextView;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mFestivalRef = mRootRef.child("festival");
    DatabaseReference mCoachellaRef = mFestivalRef.child("0");
    DatabaseReference mNameRef = mCoachellaRef.child("name");
    DatabaseReference mScheduleRef = mCoachellaRef.child("schedule");
    DatabaseReference mPerformanceRef = mScheduleRef.child("0");
    DatabaseReference mArtistRef = mPerformanceRef.child("artist");

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("test");

    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonTest = (Button)findViewById(R.id.buttonTest);
        mConditionTextView = (TextView)findViewById(R.id.textViewTest);

        rvArts=findViewById(R.id.rvArts);
        actData=new ArrayList<>();

        final ActAdapter actAdapter = new ActAdapter(this, actData);
        rvArts.setAdapter(actAdapter);
        rvArts.setLayoutManager(new LinearLayoutManager(this));
        //JSONObject

    }

    protected void onStart() {
        super.onStart();

        Log.d(TAG, "START" + mNameRef.toString());

        mScheduleRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            for (DataSnapshot actSnapshot : dataSnapshot.getChildren()) {
                                                                Act tempAct = new Act(actSnapshot.child("artist").getValue(String.class), actSnapshot.child("time").getValue(String.class), actSnapshot.child("stage").getValue(String.class));
                                                                actData.add(tempAct);
                                                            }
                                                        }

        }

        mNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mConditionTextView.setText(text);
                Log.d(TAG, "TESTING_LOG: " + dataSnapshot.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mButtonTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mNameRef.setValue("Coachella2");
            }
        });

    }
}
