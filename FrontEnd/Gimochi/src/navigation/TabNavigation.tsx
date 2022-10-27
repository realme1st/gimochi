import React from 'react';
import { View, Text, Pressable, StyleSheet, Image } from 'react-native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import HomeScreen from '../screen/HomeScreen';
import MypageScreen from '../screen/MypageScreen';
import GifticonScreen from '../screen/GifticonScreen';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faBarcode, faUser, faHouse } from '@fortawesome/free-solid-svg-icons';

// import ScheduleScreen from '../screen/ScheduleScreen';
// import ChallengeScreen from '../Screen/ChallengeScreen';
// import RollingpaperScreen from '../Screen/RollingpaperScreen';
// import PlayScreen from '../Screen/PlayScreen';
// import AttendanceScreen from '../Screen/AttendanceScreen';

const Tab = createBottomTabNavigator();

function TabNavigation() {
  return (
    <Tab.Navigator
      initialRouteName='HomeScreen'
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
        name='HomeScreen'
        component={HomeScreen}
        // listeners={({ navigation }) => ({
        //   tabPress: (e) => {
        //     e.preventDefault();
        //     navigation.navigate(`CreateModal${navigation.getState().index}`);
        //   },
        // })}
        options={{
          title: '기모치',
          tabBarIcon: ({ focused }) =>
            focused ? (
              <View>
                <Image
                  // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
                  source={require('../assets/images/homeMochi.png')}
                  style={{ width: 70, height: 70, marginBottom: 30 }}
                />
              </View>
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
