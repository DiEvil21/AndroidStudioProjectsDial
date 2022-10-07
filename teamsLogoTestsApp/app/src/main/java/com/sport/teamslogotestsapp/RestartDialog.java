package com.sport.teamslogotestsapp;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class RestartDialog  extends DialogFragment {
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        Dialog dialog = builder.setView(R.layout.restart_layout).create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;

    }
}
