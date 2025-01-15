import {
  Button,
  EventEmitter,
  Pressable,
  StyleSheet,
  Text,
  View,
  NativeModules,
  NativeEventEmitter,
} from 'react-native';
import React, {useEffect} from 'react';
const {CustomTimeStamp} = NativeModules;

const timeStampEmitter = new NativeEventEmitter(CustomTimeStamp);
export default function App() {
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

  const onPress = async () => {
    try {
      CustomTimeStamp.isWorking('linus', (error, success) => {
        if (!error) {
          console.log('sucess', success);
        } else {
          console.log('error', error);
        }
      });
      CustomTimeStamp.EmitTimeStamp();
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
