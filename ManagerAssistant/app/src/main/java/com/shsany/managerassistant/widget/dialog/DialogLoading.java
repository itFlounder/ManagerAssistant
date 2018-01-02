package com.shsany.managerassistant.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

/**
 * Created by PC on 2017/12/27.
 */

public class DialogLoading extends Dialog{
    private TextView loadingLabel;

    public DialogLoading(Context context) {
        super(context);

    }

    public void setDialogLabel(String label) {
        loadingLabel.setText(label);
    }
}
