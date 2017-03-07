package com.takarki.nomi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Takarki on 2017/02/24.
 */


public class CardAdapter extends ArrayAdapter<Card> {
    List<Card> mCards;

    public CardAdapter(Context context,int layoutResourceId,List<Card> objects){
        super(context,layoutResourceId,objects);

        mCards = objects;
    }

    @Override
    public  int getCount(){
        return mCards.size();
    }

    @Override
    public Card getItem(int position){
        return mCards.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final ViewHoler viewHoler;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card,null);
            viewHoler = new ViewHoler(convertView);
            convertView.setTag(viewHoler);
        }else{
            viewHoler = (ViewHoler)convertView.getTag();
        }

        final Card item = getItem(position);

        if(item != null){
            //set data
            viewHoler.iconImageView.setBackgroundResource(item.imageId);
            viewHoler.titleTextView.setText(item.title);
            viewHoler.contentTextView.setText("場所"+item.local);
            viewHoler.stateTextView.setText(item.state);
            viewHoler.dateTextView.setText("時間:"+item.date);
            viewHoler.memoTextView.setText(item.memo);
            viewHoler.userTextView.setText(item.user);
            viewHoler.likeButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    //item.likeNum++;
                    //viewHoler.likeTextView.setText(item.likeNum+"Likes");
                    Log.d("リストが","タップされました");
                }
            });
            viewHoler.iconImageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //Toast.makeText(getContext(),item.meaning,Toast.LENGTH_SHORT).show();
                }
            });

        }

        return convertView;
    }

    private class ViewHoler{
        ImageView iconImageView;
        TextView titleTextView;
        TextView dateTextView;
        TextView contentTextView;
        ImageView likeButton;
        TextView memoTextView;
        TextView stateTextView;
        TextView userTextView;

        public ViewHoler(View view){
            //get instance
            iconImageView = (ImageView)view.findViewById(R.id.icon);
            titleTextView = (TextView)view.findViewById(R.id.title_textview);
            stateTextView = (TextView)view.findViewById(R.id.content);
            dateTextView = (TextView)view.findViewById(R.id.like_textView);
            contentTextView = (TextView)view.findViewById(R.id.localText);
            memoTextView = (TextView)view.findViewById(R.id.memoText);
            userTextView = (TextView)view.findViewById(R.id.userName);
        }
    }

}
