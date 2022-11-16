/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-return */
import React from 'react';
import styled from 'styled-components/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import MypageScreen from '../screen/mypage/MypageScreen';
import FriendRecomScreen from '../screen/mypage/FriendRecomScreen';
import MyPointScreen from '../screen/mypage/MyPointScreen';
import NotificationScreen from '../screen/NotificationScreen';
import { Icon } from '@rneui/themed';
import { useSelector } from 'react-redux';
import { RootState } from '../store/reducer';

export type MypageStackParamList = {
  MypageScreen: undefined;
  FriendRecomScreen: undefined;
  MyPointScreen: undefined;
  NotificationScreen: undefined;
};

const Mypage = createNativeStackNavigator<MypageStackParamList>();

function MypageNavigation({ navigation }) {
  const notifications = useSelector((state: RootState) => state.notification.notification);
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
            <>
              <Icon
                name='bell'
                type='simple-line-icon'
                onPress={() => navigation.navigate('NotificationScreen')}
                size={30}
              />
              <NotiCountContainer>
                <NotiCountText>{notifications}</NotiCountText>
              </NotiCountContainer>
            </>
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

const NotiCountContainer = styled.View`
  position: absolute;
  width: 65%;
  height: 65%;
  right: 1%;
  bottom: 65%;
  background-color: #ffa401;
  border-radius: 10px;
  justify-content: center;
  align-items: center;
`;

const NotiCountText = styled.Text`
  font-family: 'Regular';
  font-size: 15px;
  color: #ffffff;
`;

export default MypageNavigation;
