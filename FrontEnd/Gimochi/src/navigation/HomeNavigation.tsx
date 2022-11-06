import React, { useLayoutEffect } from 'react';
import { createNativeStackNavigator, NativeStackScreenProps } from '@react-navigation/native-stack';
import { getFocusedRouteNameFromRoute } from '@react-navigation/native';
import HomeScreen from '../screen/HomeScreen';
import AttendanceScreen from '../screen/AttendanceScreen';
import ChallengeScreen from './ChallengeNavigation';
import PlayScreen from '../screen/PlayScreen';
import RPNavigation from './RPNavigation';
import ScheduleScreen from '../screen/ScheduleScreen';
import HomeModal from '../components/HomeModal';
import { Button } from 'react-native';
import { useAppDispatch } from '../store';
import screenSlice from '../slices/screen';

export type HomeStackParamList = {
  HomeScreen: undefined;
  AttendanceScreen: undefined;
  ChallengeScreen: undefined;
  PlayScreen: undefined;
  RollingpaperScreen: undefined;
  ScheduleScreen: undefined;
  HomeModal: undefined;
};
export type HomeModalProps = NativeStackScreenProps<HomeStackParamList, 'HomeModal'>;

const Home = createNativeStackNavigator<HomeStackParamList>();
function HomeNavigation({ route, navigation }: HomeModalProps) {
  const routeName = getFocusedRouteNameFromRoute(route);
  const dispatch = useAppDispatch();
  const back = () => {
    navigation.pop();
    const backRouteName = getFocusedRouteNameFromRoute(route);
    console.log(backRouteName);
    dispatch(
      screenSlice.actions.setScreen({
        screenName: backRouteName,
      }),
    );
    console.log('hi');
    console.log(backRouteName);
  };
  useLayoutEffect(() => {
    if (routeName === 'HomeModal') {
      //MyPage이외의 화면에 대해 tabBar none을 설정한다.
      navigation.setOptions({ tabBarStyle: { display: 'none' } });
    } else {
      navigation.setOptions({ tabBarStyle: { display: undefined } });
    }
    // console.log(routeName);
  }, [navigation, route]);

  return (
    <Home.Navigator
      // initialRouteName='HomeScreen'
      screenOptions={{
        headerTitleAlign: 'center',
        headerTitleStyle: {
          fontFamily: 'Regular',
        },
      }}
    >
      <Home.Screen
        name='HomeScreen'
        component={HomeScreen}
        options={{
          title: '기모치',
        }}
      ></Home.Screen>
      <Home.Screen
        name='AttendanceScreen'
        component={AttendanceScreen}
        options={{
          title: '출석 체크',
          headerLeft: () => <Button onPress={back} title='back' />,
        }}
      ></Home.Screen>
      <Home.Screen
        name='ChallengeScreen'
        component={ChallengeScreen}
        options={{ title: '챌린지콘' }}
      ></Home.Screen>
      <Home.Screen name='PlayScreen' component={PlayScreen} options={{ title: '놀이터' }}></Home.Screen>
      <Home.Screen
        name='RollingpaperScreen'
        component={RPNavigation}
        options={{ headerShown: false }}
      ></Home.Screen>
      <Home.Screen
        name='ScheduleScreen'
        component={ScheduleScreen}
        options={{ title: '일정 관리' }}
      ></Home.Screen>
      <Home.Screen
        name='HomeModal'
        component={HomeModal}
        options={{
          presentation: 'transparentModal',
          headerShown: false,
          gestureEnabled: true,
        }}
      ></Home.Screen>
    </Home.Navigator>
  );
}

export default HomeNavigation;
