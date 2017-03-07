package com.takarki.nomi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

//FirebaseReceivedの中にあるOnReadFinishListenerというインターフェイスを継承するのでimplementsする
public class MainActivity extends AppCompatActivity implements FirebaseReceived.OnReadFinishListener{

    List<Card> mCards;
    CardAdapter mCardAdapter;
    ListView mListView;
    FirebaseReceived mFire;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.listView);
        mCards = new ArrayList<Card>();
        //FirebaseRecievedのコンストラクタを発動
        mFire = new FirebaseReceived();
        //FirebaseRecivedのリスナーを使いますよ．この画面ですよ．
        mFire.setListener(this);
        //読み込みを開始．
        mFire.read();
        //mFire.readd(mFire.saveduid);
        //CardAdatperのインスタンスを作成
        mCardAdapter = new CardAdapter(this, R.layout.card, mCards);

        //ListViewのsetAdapterメソッドでListViewにCardAdapterを関連付け
        mListView.setAdapter(mCardAdapter);

    }

    public void add(View v){
        Intent intent = new Intent(MainActivity.this,AddActivity.class);
        startActivity(intent);
    }

    public void addfriend(View v){
        Intent intent = new Intent(MainActivity.this,QRCodeActivity.class);
        startActivity(intent);
    }

    public void setting(View v){
//        Intent intent = new Intent(MainActivity.this,SettingActivity.class);
//        startActivity(intent);
    }

    //継承しているインターフェイスのメソッドをオーバーライド
    //Readの処理が終わったら自然に呼ばれる（というかFirebaseRecievedで設定した時に呼ばれる)
    @Override
    public void onReadFinish(String title,String state,String date,String local,String memo,String user) {
        Log.d("Finish", "FinishReadDatabase");
        //Log.d("Data", title);

        //mCards.addでリストに要素を追加する(引数は全てで４つで，画像，文字列*3)
        //例を代入している状態，Firebase導入後に変更
        if (title != null) {
            mCards.add(new Card(R.drawable.hito, title, state, date,local,memo,user));
        }

        //ListViewのsetAdapterメソッドでListViewにCardAdapterを関連付け
        mListView.setAdapter(mCardAdapter);
    }
}
