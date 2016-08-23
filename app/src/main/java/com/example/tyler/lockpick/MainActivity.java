package com.example.tyler.lockpick;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.renderscript.Type;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {
    private static final boolean TOOLBOX_CLOSED = false;
    private static final boolean TOOLBOX_OPEN = true;
    private SensorManager mSensorManager;
    private lockRotation rotationSensor;
    private boolean toolbox_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        toolbox_state = TOOLBOX_CLOSED;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lockpick movement
        findViewById(R.id.lockpick).setOnTouchListener(new View.OnTouchListener(){
            float x_offset = 0;
            float y_offset = 0;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        x_offset = event.getRawX() - view.getX();
                        y_offset = event.getRawY() - view.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.setX (event.getRawX() - x_offset);
                        view.setY (event.getRawY() - y_offset);

                        break;
                }
                return true;
            }
        });

        // Start Sensor Manager
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        rotationSensor = new lockRotation();
    }

    @Override
    protected void onResume(){
        super.onResume();
        rotationSensor.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        rotationSensor.stop();
    }

    class lockRotation implements SensorEventListener {
        private Sensor mRotationVectorSensor;
        private final float[] mRotationMatrix = new float[9];

        public lockRotation(){
            mRotationVectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

            // initialize the rotation matrix to identity
            mRotationMatrix[ 0] = 1;
            mRotationMatrix[ 3] = 1;
            mRotationMatrix[ 6] = 1;
        }

        public void start(){
            // Enable the sensor when the activity is resumed, ask for 10 ms updates
            mSensorManager.registerListener(this,mRotationVectorSensor,10000);
        }

        public void stop() {
            mSensorManager.unregisterListener(this);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
                SensorManager.getRotationMatrixFromVector(mRotationMatrix,event.values);
                //String matrix = "";
                //for (float i:mRotationMatrix){
                //    matrix += i + " ";
                //}
                //System.out.println(matrix);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

    /**
     * When the toolbox button is clicked
     * @param view
     */
    public void toolboxTap(View view) {
        if (!toolbox_state){
            Toast.makeText(this,"Opening the toolbox", Toast.LENGTH_SHORT).show();
            toolbox_state = TOOLBOX_OPEN;
        } else {
            Toast.makeText(this,"Closing the toolbox", Toast.LENGTH_SHORT).show();
            toolbox_state = TOOLBOX_CLOSED;
        }
    }

    /**
     * Close the toolbox if the background is tapped
     * @param view
     */
    public void backgroundTap(View view) {
        if (toolbox_state){
            Toast.makeText(this,"Closing the toolbox", Toast.LENGTH_SHORT).show();
            toolbox_state = TOOLBOX_CLOSED;
        }
    }
}

