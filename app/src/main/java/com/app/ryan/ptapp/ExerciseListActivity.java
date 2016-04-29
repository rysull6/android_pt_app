package com.app.ryan.ptapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class ExerciseListActivity extends AppCompatActivity {
    ListView _listView;
    ImageButton _settings;
    ArrayList<Exercise> _exerciseList;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _listView = (ListView) findViewById(R.id.list_container);
        _settings = (ImageButton) findViewById(R.id.settings);
        _settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSettings();
            }
        });
        _exerciseList = ExerciseListSingleton.getInstance().getData();
        handleAlarm();
        if(_exerciseList == null)
        {
            // make request and build invoice list object
            sendRequestForExerciseList();
        }
        else
        {
            // invoice list object already made
            setupAdapter();
        }
    }


    public void sendRequestForExerciseList()
    {
        new BuildDataObject(){
            @Override public void onPostExecute(String result)
            {
                try {
                    ExerciseJSONParser parser = new ExerciseJSONParser();
                    _exerciseList = parser.parseJson(result);
                    ExerciseListSingleton.getInstance().setData(_exerciseList);
                    setupAdapter();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}.execute();
    }

    public void setupAdapter()
    {
        ExerciseListAdapter adapter = new ExerciseListAdapter(getApplicationContext(), _exerciseList);
        _listView.setAdapter(adapter);
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                goToSingleExercise(position);
            }
        });
    }


    public void goToSingleExercise(int position)
    {
        Intent intent = new Intent(getApplicationContext(), SingleExerciseActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void goToSettings()
    {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    public void handleAlarm()
    {
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        Boolean firstSetup = prefs.getBoolean("firstSetup", true);
        if(firstSetup) {
            alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 17);
            calendar.set(Calendar.MINUTE, 0);

            // With setInexactRepeating(), you have to use one of the AlarmManager interval
            // constants--in this case, AlarmManager.INTERVAL_DAY.
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);

            SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit();
            editor.putBoolean("firstSetup", false);
            editor.putInt("hour", 17);
            editor.putInt("minute", 0);
            editor.putBoolean("disabled", false);
            editor.apply();
        }
    }
}

