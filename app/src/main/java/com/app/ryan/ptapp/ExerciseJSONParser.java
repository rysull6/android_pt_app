package com.app.ryan.ptapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ryan on 4/13/2016.
 */
public class ExerciseJSONParser {

    public ExerciseJSONParser(){}

    public ArrayList<Exercise> parseJson(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        JSONArray exercises = jsonObject.getJSONArray("exercises");
        ArrayList<Exercise> objectList = new ArrayList<Exercise>();
        for(int i=0; i<exercises.length()-1; i++){
            String lines[] =  exercises.getJSONObject(i).getString("description").split("\\r?\\n");
            String title =lines[0];
            String info = lines[2];
            String img_url = exercises.getJSONObject(i).getString("image");
            String repCount = exercises.getJSONObject(i).getString("repeat");
            String holdCount = exercises.getJSONObject(i).getString("hold");
            String completeCount = exercises.getJSONObject(i).getString("complete");
            String performCount = exercises.getJSONObject(i).getString("perform");
            String timeCount = exercises.getJSONObject(i).getString("time");
            objectList.add(new Exercise(title, info, img_url, repCount, holdCount, completeCount, performCount, timeCount));
        }

        return objectList;
    }
}
