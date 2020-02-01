package com.codepath.festy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.codepath.festy.adapters.ActAdapter;
import com.codepath.festy.models.Act;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvArts;
    List<Act> actData; //Artist+time+*add button*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvArts=findViewById(R.id.rvArts);
        actData=new ArrayList<>();

        final ActAdapter actAdapter = new ActAdapter(this, actData);
        rvArts.setAdapter(actAdapter);
        rvArts.setLayoutManager(new LinearLayoutManager(this));



    }
}
