package com.shsany.managerassistant.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by PC on 2017/10/19.
 */

public class ToastUtil {
    private static Toast toast;
    public static void showToast(Context context, int content){
        if (toast == null ){
            toast = Toast.makeText(context,content,Toast.LENGTH_SHORT);
        }else {
            toast.setText(content);
        }
        toast.show();
    }
}
