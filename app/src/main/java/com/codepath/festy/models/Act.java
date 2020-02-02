package com.codepath.festy.models;

public class Act {
    String time;
    String name;
    String stage;

    public Act(){};

    public Act(String name,String time,String stage)
    {
       this.time = time;
       this.name = name;
       this.stage = stage;
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
