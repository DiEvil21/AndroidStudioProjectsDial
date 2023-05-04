package ru.sport.morequize;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class FalseDialog extends DialogFragment {
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle(Html.fromHtml("<font color='#c73859'>Wrong answer!</font>"))
                .setPositiveButton("NEXT", null)
                .create();
    }
}
