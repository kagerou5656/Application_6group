package com.example.application_6group;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ReadAndWriteSnippets {
    private static final String TAG = "ReadAndWriteSnippets";
    private static final String SAFETY_CONFIRMATION = "SAFETY_CONFIRMATION";
    private static final String DATABASE_URL = "https://application-6group-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private int requestCode = 0;
    private DatabaseReference mDatabase;

    public ReadAndWriteSnippets() {
        mDatabase = FirebaseDatabase.getInstance(DATABASE_URL).getReference("users");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            //データベースに変更があったとき起動
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Object status = dataSnapshot.getValue();
                String statusChangedUser = dataSnapshot.getKey();

                System.out.println(status);
                System.out.println(statusChangedUser);

                if (status == "dangerous") requestCode = 2;
                else return;

                Context context = MainActivity.getInstance();

                Intent intent = new Intent(context, Notification.class);
                intent.setAction(SAFETY_CONFIRMATION);
                intent.putExtra("RequestCode",requestCode);
                intent.putExtra("StatusChangeUser", statusChangedUser);
                context.sendBroadcast(intent);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //ユーザー追加 ユーザー名を指定してステータスsafetyで登録
    public void addNewUser(String userName) {
        Map<String, Object> userData = new HashMap<>();
        userData.put(userName, "safety");

        mDatabase.updateChildren(userData);
    }

    //ステータス変更 指定されたユーザー(userName)のステータスを指定されたステータス(status)に変更
    public void changeUserStatus(String userName, String status) {
        Map<String, Object> changeUserStatus = new HashMap<>();
        changeUserStatus.put(userName, status);

        mDatabase.updateChildren(changeUserStatus);
    }

}
