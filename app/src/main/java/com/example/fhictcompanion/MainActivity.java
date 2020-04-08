package com.example.fhictcompanion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;

import com.example.fhictcompanion.News.ITaskReceiver;
import com.example.fhictcompanion.News.JSONTaskNews;
import com.example.fhictcompanion.News.JSONTaskNewsAmount;
import com.example.fhictcompanion.News.NewsActivity;
import com.example.fhictcompanion.News.NewsPost;
import com.example.fhictcompanion.Person.IPersonContext;
import com.example.fhictcompanion.Person.Person;
import com.example.fhictcompanion.Person.PersonTask;
import com.example.fhictcompanion.Schedule.IScheduleContext;
import com.example.fhictcompanion.Schedule.Schedule;
import com.example.fhictcompanion.Schedule.ScheduleActivity;
import com.example.fhictcompanion.Schedule.ScheduleDayAdapter;
import com.example.fhictcompanion.Schedule.ScheduleDayItem;
import com.example.fhictcompanion.Schedule.ScheduleItemTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IPersonContext, IScheduleContext, ITaskReceiver {
    private static final Date TODAY = new Date();

    private Schedule schedule;

    private int REQUESTCODE_GET_TOKEN = 1;
    private int REQUESTCODE_GET_NEWS_AMOUNT = 2;
    private int REQUESTCODE_GET_NEWS = 3;
    
    private String fontysToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayTodaysDate();

        Button btnReadNews = findViewById(R.id.read_news_button);
        btnReadNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("token", fontysToken);
                startActivity(intent);
            }
        });
        btnReadNews.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String title = "Fontys news downloading";
                String content = "Fontys news is being downloaded...";
                showNotification(title, content, null);
                new JSONTaskNews(MainActivity.this, REQUESTCODE_GET_NEWS).execute(fontysToken);
                return true;
            }
        });

        // Alternatief was geweest om de login activity als eerst te starten en na succesvolle login
        // een main activity aan te maken met token als extra.

        // FINAL (get token)

        Intent intent = new Intent(MainActivity.this, FontysLoginActivity.class);
        startActivityForResult(intent, REQUESTCODE_GET_TOKEN);

    }

    private void displayTodaysDate() {
        TextView dateDisplay = findViewById(R.id.day_date);
        SimpleDateFormat dayOfWeek = new SimpleDateFormat("EEEE");
        SimpleDateFormat dayOfMonth = new SimpleDateFormat("d");
        SimpleDateFormat month = new SimpleDateFormat("MMMM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");

        dateDisplay.setText("Today is " + dayOfWeek.format(TODAY) + ", " + dayOfMonth.format(TODAY) + " " + month.format(TODAY) + " " + year.format(TODAY));
    }

    public void viewSchedule(View view) {
        Intent scheduleIntent = new Intent(this, ScheduleActivity.class);

        ScheduleDayItem today = schedule.getToday();
        scheduleIntent.putExtra("schedule", schedule);

        startActivity(scheduleIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_GET_TOKEN) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("token");
                fontysToken = result;

                // Get person's information
                PersonTask personTask = new PersonTask(this);
                personTask.execute(fontysToken);

                // Get all the schedule items per day.
                ScheduleItemTask scheduleItemTask = new ScheduleItemTask(this);
                scheduleItemTask.execute(fontysToken);

                //Get amount of news posts
                new JSONTaskNewsAmount(this, REQUESTCODE_GET_NEWS_AMOUNT).execute(fontysToken);
            }
        }
    }

    @Override
    public void setPersonDetails(Person person) {
        TextView welcomeText = findViewById(R.id.welcome);
        welcomeText.setText("Welcome " + person.toString() + "!");
    }

    @Override
    public void setScheduleItems(Schedule schedule) {
        this.schedule = schedule;
        ListView lv = findViewById(R.id.schedule_days);
        lv.setAdapter(new ScheduleDayAdapter(this, schedule.getScheduleDays()));
    }

    @Override
    public void OnTaskReceived(Object data, int requestCode) {
        if(REQUESTCODE_GET_NEWS_AMOUNT == requestCode){
            if(data != null){
                if(data instanceof Integer){
                    int amount = (Integer)data;
                    TextView tvPostAmount = findViewById(R.id.news_amount);
                    if(amount <= -1){tvPostAmount.setText("No news posts could be retrieved");}
                    else {tvPostAmount.setText("There are " + amount + " news posts!");}
                }
            }
        }
        else if(REQUESTCODE_GET_NEWS == requestCode){
            if(data != null){
                if(data instanceof ArrayList){
                    List<NewsPost> list = (ArrayList<NewsPost>)data;
                    NewsPost newsPosts[] = list.toArray(new NewsPost[list.size()]);

                    Intent intent = new Intent(this, NewsActivity.class);
                    intent.putExtra("posts", newsPosts); //without byte array image (because > 1MB)
                    intent.putExtra("token", fontysToken);

                    String title = "Fontys news downloaded";
                    String content = "Click here to see " + list.size() + " news posts!";
                    showNotification(title, content, intent);
                    return;
                }
            }
            String title = "Fontys news failure";
            String content = "Downloading Fontys news failed!";
            showNotification(title, content, null);
        }
    }

    private void showNotification(String title, String content, Intent intent){

        //SETTING MANAGER AND CHANNEL
        NotificationManager notificationManager = (NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel("abc", "FHICT companion notifier", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(nc);
        }

        //SETTING NOTIFICATION SETTINGS
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "abc");
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setAutoCancel(true);

        //SETTING INTENT THAT NEEDS TO BE SHOWN WHEN NOTIFICATION IS CLICKED
        //THE PENDINGINTENT CAN NOT CARRY A INTENT WHICH IS BIGGER THAN 1MB IN SIZE!
        if(intent != null) {
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pendingIntent);
        }

        //BUILDING AND SENDING NOTIFICATION
        Notification notification = null;
        notification = builder.build();
        notificationManager.notify(9999, notification);
    }
}
