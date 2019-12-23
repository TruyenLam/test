package com.it.quanlyxevai.api;

import android.content.Context;
import android.widget.Toast;

public class AppUtils {
    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
