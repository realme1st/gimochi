/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
import React, { useState } from 'react';
import { View, Text, Pressable, StyleSheet, Image, TouchableOpacity } from 'react-native';
import { getFocusedRouteNameFromRoute } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import HomeScreen from '../screen/HomeScreen';
import MypageScreen from '../screen/MypageScreen';
import GifticonScreen from '../screen/GifticonScreen';
import HomeNavigation from './HomeNavigation';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faBarcode, faUser, faHouse } from '@fortawesome/free-solid-svg-icons';
import { NavigationContainer } from '@react-navigation/native';

// import ScheduleScreen from '../screen/ScheduleScreen';
// import ChallengeScreen from '../Screen/ChallengeScreen';
// import RollingpaperScreen from '../Screen/RollingpaperScreen';
// import PlayScreen from '../Screen/PlayScreen';
// import AttendanceScreen from '../Screen/AttendanceScreen';

const Tab = createBottomTabNavigator();

function TabNavigation({ navigation, route }) {
  // 화면에 보여주는 화면의 name값 받아오기
  const routeName = getFocusedRouteNameFromRoute(route);
  const homeModal = () => {
    navigation.navigate('HomeModal');
  };
  return (
    <Tab.Navigator
      initialRouteName='Main'
      screenOptions={{
        headerTitleAlign: 'center',
        headerTitleStyle: {
          fontFamily: 'Regular',
        },
        tabBarShowLabel: false,
      }}
    >
      <Tab.Screen
        name='Gifticon'
        component={GifticonScreen}
        options={{
          title: '티콘모아',
          tabBarIcon: ({ focused }) => (
            <View>
              <FontAwesomeIcon icon={faBarcode} size={30} color={focused ? '#ffa401' : '#686868'} />
            </View>
          ),
        }}
      ></Tab.Screen>
      <Tab.Screen
        name='Main'
        component={HomeNavigation}
        // 현재 위치가 홈화면이면 중앙버튼 클릭했을때 모달뜨게
        listeners={({ navigation }) => ({
          tabPress: (e) => {
            if (routeName === 'Main') {
              e.preventDefault();
              navigation.navigate('HomeModal');
            }
          },
        })}
        options={{
          headerShown: false,
          tabBarIcon: ({ focused }) =>
            focused ? (
              <TouchableOpacity style={{ position: 'absolute', bottom: 1 }} onPress={() => homeModal()}>
                <Image
                  // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
                  source={require('../assets/images/homeMochi.png')}
                  style={{ width: 80, height: 80 }}
                />
              </TouchableOpacity>
            ) : (
              <View>
                <FontAwesomeIcon icon={faHouse} size={30} color={'#686868'} />
              </View>
            ),
        }}
      ></Tab.Screen>
      <Tab.Screen
        name='Mypage'
        component={MypageScreen}
        options={{
          title: '마이페이지',
          tabBarIcon: ({ focused }) => (
            <View>
              <FontAwesomeIcon icon={faUser} size={30} color={focused ? '#ffa401' : '#686868'} />
            </View>
          ),
        }}
      ></Tab.Screen>
    </Tab.Navigator>
  );
}

export default TabNavigation;
