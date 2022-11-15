package com.example.application_6group;

import static android.media.MediaExtractor.MetricsConstants.FORMAT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class MainActivity extends AppCompatActivity {

    private  boolean timemanager = false;
    private static MainActivity instance = null;
    private static final String SAFETY_CONFIRMATION = "SAFETY_CONFIRMATION";
    private static final String TAG = "MainActivity";
    private AlarmManager am;
    private PendingIntent pending;
    private  int requestCode;
    private DatabaseReference mDatabase;
    String blue     = "#2196F3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        instance = this;
        timemanager = false;

        startService( new Intent( MainActivity.this, TestService.class ) );
        ReadAndWriteSnippets readAndWriteSnippets = new ReadAndWriteSnippets();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        readAndWriteSnippets.addNewUser("Kei"); //("名前") 名前をステータスsafetyで登録 後で良きところに置く

        if (/*歩行感知センサーまたは通知を押したとき*/true)
        {
            //色変更
            ImageView image_1 = (ImageView) findViewById(R.id.image_favorite_1);
            image_1.setColorFilter(Color.parseColor(blue), PorterDuff.Mode.SRC_IN);
            TestService.checksurvival = LocalDateTime.now().parse("yyyy-MM-dd HH：mm：ss",DateTimeFormatter.ofPattern(FORMAT));
        }
    }

    public void OnPouse(){
        stopService( new Intent( MainActivity.this, TestService.class ));
    }

    public static MainActivity getInstance() {
        return instance;
    }
}