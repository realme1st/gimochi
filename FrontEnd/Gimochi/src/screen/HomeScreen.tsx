import React from 'react';
import { Text, View } from 'react-native';
import Calendars from '../components/Calendar';

function HomeScreen() {
  return (
    <View>
      <Text>홈</Text>
      <Calendars />
    </View>
  );
}

export default HomeScreen;
