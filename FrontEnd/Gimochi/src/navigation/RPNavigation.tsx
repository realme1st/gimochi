/* eslint-disable @typescript-eslint/restrict-template-expressions */
import React from 'react';
import { createNativeStackNavigator, NativeStackScreenProps } from '@react-navigation/native-stack';
import RPMainScreen from '../screen/rollingpaper/RPMainScreen';
import RPDetailScreen from '../screen/rollingpaper/RPDetailScreen';
import RPWriteScreen from '../screen/rollingpaper/RPWriteScreen';
import RPMessageWriteScreen from '../screen/rollingpaper/RPMessageWriteScreen';

export type RPStackParamList = {
  RPMainScreen: undefined;
  RPWriteScreen: undefined;
  RPDetailScreen: undefined;
  RPMessageWriteScreen: undefined;
  RPId: number;
};

export type RPNavigationProps = NativeStackScreenProps<RPStackParamList>;
const RP = createNativeStackNavigator<RPStackParamList>();
function RPNavigation() {
  return (
    <RP.Navigator
      initialRouteName='RPMainScreen'
      screenOptions={{
        headerTitleAlign: 'center',
        headerTitleStyle: {
          fontFamily: 'Regular',
        },
      }}
    >
      <RP.Screen name='RPMainScreen' component={RPMainScreen} options={{ title: '추카포카' }}></RP.Screen>
      <RP.Screen name='RPDetailScreen' component={RPDetailScreen}></RP.Screen>
      <RP.Screen
        name='RPWriteScreen'
        component={RPWriteScreen}
        options={{ title: '추카포카 작성' }}
      ></RP.Screen>
      <RP.Screen
        name='RPMessageWriteScreen'
        component={RPMessageWriteScreen}
        options={{ title: '추카포카 메시지 작성' }}
      ></RP.Screen>
    </RP.Navigator>
  );
}

export default RPNavigation;
