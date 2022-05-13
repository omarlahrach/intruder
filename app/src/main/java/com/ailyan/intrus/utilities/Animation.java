package com.ailyan.intrus.utilities;

import android.view.View;

public class Animation {
    public static void appear(View view) {
        view.animate()
                .alpha(1f)
                .setDuration(1000);
    }
}
