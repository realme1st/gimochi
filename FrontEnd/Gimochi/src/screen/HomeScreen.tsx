/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import React from 'react';
import { Text, View } from 'react-native';
import Calendars from '../components/Calendar';
import { useSelector } from 'react-redux';
import { RootState } from '../store/reducer';

function HomeScreen() {
  const userId = useSelector((state: RootState) => state.user.userId);
  const userNickname = useSelector((state: RootState) => state.user.userNickname);
  return (
    <View>
      <Text>{userId}</Text>
      <Text>{userNickname}</Text>
      <Calendars />
    </View>
  );
}

export default HomeScreen;
