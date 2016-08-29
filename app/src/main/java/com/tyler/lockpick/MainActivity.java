package com.tyler.lockpick;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tyler.lockpick.Global.Settings;
import com.tyler.lockpick.Objects.Lock;
import com.tyler.lockpick.Objects.LockPick;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Handler update = new Handler();
    private final int update_delay = 1000/60;// miliseconds
    private Toast flat_message;
    public static FloatingActionButton toolbox;
    private static final boolean TOOLBOX_CLOSED = false;
    private static final boolean TOOLBOX_OPEN = true;
    private boolean toolbox_open;
    private LockPick pick;
    private Lock lock;
    private Vibrator mVibration;
    private Settings settings = Settings.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        toolbox = (FloatingActionButton) findViewById(R.id.fab);
        toolbox_open = TOOLBOX_CLOSED;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO change lockpick if one is selected from the toolbox
        pick = new LockPick(findViewById(R.id.lockpick));

        // Start Sensor Manager
        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null){
            Toast.makeText(this,"No accelerometer detected, this may affect performance",Toast.LENGTH_LONG);
        }
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) == null) {
            Toast.makeText(this,"No magnetic field detected, this may affect performance",Toast.LENGTH_LONG);
        }
        flat_message = Toast.makeText(this,"Orient your device horizontally",Toast.LENGTH_SHORT);
        // TODO: Use a lock background instead of a pink line
        Point lock_center = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(lock_center);
        lock = new Lock(mSensorManager, (ImageView) findViewById(R.id.pink_line),lock_center,(FloatingActionButton)findViewById(R.id.fab));

        mVibration = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // Do something every 1/60th of a second
        update.postDelayed(new Runnable(){
            public void run(){
                Update();
                update.postDelayed(this,update_delay);
            }
        }, update_delay);
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
     * @param view the background image
     */
    public void backgroundTap(View view) {
        if (toolbox_open){
            Toast.makeText(this,"Closing the toolbox", Toast.LENGTH_SHORT).show();
            toolbox_open = TOOLBOX_CLOSED;
        }
    }

    /**
     * Tasks that will run every 1/60th of a second
     */
    private void Update() {
        // Finds collisions between all images in lock
        List<ImageView> hard_collisions = lock.getImages();
        Rect pick_rect = new Rect();
        pick.getImage().getHitRect(pick_rect);

        // Detect collisions
        for (ImageView image:hard_collisions) {
            Rect hit_rect = new Rect();
            image.getHitRect(hit_rect);
            if (pick_rect.intersect(hit_rect))
                mVibration.vibrate(update_delay);
        }
    }
}

