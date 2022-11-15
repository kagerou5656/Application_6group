package com.example.application_6group;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Timer;

public class TestService extends Service {
    public LocalDateTime now;
    static LocalDateTime checksurvival;
    final String FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("BindService", "StartCount");
        while (true) {
            try {
                Thread.sleep(1000);//１秒
                now = LocalDateTime.now().parse("yyyy-MM-dd HH：mm：ss", DateTimeFormatter.ofPattern(FORMAT));
                long timediff = ChronoUnit.HOURS.between(now, checksurvival); //(1,2)で1と2の差を求めてる

                if (timediff == 24){//24時間以上反応がないとき
                    Log.d("TestService","24h");
                    StatasManager statasManeger = new StatasManager();
                    statasManeger.TwentyfourHours();
                }else if(timediff == 48) {//48時間以上反応ないとき
                    Log.d("TestService","48h");
                    StatasManager statasManeger = new StatasManager();
                    statasManeger.FortyeightHours();
                }
            } catch (InterruptedException e) {
            }
        }
    }


    //最初のbindService呼び出しのみ、システムにIBinderインターフェースを渡すために呼ばれる。
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*Serviceのインスタンスがない状態で、クライアントがstartServiceまたはbindServiceを呼んだ時に
     *Serviceのインスタンス生成で呼ばれる。すでにインスタンスが存在している場合は呼ばれない。
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /*インドしているクライアントが「全て」いなくなったとき。そのためunbindServiceが呼ばれても、
     *ほかにバインドしているクライアントが存在した場合、onUnbindは呼ばれない。
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    //バインドされたクライアントがなくなって、onUnbindが呼ばれたあとに呼ばれる
    @Override
    public void onDestroy() {
    }

    private class MyBinder {
    }
}