import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import HomeScreen from '../screen/HomeScreen';
import MypageScreen from '../screen/MypageScreen';
import ScheduleScreen from '../screen/ScheduleScreen';
// import ChallengeScreen from '../Screen/ChallengeScreen';
// import GifticonScreen from '../Screen/GifticonScreen';
// import RollingpaperScreen from '../Screen/RollingpaperScreen';
// import PlayScreen from '../Screen/PlayScreen';
// import AttendanceScreen from '../Screen/AttendanceScreen';

const Tab = createBottomTabNavigator();

function TabNavigation() {
  return (
    <Tab.Navigator>
      <Tab.Screen
        name='HomeScreen'
        component={HomeScreen}
        options={{
          title: '홈',
        }}
      ></Tab.Screen>
      <Tab.Screen
        name='Schedule'
        component={ScheduleScreen}
        options={{
          title: '일정관리',
        }}
      ></Tab.Screen>
      <Tab.Screen
        name='Mypage'
        component={MypageScreen}
        options={{
          title: '마이페이지',
        }}
      ></Tab.Screen>
    </Tab.Navigator>
  );
}

export default TabNavigation;
