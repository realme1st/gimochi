import React, { useState, useEffect } from 'react';
import { TouchableOpacity, StyleSheet, Text, View } from 'react-native';
import { useAppDispatch } from '../store';
import screenSlice from '../slices/screen';

function PlayScreen() {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(
      screenSlice.actions.addScreen({
        screen: 'PlayScreen',
      }),
    );
    return () => {
      console.log('unmount');
      dispatch(screenSlice.actions.deleteScreen());
    };
  }, []);

  return (
    <View>
      <Text>놀이터</Text>
    </View>
  );
}

export default PlayScreen;
