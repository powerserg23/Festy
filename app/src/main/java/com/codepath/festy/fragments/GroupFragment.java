package com.codepath.festy.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.festy.R;
import com.codepath.festy.adapters.ActAdapter;
import com.codepath.festy.adapters.GroupProfileAdapter;
import com.codepath.festy.models.Act;
import com.codepath.festy.models.Profile;
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
public class GroupFragment extends Fragment implements GroupProfileAdapter.onMemberListener {
    final String TAG = "GroupFragment";
    RecyclerView rvProfs;
    List<Profile> profData;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mFestivalRef = mRootRef.child("festival");
    DatabaseReference mCoachellaRef = mFestivalRef.child("0");
    DatabaseReference mUsersRef = mCoachellaRef.child("users");
    SharedPreferences settings;




    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvProfs=view.findViewById(R.id.rvProfs);
        profData=new ArrayList<>();
        settings = getContext().getSharedPreferences("prefs", MODE_PRIVATE);
        addData();


    }

    protected void addData(){
        mUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot actSnapshot : dataSnapshot.getChildren()) {
                    Profile tempAct = new Profile(actSnapshot.child("name").getValue(String.class));
                    profData.add(tempAct);
                    Log.d(TAG, "LOG- " + tempAct.getName());
                }

                final GroupProfileAdapter groupProfileAdapter = new GroupProfileAdapter(getContext(), profData,mUsersRef,GroupFragment.this);
                rvProfs.setAdapter(groupProfileAdapter);
                rvProfs.setLayoutManager(new LinearLayoutManager(getContext()));
                groupProfileAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void onMemberClick(int position) {
        //TODO: Navigate to the clicked profile's profile
        Profile selectedProf=profData.get(position);
    }
}
