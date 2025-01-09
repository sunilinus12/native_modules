import {
  Button,
  Pressable,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import React from 'react';

import { NativeModules } from 'react-native';

const { CalenderModule } = NativeModules; // Ensure the name matches `getName()` in Java

export default function App() {
  const onPress = () => {
    try {
      CalenderModule.createCalendarEvent('testName', 'testLocation');
    } catch (error) {
      console.log("Error",error);
      
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
