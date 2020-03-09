package com.codepath.festy;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.codepath.festy.fragments.GroupFragment;
import com.codepath.festy.fragments.ListFragment;
import com.codepath.festy.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()){
                    case R.id.action_list:
                        fragment = new ListFragment();
                        break;
                    case R.id.action_group:
                        fragment = new GroupFragment();
                        break;
                    case R.id.action_profile:
                    default:
                        fragment = new ProfileFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        //bottomNavigationView.setSelectedItemId(R.id.action_list);

    }

    protected void onStart() {
        super.onStart();
        bottomNavigationView.setSelectedItemId(R.id.action_list);

    }
/*
    protected void onStart(){
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

    protected void onResume() {
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

 */
}
