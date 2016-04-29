package com.app.ryan.ptapp;

import java.util.ArrayList;

/**
 * Created by Ryan on 2/24/2016.
 */
public class ExerciseListSingleton {

    private ArrayList<Exercise> data;

    public ArrayList<Exercise> getData()
    {
        return data;
    }

    public void setData(ArrayList<Exercise> data)
    {
        this.data = data;
    }

    private static final ExerciseListSingleton list = new ExerciseListSingleton();
    public static ExerciseListSingleton getInstance()
    {
        return list;
    }
}
