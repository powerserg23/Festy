package com.codepath.festy.models;

public class Act {
    String time;
    String name;
    String stage;
    String index;

    public Act(){};

    public Act(String name,String time,String stage,String index)
    {
       this.time = time;
       this.name = name;
       this.stage = stage;
       this.index=index;


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
}
