package com.shsany.managerassistant.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.shsany.managerassistant.base.ShSanyApplication;

/**
 * Created by PC on 2017/12/28.
 */

public class ToastUtils {
    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort( CharSequence message)
    {
        if (TextUtils.isEmpty(message)) {
            message = "";
        }
        if (isShow)
            Toast.makeText(ShSanyApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort( int message)
    {
        if (isShow)
            Toast.makeText(ShSanyApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong( CharSequence message)
    {
        if (TextUtils.isEmpty(message)) {
            message = "";
        }
        if (isShow)
            Toast.makeText(ShSanyApplication.getInstance(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong( int message)
    {
        if (isShow)
            Toast.makeText(ShSanyApplication.getInstance(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show( CharSequence message, int duration)
    {
        if (TextUtils.isEmpty(message)) {
            message = "";
        }
        if (isShow)
            Toast.makeText(ShSanyApplication.getInstance(), message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show( int message, int duration)
    {
        if (isShow)
            Toast.makeText(ShSanyApplication.getInstance(), message, duration).show();
    }
}
