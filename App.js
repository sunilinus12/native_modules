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
} from 'react-native';
import React, {useEffect, useState} from 'react';
const {CustomTimeStamp, CustomImagePicker} = NativeModules;

const timeStampEmitter = new NativeEventEmitter(CustomTimeStamp);
export default function App() {
  const [image, setImage] = useState(null);
  const handleGetEventData = eventData => {
    console.log('eventDataeventDataeventData', eventData);
  };

  useEffect(() => {
    const subscribe = timeStampEmitter.addListener(
      'TimestampEvent',
      handleGetEventData,
    );

    return () => {
      subscribe.remove();
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
    } catch (error) {
      console.log('onPress: Error', error);
    }
  };
  const handleDelete = async () => {
    try {
      function getFileNameFromUri(uri) {
        // Split the URI by '/' and get the last part (the file name)
        const segments = uri.split('/');
        return segments[segments.length - 1];
      }

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
