package com.takarki.nomi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by Takarki on 2017/02/26.
 */

public class StateDialogFragment extends DialogFragment {
    int selected = 0;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //配列
        final String[] items = {"かなり行きたい！","ゆる〜く募集","気が向いたら連絡して！"};
        //ダイアログを生成
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //ダイアログの設定
        return builder.setTitle("頻度").setSingleChoiceItems(items,selected,
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        selected = which;
                    }
                }
        )
                //OKボタンを生成
        .setPositiveButton("OK",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        EditText nomitai = (EditText)getActivity().findViewById(R.id.nomiEdit);
                        nomitai.setText(items[selected]);
                    }
                }
        ).create();

    }
}
