import React from 'react';
import { View, Image, TouchableOpacity } from 'react-native';
import { getFocusedRouteNameFromRoute, NavigatorScreenParams } from '@react-navigation/native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import MypageScreen from '../screen/MypageScreen';
import GifticonScreen from '../screen/GifticonScreen';
import HomeNavigation from './HomeNavigation';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faBarcode, faUser, faHouse } from '@fortawesome/free-solid-svg-icons';
import { HomeStackParamList } from './HomeNavigation';

type TabParamList = {
  GifticonScreen: undefined;
  MypageScreen: undefined;
  Main: NavigatorScreenParams<HomeStackParamList>;
};

const Tab = createBottomTabNavigator<TabParamList>();
type HomeStackScreenProps = NativeStackScreenProps<HomeStackParamList>;

function TabNavigation({ navigation, route }: HomeStackScreenProps) {
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
        name='GifticonScreen'
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
        listeners={({ navigation }: HomeStackScreenProps) => ({
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
        name='MypageScreen'
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
