package com.takarki.nomi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by Takarki on 2017/02/26.
 */

public class FirebaseSend {


    String title;
    String state;
    String date;
    String local;
    String memo;

    SendUserData sud;

    HashMap<String,String> deic = new HashMap<String,String>();

    //Firebaseのインスタンスの生成
    private FirebaseAuth mAuth;
    public FirebaseUser user;
    FirebaseDatabase database;

    private DatabaseReference myRef;

    //ユーザーiD
    String uid;

    public FirebaseSend(String title,String state,String date,String local,String memo){
        this.title = title;
        this.state = state;
        this.date = date;
        this.local = local;
        this.memo = memo;

        sud = new SendUserData();

        //ユーザー情報
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if(user != null){
            Log.d(TAG,"ユーザーがログイン中"+user.getDisplayName());
            uid = user.getUid();
        }else{
            Log.d(TAG,"ログイン中のユーザーはいません");
        }
////
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("event");
    }

    public void post(){

        //mapにそれぞれのプロパティを代入
        deic.put("title",title);
        deic.put("state",state);
        deic.put("date",date);
        deic.put("local",local);
        deic.put("memo",memo);
        deic.put("user",user.getDisplayName());
        //値を送信
        myRef.child(uid).setValue(deic);
        //myRef.child(sud.uidList).setValue(deic);
    }

}
