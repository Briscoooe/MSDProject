package com.example.msdproject;

public class Concert {

    private int id;
    private String name;
    private String venue;
    private String date;
    private String comments;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    //
    public String getVenue()
    {
        return venue;
    }

    public void setVenue(String venue)
    {
        this.venue = venue;
    }


    //
    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    //
    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return name;
    }
}