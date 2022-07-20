package com.ailyan.intrus.utilities;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ailyan.intrus.R;
import com.ailyan.intrus.ui.views.activities.GameActivity;
import com.ailyan.intrus.utilities.enums.ConnectionState;

import java.util.Objects;

public class Toast {
    public static void showConnectionState(GameActivity gameActivity, ConnectionState connectionState) {
        View layout = gameActivity.getLayoutInflater().inflate(R.layout.toats_connection, gameActivity.findViewById(R.id.layout_toast));
        ConstraintLayout layoutToast = layout.findViewById(R.id.layout_toast);
        TextView textView_message = layout.findViewById(R.id.textView_message);

        int message = -1, background = -1;
        switch (connectionState) {
            case ONLINE:
                message = R.string.online_message;
                background = R.drawable.online_toast_background;
                break;
            case OFFLINE:
                message = R.string.offline_message;
                background = R.drawable.offline_toast_background;
                break;
            case TIMEOUT:
                message = R.string.timeout_message;
                background = R.drawable.timeout_toast_background;
                break;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        gameActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        layoutToast.setMinWidth((int) (width - width * 0.5));
        textView_message.setText(message);
        textView_message.setBackground(AppCompatResources.getDrawable(gameActivity.getApplicationContext(), background));

        android.widget.Toast toast = new android.widget.Toast(gameActivity.getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
