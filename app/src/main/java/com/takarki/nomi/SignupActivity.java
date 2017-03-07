package com.takarki.nomi;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "サインイン時";
    EditText emailText;
    EditText passText;

    String mailAdress;
    String password;

    //Firebaseのインスタンスの生成
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailText = (EditText)findViewById(R.id.mailText);
        passText = (EditText)findViewById(R.id.passText);

        //ユーザー情報
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG,"ユーザーがログイン中\n画面遷移を行います");
                    String name = user.getDisplayName();
                    if (name != null) {
                        intent();
                    }
                }else{
                    Log.d(TAG,"ログイン中のユーザーはいません");

                }
            }
        };
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    //サインアップを行うメソッド
    public void signup(View v){
        mailAdress = emailText.getText().toString();
        password = passText.getText().toString();

        //サインインの処理を実行
        this.makeid(mailAdress,password);

    }

    public void makeid(String email,String pass){

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //成功した場合
                        Toast.makeText(SignupActivity.this,"Authentication ok",Toast.LENGTH_SHORT).show();
                        //サインイン終了後にログインの処理も行う
                        signin(mailAdress,password);
                        //失敗した場合
                        if(!task.isSuccessful()){
                            Toast.makeText(SignupActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signin(String email,String pass){
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("サインイン","signInWithEmail",task.getException());
                if (!task.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }else{
                    SignupActivity.this.setDetail();
                }
            }
        });
    }

    private void intent(){
        //画面遷移する
        Intent i = new Intent(SignupActivity.this,MainActivity.class);
        startActivity(i);
    }

    private void setDetail(){
        //画面遷移する
        Intent i = new Intent(SignupActivity.this,DetailSetActivity.class);
        startActivity(i);
    }

    public void login(View v){
        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
