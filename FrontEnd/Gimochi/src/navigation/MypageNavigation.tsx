/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-return */
import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import MypageScreen from '../screen/mypage/MypageScreen';
import FriendRecomScreen from '../screen/mypage/FriendRecomScreen';
import MyPointScreen from '../screen/mypage/MyPointScreen';
import NotificationScreen from '../screen/NotificationScreen';
import { Icon } from '@rneui/themed';

export type MypageStackParamList = {
  MypageScreen: undefined;
  FriendRecomScreen: undefined;
  MyPointScreen: undefined;
  NotificationScreen: undefined;
};

const Mypage = createNativeStackNavigator<MypageStackParamList>();

function MypageNavigation({ navigation }) {
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
        options={{
          title: '마이페이지',
          headerRight: () => (
            <Icon
              name='bell'
              type='evilicon'
              onPress={() => navigation.navigate('NotificationScreen')}
              size={30}
            />
          ),
        }}
      ></Mypage.Screen>
      <Mypage.Screen
        name='FriendRecomScreen'
        component={FriendRecomScreen}
        options={{ title: '알 수도 있는 친구들' }}
      ></Mypage.Screen>
      <Mypage.Screen
        name='MyPointScreen'
        component={MyPointScreen}
        options={{ title: '내 포인트 내역' }}
      ></Mypage.Screen>
      <Mypage.Screen
        name='NotificationScreen'
        component={NotificationScreen}
        options={{ title: '알림' }}
      ></Mypage.Screen>
    </Mypage.Navigator>
  );
}

export default MypageNavigation;
