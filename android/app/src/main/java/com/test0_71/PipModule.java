package com.test0_71;

import android.app.Activity;
import android.app.PictureInPictureParams;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Rational;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class PipModule extends ReactContextBaseJavaModule {
    private Rational aspectRatio = new Rational(16, 9);
    PipModule(ReactApplicationContext context){
        super(context);

    }

    @Override
    public String getName(){
        return  "PipModule";
    }
    @ReactMethod
    public void StartPipMode(Promise promise){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            Activity activity=getCurrentActivity();
            if(activity==null){
                promise.reject("Activity_Not_Found","Activity is null");
                return;
            }
            if(!activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)){
                promise.reject("PIP_NOT_SUPPORTED","Pip mode is not support for your device");
                return;
            }
            PictureInPictureParams params = new PictureInPictureParams.Builder().setAspectRatio((this.aspectRatio)).build();
            boolean isPIPStarted= activity.enterPictureInPictureMode(params);
            if(isPIPStarted){
                promise.resolve("PiP mode started successfully.");
            }else{
                promise.reject("PIP_FAILED", "Failed to start PiP mode.");
            }
        }
        else{
            promise.reject("PIP_NOT_AVAILABLE", "Picture-in-Picture mode is not available on this device.");
        }
    }

}
