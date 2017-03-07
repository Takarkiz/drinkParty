package com.takarki.nomi;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.key;
import static android.content.ContentValues.TAG;

/**
 * Created by Takarki on 2017/03/07.
 */

public class SendUserData {

    String adana;
    String uidList;

    //Firebaseのインスタンスの生成
    private FirebaseAuth mAuth;
    public FirebaseUser user;
    FirebaseDatabase database;

    private DatabaseReference myRef;

    String uid;

    public SendUserData(){

        //ユーザー情報
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if(user != null){
            Log.d(TAG,"ユーザーがログイン中"+user.getDisplayName());
        }else{
            Log.d(TAG,"ログイン中のユーザーはいません");
        }

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user");
        adana = user.getDisplayName();
        uid = user.getUid();
    }

    public void post(String xuid){
        uidList = xuid;
        myRef.child(uid).child("あだ名").setValue(adana);
        myRef.child(uid).child("uidList").setValue(uidList);
    }

    //QRコードを作成する際に用いる(->uidを返すメソッド)
    public String returnUid(){
        return uid;
    }

    //Listに保存されているデータがあるか確認したい
    public void checkList(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //データが更新されている時
                Log.d("データを確認中","受信してます");
//                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
//                uidList = dataSnapshot.child("user").child(uid).child("uidList").getValue(t);
//                if( uidList == null ) {
//                    System.out.println("No messages");
//                }
//                else {
//                    System.out.println("The first message is: " + uidList.get(0) );
//                }
                uidList = dataSnapshot.child("user").child(uid).child("uidList").getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
