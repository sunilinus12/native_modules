import {
  Button,
  EventEmitter,
  Pressable,
  StyleSheet,
  Text,
  View,
  NativeModules,
  NativeEventEmitter,
  AppState,
} from 'react-native';
import React, {useEffect} from 'react';
const {CustomTimeStamp, PipModule} = NativeModules;

const timeStampEmitter = new NativeEventEmitter(CustomTimeStamp);
export default function App() {
  const handleGetEventData = eventData => {
    console.log('eventDataeventDataeventData', eventData);
  };

  useEffect(() => {
    const appStateSubs = AppState.addEventListener('change', nextAppState => {
      console.log('AppState changed to:', nextAppState);
      if (nextAppState == 'background') {
        onPress();
      }
    });

    // Cleanup the listener when component unmounts
    return () => {
      appStateSubs.remove();
    };
  }, []); // Em

  const onPress = async () => {
    try {
      let resp = await PipModule.StartPipMode();
      console.log('resrsep', resp);
    } catch (error) {
      console.log('Error', error);
    }
  };
  return (
    <View style={styles.container}>
      <Button onPress={onPress} title="Press Me" />
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
