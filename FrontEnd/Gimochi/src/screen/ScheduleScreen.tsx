import React from 'react';
import { Text, View } from 'react-native';
import Calendars from '../components/Calendar';

function ScheduleScreen() {
  return (
    <View>
      <Text>일정관리</Text>
      <Calendars />
    </View>
  );
}

export default ScheduleScreen;
