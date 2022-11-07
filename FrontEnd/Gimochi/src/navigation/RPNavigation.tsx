/* eslint-disable @typescript-eslint/restrict-template-expressions */
import React from 'react';
import { createNativeStackNavigator, NativeStackScreenProps } from '@react-navigation/native-stack';
import RPMainScreen from '../screen/rollingpaper/RPMainScreen';
import RPDetailScreen from '../screen/rollingpaper/RPDetailScreen';
import RPWriteScreen from '../screen/rollingpaper/RPWriteScreen';

export type RPStackParamList = {
  RPMainScreen: undefined;
  RPWriteScreen: undefined;
  RPDetailScreen: undefined;
  RPId: number;
};

export type RPNavigationProps = NativeStackScreenProps<RPStackParamList>;
const RP = createNativeStackNavigator<RPStackParamList>();
function RPNavigation({ route }) {
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
      <RP.Screen
        name='RPDetailScreen'
        component={RPDetailScreen}
        options={({ route }) => ({ title: `${route.params.RPId}번 추카포카` })}
      ></RP.Screen>
      <RP.Screen
        name='RPWriteScreen'
        component={RPWriteScreen}
        options={{ title: '추카포카 작성' }}
      ></RP.Screen>
    </RP.Navigator>
  );
}

export default RPNavigation;
