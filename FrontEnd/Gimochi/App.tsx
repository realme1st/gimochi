/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-implied-eval */
/* eslint-disable @typescript-eslint/no-unsafe-argument */
import React, { useEffect, useState } from 'react';
import { Provider } from 'react-redux';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import store from './src/store';
import TabNavigation from './src/navigation/TabNavigation';
import LoginScreen from './src/screen/LoginScreen';
import SplashScreen from 'react-native-splash-screen';
import AsyncStorage from '@react-native-async-storage/async-storage';
import EncryptedStorage from 'react-native-encrypted-storage';
import { useAppDispatch } from './src/store';
import { useSelector } from 'react-redux';
import { RootState } from './src/store/reducer';
import userSlice from './src/slices/user';
import { format } from 'date-fns';
import axios from 'axios';
import { URL } from './src/api/API';

const Stack = createNativeStackNavigator();

function AppInner() {
  const dispatch = useAppDispatch();
  const isUserId = useSelector((state: RootState) => !!state.user.userId);
  const date = new Date();
  const loginCheck = async (): Promise<void> => {
    const login = await AsyncStorage.getItem('Login');
    const userId = await AsyncStorage.getItem('UserId');
    const accessToken = await EncryptedStorage.getItem('accessToken');
    const accessTokenExpiresAt = await EncryptedStorage.getItem('accessTokenExpiresAt');
    console.log(accessToken);
    console.log(accessTokenExpiresAt);
    const tokenTime = new Date(accessTokenExpiresAt);
    // EncryptedStorage에 토큰이 있고(로그인된 상태) 토큰만료시간-현재시간이 2시간 이하라면 새 토큰을 발급받음
    if (accessToken && format(tokenTime - date, 'HH') <= 2) {
      axios
        .get(`${URL}/kakao/oauth/refreshToken`, {
          headers: {
            token: accessToken,
          },
        })
        .then(async function (response) {
          // console.log(response);
          await EncryptedStorage.setItem('accessToken', response.data.data.accessToken);
          await EncryptedStorage.setItem('accessTokenExpiresAt', response.data.data.expiresIn);
        })
        .catch(function (error) {
          console.log(error);
        });
    }
    dispatch(
      userSlice.actions.setLogin({
        accessToken: accessToken,
        accessTokenExpiresAt: accessTokenExpiresAt,
        isLogin: login,
        userId: Number(userId),
      }),
    );
  };

  useEffect(() => {
    void loginCheck();
    setTimeout(function () {
      SplashScreen.hide();
    }, 3000);
  }, [isUserId]);

  return (
    <NavigationContainer>
      <Stack.Navigator>
        {isUserId ? (
          <Stack.Screen name='Home' component={TabNavigation} options={{ headerShown: false }}></Stack.Screen>
        ) : (
          <Stack.Screen name='Login' component={LoginScreen} options={{ headerShown: false }}></Stack.Screen>
        )}
      </Stack.Navigator>
    </NavigationContainer>
  );
}
function App() {
  return (
    <Provider store={store}>
      <AppInner />
    </Provider>
  );
}

export default App;
