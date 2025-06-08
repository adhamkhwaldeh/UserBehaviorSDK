package com.behaviosec.android.sample;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behaviosec.android.gesturedetectorsdk.Listeners.AccelerometerListener;
import com.behaviosec.android.gesturedetectorsdk.GestureDetectorBuilder;
import com.behaviosec.android.gesturedetectorsdk.GlobalTouchTracker;
import com.behaviosec.android.gesturedetectorsdk.TouchInterceptorLayout;

public class SampleApp extends Application implements Application.ActivityLifecycleCallbacks {

    private AccelerometerListener accelerometerListener;

    @Override
    public void onCreate() {
        super.onCreate();
        accelerometerListener = new AccelerometerListener(this);
        accelerometerListener.start();
        TouchInterceptorLayout layout = new TouchInterceptorLayout(this);
        new GestureDetectorBuilder().setLoggingEnabled(true)
                .setEnabled(true).setDebugMode(true).build(getApplicationContext());

        // Register activity lifecycle to hook into Window
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        Window window = activity.getWindow();
        Window.Callback currentCallback = window.getCallback();
        if (!(currentCallback instanceof GlobalTouchTracker)) {
            window.setCallback(new GlobalTouchTracker(currentCallback));
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

}
