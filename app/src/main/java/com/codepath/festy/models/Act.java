package com.codepath.festy.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Act {
    String time;
    String name;
    String stage;
    public Act(){};
    public Act(String name,String time,String stage)
    {
   this.time=time;
   this.name=name;
   this.stage=stage;
    }



    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getStage() {
        return stage;
    }
}
