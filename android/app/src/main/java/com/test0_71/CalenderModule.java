package com.test0_71;
import android.util.Log;

import com.facebook.react.bridge.NativeModule;
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
    public void createCalendarEvent(String name,String location){
        Log.d("Calender Module","createCalendarEvent"+name+"and location is"+location);

    }


}
