import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import MypageScreen from '../screen/mypage/MypageScreen';
import FriendRecomScreen from '../screen/mypage/FriendRecomScreen';

export type MypageStackParamList = {
  MypageScreen: undefined;
  FriendRecomScreen: undefined;
};

const Mypage = createNativeStackNavigator<MypageStackParamList>();

function MypageNavigation() {
  return (
    <Mypage.Navigator
      initialRouteName='MypageScreen'
      screenOptions={{
        headerTitleAlign: 'center',
        headerTitleStyle: {
          fontFamily: 'Regular',
        },
      }}
    >
      <Mypage.Screen
        name='MypageScreen'
        component={MypageScreen}
        options={{ title: '마이페이지' }}
      ></Mypage.Screen>
      <Mypage.Screen
        name='FriendRecomScreen'
        component={FriendRecomScreen}
        options={{ title: '알 수도 있는 친구들' }}
      ></Mypage.Screen>
    </Mypage.Navigator>
  );
}

export default MypageNavigation;
