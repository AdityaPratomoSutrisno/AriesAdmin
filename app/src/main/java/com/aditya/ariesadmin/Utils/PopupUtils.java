package com.aditya.ariesadmin.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import com.aditya.ariesadmin.R;

public class PopupUtils {

    Context context;
    View viewtoastsucces, viewtoastinfo, viewtoastfailed;
    Dialog dialogloading;
    ToastUtils toastUtils;
    Boolean isdialogshow1 = false;

    public PopupUtils(Context context, View viewtoastsucces, View viewtoastinfo, View viewtoastfailed) {
        this.context = context;
        toastUtils = new ToastUtils(context);
        this.viewtoastsucces = viewtoastsucces;
        this.viewtoastinfo = viewtoastinfo;
        this.viewtoastfailed = viewtoastfailed;
    }

    public void showtoastsucces(String text){
        final TextView tvtoastsuccess = viewtoastsucces.findViewById(R.id.tvtoast);

        tvtoastsuccess.setText(text);
        toastUtils.toastbottomshort(viewtoastsucces);
    }

    public void showtoastinfo(String text){
        final TextView tvtoastinfo = viewtoastinfo.findViewById(R.id.tvtoast);

        tvtoastinfo.setText(text);
        toastUtils.toastbottomshort(viewtoastinfo);
    }

    public void showtoastfailed(String text){
        final TextView tvtoastfailed = viewtoastfailed.findViewById(R.id.tvtoast);

        tvtoastfailed.setText(text);
        toastUtils.toastbottomshort(viewtoastfailed);
    }
}
