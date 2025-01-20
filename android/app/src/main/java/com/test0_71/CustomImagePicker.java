package com.test0_71;

import android.graphics.Bitmap;
import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ActivityEventListener;

import java.io.File;

public class CustomImagePicker extends ReactContextBaseJavaModule implements ActivityEventListener {

    private static final int CAMERA_REQUEST_CODE=100;
    private Promise cameraPromise;
    CustomImagePicker(ReactApplicationContext context){
        super(context);
        context.addActivityEventListener(this);
    }
    @Override
    public String getName(){
        return "CustomImagePicker";
    }

    @ReactMethod
    public  void openCamera(Promise promise){
        Activity currentActivity = getCurrentActivity();
        if(currentActivity==null){
             promise.reject("No Activity Found","Activity Doesn't exist");
            return;
        }
        cameraPromise=promise;
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(currentActivity.getPackageManager())!=null){
            currentActivity.startActivityForResult(intent,CAMERA_REQUEST_CODE);
        }else {
            cameraPromise.reject("NO_CAMERA", "No camera available");
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (cameraPromise != null) {
                if (resultCode == Activity.RESULT_OK) {
                    // Get the image URI from the Intent data
                    Uri imageUri = data.getData();
                    if (imageUri == null) {
                        // No URI returned, handle it as a bitmap and save it to file
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            if (imageBitmap != null) {
                                // Save the Bitmap to a file and return the file URI
                                File imageFile = saveImageToInternalStorage(imageBitmap);
                                Uri fileUri = Uri.fromFile(imageFile);
                                cameraPromise.resolve(fileUri.toString());
                            } else {
                                cameraPromise.reject("NO_IMAGE", "Image capture failed or data is null");
                            }
                        }
                    } else {
                        // Resolve with the image URI
                        cameraPromise.resolve(imageUri.toString());
                    }
                } else {
                    cameraPromise.reject("CANCELLED", "Camera action was cancelled");
                }
                cameraPromise = null;  // Reset the promise to avoid conflicts
            }
        }
    }
    @ReactMethod
    public void deleteImage(String fileName, Promise deletedPromise) {
       try{
           // Get the app's internal storage directory
           File directory = getReactApplicationContext().getFilesDir(); // Internal storage (private to your app)
           // Specify the image file to delete
           File imageFile = new File(directory, fileName);
           // Check if the file exists
           if (imageFile.exists()) {
               boolean deleted = imageFile.delete();
               if (deleted) {
                   Log.d("ImageDeletion", "Image deleted successfully: " + fileName);
                   deletedPromise.resolve("Image deleted successfully"+fileName);
               } else {
                   Log.d("ImageDeletion", "Failed to delete image: " + fileName);
                   deletedPromise.reject("DELETE_FAILED", "Failed to delete the image: " + fileName);
               }
           } else {
               Log.d("ImageDeletion", "File not found: " + fileName);
               deletedPromise.reject("FILE_NOT_FOUND", "Image file not found: " + fileName);
           }
       }
       catch (Exception e){
           deletedPromise.reject("error:","Error while deleteImage"+e.getMessage());
       }
    }

    private File saveImageToInternalStorage(Bitmap bitmap) {
        FileOutputStream outStream = null;
        File imageFile = null;

        try {
            // Get the internal storage directory for your app
//            File directory = getContext().getFilesDir(); // Internal app storage (private to your app)
            File directory = getReactApplicationContext().getFilesDir();
            // Create a unique file name (e.g., "image_<timestamp>.jpg")
            String fileName = "image_" + System.currentTimeMillis() + ".jpg";
            imageFile = new File(directory, fileName);
            // Open a file output stream to write the bitmap to the file
            outStream = new FileOutputStream(imageFile);

            // Compress the bitmap and save it as a JPEG file (quality can be adjusted)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);

            // Flush and close the output stream
            outStream.flush();
            outStream.close();

        } catch (IOException e) {
            Log.e("ImageCapture", "Error saving image", e);
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imageFile;
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

}
