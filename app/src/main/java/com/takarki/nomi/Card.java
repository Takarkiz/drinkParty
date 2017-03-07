package com.takarki.nomi;

/**
 * Created by Takarki on 2017/02/24.
 */

public class Card {
    public int imageId;
    public String title;
    public String date;
    public String local;
    public String memo;
    public String state;
    public String user;


    //コンストラクタ(初期化作業)
    public Card(int imageId,String title, String state,String date,String local, String memo,String user){
        this.imageId = imageId;
        this.title = title;
        this.state = state;
        this.date = date;
        this.local = local;
        this.memo = memo;
        this.user = user;
    }


}