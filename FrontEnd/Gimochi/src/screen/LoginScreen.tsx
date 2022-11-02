/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-misused-promises */
/* eslint-disable @typescript-eslint/no-floating-promises */
import React, { useEffect, useState } from 'react';
import { Text, View, TouchableOpacity } from 'react-native';
import styled from 'styled-components/native';
import { KakaoOAuthToken, login } from '@react-native-seoul/kakao-login';
import AsyncStorage from '@react-native-async-storage/async-storage';
import userSlice from '../slices/user';
import { useAppDispatch } from '../store';

function LoginScreen() {
  const [aToken, setAToken] = useState('');
  const [rToken, setRToken] = useState('');
  const dispatch = useAppDispatch();

  const signInWithKakao = async (): Promise<void> => {
    const token: KakaoOAuthToken = await login();
    setAToken(token.accessToken);
    setRToken(token.refreshToken);
    await AsyncStorage.setItem('Login', 'true');
    dispatch(
      userSlice.actions.setLogin({
        isLogin: 'true',
      }),
    );
  };

  return (
    <View>
      <Text>로그인</Text>
      <TouchableOpacity onPress={signInWithKakao}>
        <Text>로그인</Text>
      </TouchableOpacity>
    </View>
  );
}

export default LoginScreen;
