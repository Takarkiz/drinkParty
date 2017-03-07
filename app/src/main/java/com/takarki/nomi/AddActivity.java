package com.takarki.nomi;

import android.app.DialogFragment;
import android.app.job.JobScheduler;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {

    //editTextの宣言
    EditText titleText;
    EditText stateText;
    EditText dateText;
    EditText placeText;
    EditText memoText;
    
    FirebaseSend mfire;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //テキストの初期化
        titleText = (EditText)findViewById(R.id.nameEditText);
        stateText = (EditText) findViewById(R.id.nomiEdit);
        dateText = (EditText) findViewById(R.id.dateEdit);
        placeText = (EditText)findViewById(R.id.editText4);
        memoText = (EditText)findViewById(R.id.editText5);

        Log.d("ユーザー名:" ,mfire.user.getDisplayName());
    }

    //度数を示すeditのテキストをタップした時
    public void nomiStateEdit(View v){
        DialogFragment dialog = new StateDialogFragment();
        dialog.show(getFragmentManager(),"dialog_basic");

    }

    public void dateDialog(View v){
        DialogFragment dialog = new DateDialogFragment();
        dialog.show(getFragmentManager(),"dialog_basic");

    }

    //OKボタンタップ時の処理
    public void editDone(View v){
        mfire = new FirebaseSend(titleText.getText().toString(),
                stateText.getText().toString(),
                dateText.getText().toString(),
                placeText.getText().toString(),
                memoText.getText().toString());

        mfire.post();

        //画面線を行う
        toListView();
    }

    //画面遷移
    public void toListView(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
