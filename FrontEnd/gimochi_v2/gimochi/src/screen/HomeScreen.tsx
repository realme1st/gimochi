/* eslint-disable @typescript-eslint/no-floating-promises */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import React, { useEffect } from 'react';
import { Text, View } from 'react-native';
import Calendars from '../components/Calendar';
import { useSelector } from 'react-redux';
import { RootState } from '../store/reducer';
import { useAppDispatch } from '../store';
import screenSlice from '../slices/screen';
import EncryptedStorage from 'react-native-encrypted-storage';

function HomeScreen() {
  const userId = useSelector((state: RootState) => state.user.userId);
  const userNickname = useSelector((state: RootState) => state.user.userNickname);
  const dispatch = useAppDispatch();
  const test = async () => {
    const hi = await EncryptedStorage.getItem('accessToken');
    console.log(hi);
  };

  useEffect(() => {
    dispatch(
      screenSlice.actions.addScreen({
        screen: 'HomeScreen',
      }),
    );
    test();
    return () => {
      console.log('unmount');
      // dispatch(screenSlice.actions.deleteScreen());
    };
  }, []);

  return (
    <View>
      <Text>{userId}</Text>
      <Text>{userNickname}</Text>
      <Calendars />
    </View>
  );
}

export default HomeScreen;
