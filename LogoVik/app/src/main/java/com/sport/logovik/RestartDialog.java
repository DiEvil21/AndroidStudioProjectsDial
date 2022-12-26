package com.sport.logovik;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;

public class RestartDialog  extends DialogFragment {
    ImageView iv_restart;
    TextView tv_answer;
    Button btn_restart;
    public void showDialog(Activity activity, String msg,String img){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.restart_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv_restart = dialog.findViewById(R.id.iv_restart);
        tv_answer = dialog.findViewById(R.id.tv_restart);
        btn_restart = dialog.findViewById(R.id.btn_restart);
        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        tv_answer.setText(msg);
        Glide.with(activity.getApplicationContext())
                .load(img)
                .into(iv_restart);
        dialog.show();

    }
}