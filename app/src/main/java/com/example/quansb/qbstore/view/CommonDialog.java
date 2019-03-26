package com.example.quansb.qbstore.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class CommonDialog {
    AlertDialog.Builder dialog;
    private OnDialogClickListener listener;

    public void setOnDialogClikeListener(OnDialogClickListener listener) {
        this.listener = listener;
    }


    public interface OnDialogClickListener {
        void onConfirm();
        void onCancel();
    }


    /**
     * 弹出窗口  复用
     *
     * @param context  上下文
     * @param title    弹出窗口的标题栏
     * @param positive 确认按钮  的文本
     * @param negative 取消按钮  的文本
     */
    public void ComDialog(Context context, String title, String positive, String negative) {

        dialog = new AlertDialog.Builder(context);
        dialog.setMessage(title);
        dialog.setCancelable(false);
        dialog.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onConfirm();
            }
        });
        dialog.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onCancel();
            }
        });
        dialog.show();
    }


}
