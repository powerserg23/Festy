package com.codepath.festy.fragments;


import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.festy.MainActivity;
import com.codepath.festy.R;
import com.codepath.festy.adapters.ActAdapter;
import com.codepath.festy.models.Act;
import com.codepath.festy.models.User;
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
    private AlertDialog logDialog;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mCoachellaRef = mRootRef.child("festival").child("0");
    DatabaseReference mScheduleRef = mCoachellaRef.child("schedule");
    DatabaseReference mUsersRef = mCoachellaRef.child("users");
    DatabaseReference mGroupsRef = mCoachellaRef.child("groups");

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

        // Loads the acts from firebase and adds to adapter
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

                final ActAdapter actAdapter = new ActAdapter(getContext(), actData, mScheduleRef);
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

        final ArrayList<String> usersList = new ArrayList<String>();

        if (settings.getBoolean("FirstLaunch", true)) {
            // first time launching

            // Load all of the usernames, and store in a list
            // this ensures we do not duplicate usernames
            mUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount() > 0) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot nameSnapshot : userSnapshot.getChildren()) {
                                Log.i(TAG, "add user:  " + nameSnapshot.getValue());
                                usersList.add(nameSnapshot.getValue().toString());
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


            final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setCancelable(false);
            alert.setTitle("Hello! Welcome to Festy!");
            alert.setMessage("Please enter a username");
            alert.setPositiveButton("Enter",null);
            final EditText input = new EditText(getContext());

            alert.setView(input);
            final AlertDialog diag = alert.create();

            diag.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);

                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            final String value = String.valueOf(input.getText());

                            if(value.isEmpty()) {
                                Toast.makeText(getContext(),"Please enter a username",Toast.LENGTH_SHORT).show();
                            }
                            else if(usersList.contains(value)) {
                                Toast.makeText(getContext(),"Username already exists",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // If we change the user member variables, it will change what gets stored in the database
                                User user = new User();
                                user.setName(value);

                                // Using 'push()' allows for unique ID to be generated
                                mUsersRef.push().setValue(user);

                                editor.putString("name", value);
                                editor.putBoolean("FirstLaunch", false);
                                editor.apply();
                                diag.dismiss();
                            }
                        }
                    });
                }
            });
            diag.show();
        }
    }
}
