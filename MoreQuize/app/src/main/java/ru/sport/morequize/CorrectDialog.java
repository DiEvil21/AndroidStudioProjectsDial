package ru.sport.morequize;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CorrectDialog extends DialogFragment {
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle(Html.fromHtml("<font color='#29a630'>Correct answer!</font>"))
                .setPositiveButton("NEXT", null)
                .create();
    }
}
