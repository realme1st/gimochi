import React, { useEffect } from 'react';
import { Text, View } from 'react-native';
import Calendars from '../components/Calendar';
import { useAppDispatch } from '../store';
import screenSlice from '../slices/screen';

function AttendanceScreen() {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(
      screenSlice.actions.addScreen({
        screen: 'AttendanceScreen',
      }),
    );
    return () => {
      console.log('unmount');
      dispatch(screenSlice.actions.deleteScreen());
    };
  }, []);

  return (
    <View>
      <Text>출석체크</Text>
      <Calendars />
    </View>
  );
}

export default AttendanceScreen;
