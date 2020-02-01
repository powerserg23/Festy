package com.codepath.festy.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Act {
    String time;
    String name;
    String stage;
    public Act(){};
    public Act(JSONObject jsonObject) throws JSONException
    {
        time=jsonObject.getString("time");
        stage=jsonObject.getString("stage");
        name=jsonObject.getString("name");
    }

    public static List<Act> fromJsonArray(JSONArray actJsonArray) throws JSONException
    {
        List<Act> acts=new ArrayList<>();
        for(int i=0;i<actJsonArray.length();i++)
        {
            acts.add(new Act(actJsonArray.getJSONObject(i)));
        }
        return acts;
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
