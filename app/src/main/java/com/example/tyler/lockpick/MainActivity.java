package com.example.tyler.lockpick;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toast flat_message;
    private static final boolean TOOLBOX_CLOSED = false;
    private static final boolean TOOLBOX_OPEN = true;
    private SensorManager mSensorManager;
    private boolean toolbox_open;
    private Lock lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        toolbox_open = TOOLBOX_CLOSED;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO change lockpick if one is selected from the toolbox
        LockPick pick = new LockPick(findViewById(R.id.lockpick));

        // Start Sensor Manager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        flat_message = Toast.makeText(this,"Orient your device horizontally",Toast.LENGTH_SHORT);
        // TODO: Use a lock background instead of a pink line
        lock = new Lock(mSensorManager, (ImageView) findViewById(R.id.pink_line));
    }

    @Override
    protected void onStart(){
        super.onStart();
        lock.lockRotation.start();
    }

    @Override
    protected void onResume(){
        super.onResume();
        lock.lockRotation.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        lock.lockRotation.stop();
    }

    /**
     * When the toolbox button is clicked
     * @param view
     */
    public void toolboxTap(View view) {
        if (!toolbox_open){
            Toast.makeText(this,"Opening the toolbox", Toast.LENGTH_SHORT).show();
            toolbox_open = TOOLBOX_OPEN;
        } else {
            Toast.makeText(this,"Closing the toolbox", Toast.LENGTH_SHORT).show();
            toolbox_open = TOOLBOX_CLOSED;
        }
    }

    /**
     * Close the toolbox if the background is tapped
     * @param view
     */
    public void backgroundTap(View view) {
        if (toolbox_open){
            Toast.makeText(this,"Closing the toolbox", Toast.LENGTH_SHORT).show();
            toolbox_open = TOOLBOX_CLOSED;
        }
    }


}

