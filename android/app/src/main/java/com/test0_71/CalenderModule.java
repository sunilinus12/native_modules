package com.test0_71;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
public class CalenderModule extends  ReactContextBaseJavaModule {

    private int listenerCount = 0;


    CalenderModule(ReactApplicationContext context){
        super(context);
    }
    @Override
    public String getName(){
        return  "CalenderModule";
    }
    @ReactMethod
    public void createCalendarEvent(String name, String location , Callback myFailureCallback,Callback mySuccessCallback){
        try{
            Log.d("Calender Module","createCalendarEvent"+name+"and location is"+location);
            Integer eventId = 100;
            mySuccessCallback.invoke(eventId);
        }catch (Exception e){
            myFailureCallback.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void createCalendarEventWithPromise(String name, String location , Promise promise){
        try{
            Log.d("Calender Module","createCalendarEvent"+name+"and location is"+location);
            Integer eventId = 100;
            promise.resolve(eventId);
        }catch (Exception e){
            promise.reject("Error",e.getMessage());
        }
    }
    public void createCalendarEventSingleCallback(String name, String location , Callback callback){
        Log.d("Calender Module","createCalendarEvent"+name+"and location is"+location);
        Integer eventId = 100;
        callback.invoke(null,eventId);
    }


    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    @ReactMethod
    public void addListener(String eventName) {
        if (listenerCount == 0) {
            // Set up any upstream listeners or background tasks as necessary
        }

        listenerCount += 1;
        if (listenerCount == 50) {
            WritableMap params = Arguments.createMap();
            params.putInt("listenerCount", listenerCount);
            sendEvent(getReactApplicationContext(), "ListenerCountReached", params);
        }
    }

    @ReactMethod
    public void removeListeners(Integer count) {
        listenerCount -= count;
        if (listenerCount == 0) {
            // Remove upstream listeners, stop unnecessary background tasks
        }
    }

}
