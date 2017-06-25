package com.wp.lifeplan.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by wangpeng on 2017/5/21.
 */

public class DecisionDialogFragment extends DialogFragment {
    private final static String ARGS_MESSAGE = "args_message";
    private final static String ARGS_CALLBACK = "args_callback";

    public interface Callback {
        void userSelectedYes();

        void userSelectedNo();
    }

    private static Callback CALLBACK;

    public final static DecisionDialogFragment newInstance(String message, Callback callback) {
        DecisionDialogFragment f = new DecisionDialogFragment();
        Bundle b = new Bundle(2);
        b.putString(ARGS_MESSAGE, message);
        CALLBACK = callback;
        f.setArguments(b);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (getArguments() != null) {
            builder.setMessage(getArguments().getString(ARGS_MESSAGE));
        }
        builder.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (CALLBACK != null) {
                    CALLBACK.userSelectedYes();
                }
                dismiss();
            }
        });
        builder.setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (CALLBACK != null) {
                    CALLBACK.userSelectedNo();
                }
                dismiss();
            }
        });
        builder.setCancelable(false);
        return builder.create();
    }
}
