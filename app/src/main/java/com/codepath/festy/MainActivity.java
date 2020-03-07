package com.codepath.festy;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.festy.fragments.ListFragment;
import com.codepath.festy.adapters.ActAdapter;
import com.codepath.festy.models.Act;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //RecyclerView rvArts;
    //List<Act> actData;

    //DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    //DatabaseReference mFestivalRef = mRootRef.child("festival");
    //DatabaseReference mCoachellaRef = mFestivalRef.child("0");
    //DatabaseReference mScheduleRef = mCoachellaRef.child("schedule");

    final String TAG = "MainActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    //SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //rvArts = findViewById(R.id.rvArts);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        //actData = new ArrayList<>();

        //settings = getSharedPreferences("prefs", MODE_PRIVATE);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()){
                    case R.id.action_list:
                        Toast.makeText(MainActivity.this, "Main List", Toast.LENGTH_SHORT).show();
                        fragment = new ListFragment();
                        break;
                    case R.id.action_group:
                        Toast.makeText(MainActivity.this, "Group Page", Toast.LENGTH_SHORT).show();
                        fragment = new ListFragment();
                        break;
                    case R.id.action_profile:
                    default:
                        Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        fragment = new ListFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_list);

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
