package com.takarki.nomi;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Locale;

/**
 * Created by Takarki on 2017/02/26.
 */

public class DateDialogFragment extends DialogFragment {

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //現在の時刻を準備
        final Calendar cal = Calendar.getInstance();
        return new TimePickerDialog(
                getActivity(),
                new TimePickerDialog.OnTimeSetListener(){
                    //選択された時刻をテキストボックスに反映
                    public void onTimeSet(TimePicker view, int hourOdDay, int minute){
                        EditText dateText = (EditText)getActivity().findViewById(R.id.dateEdit);
                        dateText.setText(String.format(Locale.JAPAN,"%02d:%02d",hourOdDay,minute));
                    }
                },
                cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true
        );
    }
}
