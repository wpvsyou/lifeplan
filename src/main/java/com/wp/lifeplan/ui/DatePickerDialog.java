package com.wp.lifeplan.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.wp.lifeplan.R;

import java.util.Calendar;
import java.util.Date;

import static com.wp.lifeplan.ui.CollectTimeLinearView.SDF_LOG;

/**
 * Created by wangpeng on 2017/6/11.
 */

public class DatePickerDialog extends DialogFragment {
    private final static String TAG = "DatePickerDialog";

    public interface Callback {
        void onDateSelected(long millisecond);
    }

    public static DatePickerDialog newInstance(Callback callback, Context context) {
        DatePickerDialog.callback = callback;
        return new DatePickerDialog();
    }

    private static Callback callback;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.date_dialog_fragment, null);
        builder.setView(root);
        final DatePicker datePicker = (DatePicker) root.findViewById(R.id.datePicker);
        builder.setCancelable(false);
        builder.setNegativeButton(android.R.string.cancel, null);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int day = datePicker.getDayOfMonth();
                int mouth = datePicker.getMonth();
                int year = datePicker.getYear();
                Calendar now = Calendar.getInstance();
                now.set(Calendar.YEAR, year);
                now.set(Calendar.MONTH, (mouth));
                now.set(Calendar.DAY_OF_MONTH, day);
                Log.i(TAG, String.format("check selected date: year[%s], mouth[%s], day[%s]%n" +
                        "check format Calendar[%s]", day, mouth + 1, year, SDF_LOG.format(new
                        Date(now.getTimeInMillis()))));
                if (callback != null) {
                    callback.onDateSelected(now.getTimeInMillis());
                }
                dismiss();
            }
        });

        return builder.create();
    }
}
