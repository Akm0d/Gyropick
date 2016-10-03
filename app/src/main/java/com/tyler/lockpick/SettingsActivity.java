package com.tyler.lockpick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.tyler.lockpick.Global.Settings;

public class SettingsActivity extends AppCompatActivity {
    private Settings settings = Settings.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("GyroPick: Settings");
        //TODO: Make the back button work
    }
}
