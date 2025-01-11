import {Button, Pressable, StyleSheet, Text, View} from 'react-native';
import React from 'react';
import {CalenderModule} from './src/NativeModules';

export default function App() {
  const onPress = async () => {
    try {
      // CalenderModule.createCalendarEvent(
      //   'testName',
      //   'testLocation',
      //   (error) => {
      //     console.log("Error",error)
      //   },
      //   (success) => {
      //     console.log("success",success);

      //   }
      // );
      let resp = await CalenderModule.createCalendarEventWithPromise(
        'testName',
        'testLocation',
      );
      console.log("resp",resp);
      
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
