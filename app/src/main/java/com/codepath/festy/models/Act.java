package com.codepath.festy.models;

public class Act {
    String time;
    String name;
    String stage;
    String index;
    boolean going;

    public Act(){};

    public Act(String name,String time,String stage,String index, boolean going)
    {
       this.time = time;
       this.name = name;
       this.stage = stage;
       this.index=index;
       this.going = going;

    }
    public Act(String name,String time, String stage)
    {
        this.name=name;
        this.time=time;
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

    public String getIndex() {
        return index;
    }

    public boolean getGoing() {
        return going;
    }
}
