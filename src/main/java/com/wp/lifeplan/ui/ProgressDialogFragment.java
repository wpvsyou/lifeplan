/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wp.lifeplan.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Simple dialog that shows progress as we work through the settings checks.
 * This is stateless except for its UI (e.g. current strings) and can be torn down or
 * recreated at any time without affecting the account checking progress.
 */
public class ProgressDialogFragment extends DialogFragment {
    public final static String TAG = "CheckProgressDialog";

    private final static String ARGS_MESSAGE = "args_message";

    // Public no-args constructor needed for fragment re-instantiation
    public ProgressDialogFragment() {
    }

    /**
     * Create a dialog that reports progress
     *
     * @param message check settings mode
     */
    public static ProgressDialogFragment newInstance(String message) {
        final ProgressDialogFragment f = new ProgressDialogFragment();
        final Bundle b = new Bundle(1);
        b.putString(ARGS_MESSAGE, message);
        f.setArguments(b);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context context = getActivity();

        // Don't bail out if the user taps outside the progress window
        setCancelable(false);

        String message = "";
        if (getArguments() != null) {
            message = getArguments().getString(ARGS_MESSAGE);
        }

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        return dialog;
    }
}
