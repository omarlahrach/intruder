package com.ailyan.intrus.ui.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.ailyan.intrus.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}