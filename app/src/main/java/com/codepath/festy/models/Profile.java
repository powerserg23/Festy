package com.codepath.festy.models;

import android.widget.ImageView;

import java.util.List;

public class Profile {
    public String imageurl;
    private ImageView prof_image;
    private String name;
    private List<String> sets_attending;

    public Profile(String name,List<String>sets_attending)
    {
        this.name=name;
        this.sets_attending=sets_attending;
    }
    public Profile(String name,String imageurl)
    {
        this.name=name;
        this.imageurl=imageurl;
    }

    //this constructor is only used for testing before we have implemented an image for the profile
    public Profile(String name)
    {
        this.name=name;

    }

    public String getImageurl() {
        return imageurl;
    }

    public ImageView getProf_image() {
        return prof_image;
    }

    public String getName() {
        return name;
    }

    public List<String> getSets_attending() {
        return sets_attending;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setProf_image(ImageView prof_image) {
        this.prof_image = prof_image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSets_attending(List<String> sets_attending) {
        this.sets_attending = sets_attending;
    }
}
