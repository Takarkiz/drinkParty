package com.takarki.nomi;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Takarki on 2017/03/01.
 */

//Fiebaseを用いてデータを受け取る処理をここに書きます
public class FirebaseReceived {

    final String TAG = "Firebase受け取り中:";

    //user情報を得る
    //Firebaseのインスタンスの生成
    private FirebaseAuth mAuth;
    public FirebaseUser user;
    //受け取るのに必要
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");
    SendUserData sud;


    //ユーザーiD
    String uid;
    //保存されたUid
    String saveduid;

    //要素のインスタンス
    String title;
    String state;
    String date;
    String local;
    String memo;
    String adana;

    //読み取り終了時に『終わったよ』と知らせてほしいのでインスタンス生成する
    private OnReadFinishListener mListener;

    //リスナーの初期化(リスナーを使う準備)
    public void setListener(OnReadFinishListener mListener) {
        this.mListener = mListener;
    }

    //メインのクラス内に読み取りが完了したことを知らせるリスナーのインターフェイスを設置
    interface OnReadFinishListener {
        void onReadFinish(String title,String state,String date, String local, String memo,String user);
    }

    public FirebaseReceived(){

        //保存したuidを呼ぶ
        sud = new SendUserData();
        //ユーザー情報
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if(user != null){
            Log.d(TAG,"ユーザーがログイン中"+user.getDisplayName());
            uid = user.getUid();
        }else {
            Log.d(TAG, "ログイン中のユーザーはいません");
        }

        sud.checkList();
        myRef = database.getReference();
    }

    public void read(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG, "event");
                title = dataSnapshot.child("event").child(uid).child("title").getValue(String.class);
                state = dataSnapshot.child("event").child(uid).child("state").getValue(String.class);
                date = dataSnapshot.child("event").child(uid).child("date").getValue(String.class);
                local = dataSnapshot.child("event").child(uid).child("local").getValue(String.class);
                memo = dataSnapshot.child("event").child(uid).child("memo").getValue(String.class);
                adana = dataSnapshot.child("event").child(uid).child("user").getValue(String.class);

                //titleを渡す
                mListener.onReadFinish(title,state,date,local,memo,adana);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //読み取り失敗時
                Log.w(TAG, "Failed to Read value", databaseError.toException());
            }
        });

//        return title == null ? "" : title;
    }
}
