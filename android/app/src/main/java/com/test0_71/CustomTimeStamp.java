package com.test0_71;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class CustomTimeStamp extends ReactContextBaseJavaModule {
    public ReactApplicationContext reactContext;
    CustomTimeStamp(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext=reactContext;
    }

    @Override
    public String getName (){
        return  "CustomTimeStamp";
    }
    @ReactMethod
    public void isWorking(String name, Callback callback){
       try {
           long timestamp= System.currentTimeMillis();
           callback.invoke(null,String.valueOf(timestamp));
       }catch (Exception e){callback.invoke(e.getMessage(),null);}
    }
    @ReactMethod
    public void SendEvent (String eventName,String eventData){
        if(reactContext.hasCatalystInstance()){
            reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName,eventData);
        }
    }

    @ReactMethod
    public void EmitTimeStamp(){
        long timestamp = System.currentTimeMillis();
        SendEvent("TimestampEvent",String.valueOf(timestamp));
    }
    @ReactMethod
    public void addListener(String eventName){

    }
    @ReactMethod
    public void removeListeners(String eventName){

    }
}
