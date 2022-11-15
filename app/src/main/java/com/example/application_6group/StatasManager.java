package com.example.application_6group;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.ImageView;

public class StatasManager {
    String yellow   = "#FFEB3B";
    String red      = "#F44336";

    public void TwentyfourHours(){
        ImageView image_1 = (ImageView) findViewById(R.id.image_favorite_1);
        image_1.setColorFilter(Color.parseColor(yellow), PorterDuff.Mode.SRC_IN);

        readAndWriteSnippets.changeUserStatus("ユーザー名", "caution"); // ("名前", "ステータス") 名前を指定されたステータスに変更 //最初大文字？確認必要
        requestCode = 1;

        Context context = MainActivity.getInstance();

        Intent intent = new Intent(context, Notification.class);
        intent.setAction(SAFETY_CONFIRMATION);
        intent.putExtra("RequestCode",requestCode);
        intent.putExtra("StatusChangeUser", "ユーザー名");
        context.sendBroadcast(intent);
    }

    public void FortyeightHours(){
        //48時間経過後の処理内容

        //色変更
        ImageView image_1 = (ImageView) findViewById(R.id.image_favorite_1);
        image_1.setColorFilter(Color.parseColor(red), PorterDuff.Mode.SRC_IN);

        readAndWriteSnippets.changeUserStatus("ユーザー名", "dangerous");
    }
}
