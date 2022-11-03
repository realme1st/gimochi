import React from 'react';
import { createNativeStackNavigator, NativeStackScreenProps } from '@react-navigation/native-stack';
import RPMainScreen from '../screen/rollingpaper/RPMainScreen';
import RPDetailScreen from '../screen/rollingpaper/RPDetailScreen';
import RPWriteScreen from '../screen/rollingpaper/RPwriteScreen';

export type RPStackParamList = {
  RPMainScreen: undefined;
  RPWriteScreen: undefined;
  RPDetailScreen: undefined;
};

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
      <RP.Screen
        name='RPDetailScreen'
        component={RPDetailScreen}
        options={{ title: '개별 추카포카' }}
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
