package com.example.connect.shake;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import shakeDetector_manager.OnShakeListener;

public class shakeDetectorManager implements SensorEventListener {
    private static final float SHAKE_THRESHOLD_GRAVITY = 1.7f;
    private static final int SHAKE_SLOP_TIME_MS = 300;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 1500;

    OnShakeListener listener;
    long shakeTimestamp;
    int shakeCount;

    public void setOnShakeListener(shakeDetector_manager.OnShakeListener onShakeListener) {
    }

    public interface OnShakeListener {
        void onShake();
    }

    public void setOnShakeListener(OnShakeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ = z / SensorManager.GRAVITY_EARTH;

        float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            final long now = System.currentTimeMillis();
            // Ignore shakes too close to each other (500ms)
            if (shakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                return;
            }

            // Reset the shake count after a long pause (3s)
            if (shakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                shakeCount = 0;
            }

            shakeTimestamp = now;
            shakeCount++;

            if (listener != null) {
                listener.onShake();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

