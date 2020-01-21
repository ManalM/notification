package com.example.notification;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String CHANNEL_1_ID = "channel1";
    SharedPreferences.Editor pref;

    SharedPreferences getPref;
    TextView timePickerValueTextView;
    Calendar now;
    private NotificationManagerCompat notificationManager;
    Button button, picker;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // notificationManager = NotificationManagerCompat.from(this);

        createNotificationChannels();
        timePickerValueTextView = (TextView) findViewById(R.id.timePickerValue);

        pref = getSharedPreferences("MY_PREFS", MODE_PRIVATE).edit();


        getPref = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        now = Calendar.getInstance();
        picker = findViewById(R.id.picker);
        button = findViewById(R.id.button);

        if (getPref.getInt("hour", 00) != 0 || getPref.getInt("min", 00) != 0) {
            timePickerValueTextView.setText(getPref.getInt("hour", 00) + ":" + getPref.getInt("min", 00));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent broadcastIntent = new Intent(MainActivity.this, NotificationClass.class);

                PendingIntent actionIntent = PendingIntent.getBroadcast(MainActivity.this,
                        0, broadcastIntent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                now.set(Calendar.HOUR_OF_DAY, getPref.getInt("hour", 00));
                now.set(Calendar.MINUTE, getPref.getInt("min", 00));

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(), AlarmManager.INTERVAL_DAY, actionIntent);
                Toast.makeText(MainActivity.this, getPref.getInt("hour", 00) +":"+getPref.getInt("min", 00), Toast.LENGTH_SHORT).show();

            }
        });

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    /*            final Dialog builder = new Dialog(MainActivity.this);
                builder.setContentView(R.layout.time_picker);
                builder.show();
                showTimePickerDialog(v);
                close= builder.findViewById(R.id.close);
                check = builder.findViewById(R.id.check);
                timePicker = builder.findViewById(R.id.time_picker);
                h= timePicker.getHour();
                m = timePicker.getMinute();

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });

            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/


                showTimePickerDialog();
            }
        });

    }

    public void sendOnChannel1(View v) {

/*        Calendar c = Calendar.getInstance();

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Intent broadcastIntent = new Intent(this, NotificationClass.class);
        broadcastIntent.putExtra("toastMessage", "hello");
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, "channel1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("title")
                .setContentText("how are you?")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build();

        notificationManager.notify(1, notification);*/


    }

    private void createNotificationChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
      }else{
            Toast.makeText(this, "the sdk is less than this action: "+Build.VERSION.SDK_INT, Toast.LENGTH_SHORT).show();
        }
    }

    private void showTimePickerDialog() {

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                pref.putInt("hour", hour);
                pref.putInt("min", minute);
                pref.commit();
                timePickerValueTextView.setText(getPref.getInt("hour", 00) + ":" + getPref.getInt("min", 00));

            }
        };


        int hour = now.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = now.get(java.util.Calendar.MINUTE);

        // Whether show time in 24 hour format or not.
        boolean is24Hour = true;

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog, onTimeSetListener, hour, minute, is24Hour);

        timePickerDialog.show();
    }

}
