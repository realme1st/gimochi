import React from 'react';
import { Text, View } from 'react-native';
import Calendars from '../components/Calendar';
import { useSelector } from 'react-redux';
import { RootState } from '../store/reducer';

function HomeScreen() {
  const userId = useSelector((state: RootState) => state.user.userId);
  return (
    <View>
      <Text>{userId}</Text>
      <Calendars />
    </View>
  );
}

export default HomeScreen;
