package com.test0_71;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;
public class CalenderModule extends  ReactContextBaseJavaModule {

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


}
