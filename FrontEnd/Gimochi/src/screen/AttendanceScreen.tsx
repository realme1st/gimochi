import React from 'react';
import { Text, View } from 'react-native';
import Calendars from '../components/Calendar';

function AttendanceScreen() {
  return (
    <View>
      <Text>출석체크</Text>
      <Calendars />
    </View>
  );
}

export default AttendanceScreen;
