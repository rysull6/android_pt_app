package com.app.ryan.ptapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;

import java.util.Calendar;


public class SettingsActivity extends Activity{
    private TimePicker timePicker1;
    private CheckBox disableCheck;
    private Button confirmButton;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        disableCheck = (CheckBox) findViewById(R.id.alarmCheckbox);
        confirmButton = (Button) findViewById(R.id.confirmAlarm);
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        Boolean disabled = prefs.getBoolean("disabled", false);
        int hour = prefs.getInt("hour", 17);
        int minute = prefs.getInt("minute", 0);
        disableCheck.setChecked(disabled);
        timePicker1.setCurrentHour(hour);
        timePicker1.setCurrentMinute(minute);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveInformation();
            }
        });
    }

    public void saveInformation()
    {
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmMgr.cancel(alarmIntent);
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit();

        if (!disableCheck.isChecked()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, timePicker1.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker1.getCurrentMinute());

            // With setInexactRepeating(), you have to use one of the AlarmManager interval
            // constants--in this case, AlarmManager.INTERVAL_DAY.
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);

            editor.putInt("hour", timePicker1.getCurrentHour());
            editor.putInt("minute", timePicker1.getCurrentMinute());

        }
        editor.putBoolean("disabled", disableCheck.isChecked());
        editor.apply();
        finish();
    }
}
