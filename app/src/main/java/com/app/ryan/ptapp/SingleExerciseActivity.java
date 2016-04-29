package com.app.ryan.ptapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Ryan on 3/7/2016.
 */
public class SingleExerciseActivity extends AppCompatActivity {
    TextView titleView;
    TextView infoView;
    ImageView image;
    TextView repView;
    TextView holdView;
    TextView completeView;
    TextView performView;
    TextView timeView;
    ArrayList<Exercise> _exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_exercise);
        titleView = (TextView) findViewById(R.id.single_title);
        image = (ImageView) findViewById(R.id.single_image);
        infoView = (TextView) findViewById(R.id.single_info);
        repView = (TextView) findViewById(R.id.single_rep_count);
        holdView = (TextView) findViewById(R.id.single_hold_count);
        completeView = (TextView) findViewById(R.id.single_complete_count);
        performView = (TextView) findViewById(R.id.single_perform_count);
        timeView = (TextView) findViewById(R.id.single_time_count);

        _exerciseList = ExerciseListSingleton.getInstance().getData();
        Intent intent = getIntent();
        int position = intent.getExtras().getInt("position");

        Exercise currentExercise = _exerciseList.get(position);

        titleView.setText(currentExercise.getTitle());
        new GetImageFromURL(image).execute(currentExercise.getImageURL());
        infoView.setText(currentExercise.getInfo());
        repView.setText(currentExercise.getRepCount());
        holdView.setText(currentExercise.getHoldCount());
        completeView.setText(currentExercise.getCompleteCount());
        performView.setText(currentExercise.getPerformCount());
        timeView.setText(currentExercise.getTimeCount());

    }
}