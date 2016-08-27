package com.example.tyler.lockpick;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by tyler on 8/27/16.
 */
public class Lock {
    private final ImageView lock_background;
    public lockRotation lockRotation = new lockRotation();
    private final SensorManager mSensorManager;
    private ArrayList<Pins> pins = new ArrayList<>();

    public Lock(SensorManager mSensorManager,ImageView lock_background){
        this.lock_background = lock_background;
        this.mSensorManager = mSensorManager;
    }

    public void rotateLock(float roll,float tilt){
        /*
        if (abs(roll) < 20 && !toolbox_open){
            flat_message.show();
        } else {
            flat_message.cancel();
        }
        */

    }

    public class lockRotation implements SensorEventListener {

        private final float[] mAccelerometerReading = new float[3];
        private final float[] mMagnetometerReading = new float[3];

        private final float[] mRotationMatrix = new float[9];
        private final float[] mOrientationAngles = new float[3];

        lockRotation(){
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Do something here if sensor accuracy changes.
            // You must implement this callback in your code.
        }

        protected void start() {
            // Get updates from the accelerometer and magnetometer at a constant rate.
            // To make batch operations more efficient and reduce power consumption,
            // provide support for delaying updates to the application.
            //
            // In this example, the sensor reporting delay is small enough such that
            // the application receives an update before the system checks the sensor
            // readings again.
            mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_GAME,SensorManager.SENSOR_DELAY_UI);
            mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                    SensorManager.SENSOR_DELAY_GAME,SensorManager.SENSOR_DELAY_UI);
        }

        protected void stop() {
            // Don't receive any more updates from either sensor.
            mSensorManager.unregisterListener(this);
        }

        // Get readings from accelerometer and magnetometer. To simplify calculations,
        // consider storing these readings as unit vectors.
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(event.values, 0, mAccelerometerReading,
                        0, mAccelerometerReading.length);
            }
            else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                System.arraycopy(event.values, 0, mMagnetometerReading,
                        0, mMagnetometerReading.length);
            }
            updateOrientationAngles();
        }

        // Compute the three orientation angles based on the most recent readings from
        // the device's accelerometer and magnetometer.
        public void updateOrientationAngles() {
            // Update rotation matrix, which is needed to update orientation angles.
            SensorManager.getRotationMatrix(mRotationMatrix, null,
                    mAccelerometerReading, mMagnetometerReading);

            // "mRotationMatrix" now has up-to-date information.
            SensorManager.getOrientation(mRotationMatrix, mOrientationAngles);

            // "mOrientationAngles" now has up-to-date information.
            // Azimuth is the direction the device is facing from 0-360
            float azimuth = mOrientationAngles[0]*180/(float)Math.PI;
            float tilt = mOrientationAngles[1]*180/(float)Math.PI;
            float roll = mOrientationAngles[2]*180/(float)Math.PI;

            // Rotate the lock based on the rotation data
            System.out.println(tilt);

            //rotateLock(roll,tilt);
        }
    }
}
