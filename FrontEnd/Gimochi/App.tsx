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
  const isLoggedIn = useSelector((state: RootState) => state.user.isLogin);
  const date = new Date();
  // const now = format(date, 'yyyy-MM-dd HH:mm:ss');
  const loginCheck = async (): Promise<void> => {
    const login = await AsyncStorage.getItem('Login');
    const accessToken = await EncryptedStorage.getItem('accessToken');
    const accessTokenExpiresAt = await EncryptedStorage.getItem('accessTokenExpiresAt');
    const tokenTime = new Date(accessTokenExpiresAt);
    // console.log(accessToken);
    // console.log(accessTokenExpiresAt);
    // console.log(tokenTime);
    // console.log(format(tokenTime - date, 'HH'));
    if (format(tokenTime - date, 'HH') <= 2) {
      axios
        .get(`${URL}/kakao/oauth/refreshToken`, {
          headers: {
            token: accessToken,
          },
        })
        .then(async function (response) {
          await EncryptedStorage.setItem('accessToken', response.data.data.accessToken);
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
      }),
    );
  };

  useEffect(() => {
    void loginCheck();
    setTimeout(function () {
      SplashScreen.hide();
    }, 3000);
  }, []);

  return (
    <NavigationContainer>
      <Stack.Navigator>
        {isLoggedIn === 'true' ? (
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
