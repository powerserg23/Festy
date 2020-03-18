package com.codepath.festy.fragments;


import android.os.Bundle;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 54;
    private TextView tvUsername;
    private ImageView ivUserImage;
    private Button btnUpload;
    private TextView tvLabel;
    private RecyclerView rvLineup;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mCoachellaRef = mRootRef.child("festival").child("0");
    DatabaseReference mScheduleRef = mCoachellaRef.child("schedule");
    DatabaseReference mUsersRef = mCoachellaRef.child("users");
    DatabaseReference mGroupsRef = mCoachellaRef.child("groups");
    List<Act> actData;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvUsername = view.findViewById(R.id.tvUsername);
        ivUserImage = view.findViewById(R.id.ivUserImage);
        btnUpload = view.findViewById(R.id.btnUpload);
        tvLabel = view.findViewById(R.id.tvLabel);
        rvLineup = view.findViewById(R.id.rvLineup);


       actData= new ArrayList<>();
        addData();
    }

        protected void addData(){
            mScheduleRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot actSnapshot : dataSnapshot.getChildren()) {
                        Act tempAct = new Act(actSnapshot.child("artist").getValue(String.class),
                                actSnapshot.child("time").getValue(String.class),
                                actSnapshot.child("stage").getValue(String.class));
                        actData.add(tempAct);
                        Log.d(TAG, "LOG- " + tempAct.getName());
                    }

                    final ActAdapter actAdapter = new ActAdapter(getContext(), actData, mScheduleRef,true);
                    rvLineup.setAdapter(actAdapter);
                    rvLineup.setLayoutManager(new LinearLayoutManager(getContext()));
                    actAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }

            });
        }



        //TODO: Get all Artists that the user selected and display
        //Step 1: get data from ListFragment
        //Step 2: filter data for only the artists that users selected
        //Step 3: put data into actData List
        //Step 4: display actData into rvLineup
        //Step 5: click button allows user to change profile picture (i.e change ivUserImage)
    }


