package org.elastos.dma.dmademo.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.elastos.dma.dmademo.R;

public class AlertDialogUtil {

    public static void showUtil(Context context, String hint, String operator) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(context, R.layout.dialog_layout, null);
        //设置对话框布局
        dialog.setView(dialogView);
        dialog.show();
        EditText input = (EditText) dialogView.findViewById(R.id.dialog_input);
        input.setHint(hint);
        final String name = input.getText().toString();
        TextView operate = dialogView.findViewById(R.id.dialog_operate);
        operate.setText(operator);
        operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //Toast.makeText(context, "转让成功", Toast.LENGTH_LONG).show();
            }
        });

    }
}
