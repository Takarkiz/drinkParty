package com.takarki.nomi;

import android.content.Intent;
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
import com.google.firebase.auth.UserProfileChangeRequest;

public class DetailSetActivity extends AppCompatActivity {

    EditText adanaEdit;

    String adana;

    private static final String TAG = "DetailSet時";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    SendUserData msud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_set);

        adanaEdit = (EditText)findViewById(R.id.adanaEdit);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "ログイン中" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG,"ログイン中のユーザーはいません");
                }
                // ...
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
        mAuth.removeAuthStateListener(mAuthListener);
    }

    public void changedInfo(View v){
        adana = adanaEdit.getText().toString();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(adana)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                            msud = new SendUserData();
                            msud.post(user.getUid());
                            intentList();
                        }else{
                            Toast.makeText(DetailSetActivity.this, "失敗.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void intentList(){
        Intent intent = new Intent(DetailSetActivity.this,MainActivity.class);
        startActivity(intent);
    }


}
