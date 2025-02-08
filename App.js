import {
  Button,
  EventEmitter,
  Pressable,
  StyleSheet,
  Text,
  View,
  NativeModules,
  NativeEventEmitter,
  PermissionsAndroid,
  Image,
  Platform,
} from 'react-native';
import React, {useEffect, useState} from 'react';
import {CustomTimeStamp, CalenderModule} from './src/NativeModules';

const timeStampEmitter =
  Platform.OS == 'android' ? new NativeEventEmitter(CustomTimeStamp) : null;

export default function App() {
  const [image, setImage] = useState(null);
  const handleGetEventData = eventData => {
    console.log('eventDataeventDataeventData', eventData);
  };

  useEffect(() => {
    let subscribe;
    if (Platform.OS == 'android') {
      subscribe = timeStampEmitter.addListener(
        'TimestampEvent',
        handleGetEventData,
      );
    }
    return () => {
      if (subscribe) {
        subscribe.remove();
      }
    };
  }, []);

  const handleCustomTimeStamp = () => {
    CustomTimeStamp.isWorking('linus', (error, success) => {
      if (!error) {
        console.log('sucess', success);
      } else {
        console.log('error', error);
      }
    });
    CustomTimeStamp.EmitTimeStamp();
  };
  const onPress = async () => {
    try {
      CalenderModule.createCalendarEvent(
        ' office',
        ' on tuestday ',
        (error,eventData) => {
          if(eventData){
            console.log('eventDataeventData', eventData);
          }else{
            console.log('error in evenData', error);
          }
        },
      );
    } catch (error) {
      console.log('onPress: Error', error);
    }
  };
  const handleCaptureImage = async () => {
    function getFileNameFromUri(uri) {
      // Split the URI by '/' and get the last part (the file name)
      const segments = uri.split('/');
      return segments[segments.length - 1];
    }
    // handleCustomTimeStamp();
    let askPermission = await PermissionsAndroid.request(
      PermissionsAndroid.PERMISSIONS.CAMERA,
    );
    if (askPermission == PermissionsAndroid.RESULTS.GRANTED) {
      let resp = await CustomImagePicker.openCamera();
      console.log('resprespresprespresp', resp);
      setImage(resp);
    } else {
      console.log('Camera permission denied');
    }
  };
  const handleDelete = async () => {
    try {
      let resp = await CustomImagePicker.deleteImage(getFileNameFromUri(image));
      console.log('respresprespresprespresp', resp);
    } catch (error) {
      console.log('handleDelete Error:', error);
    }
  };
  return (
    <View style={styles.container}>
      <Button onPress={onPress} title="Capture" />
      <Button onPress={handleDelete} title="Deleted " />
      {image && (
        <Image
          style={{
            width: '70%',
            height: 600,
            resizeMode: 'contain',
            backgroundColor: 'red',
          }}
          source={{
            uri: image,
          }}
        />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  btn: {
    width: 1000,
    height: 0,
    backgroundColor: 'red',
  },
});
