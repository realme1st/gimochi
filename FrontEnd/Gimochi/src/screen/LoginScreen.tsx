/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-misused-promises */
/* eslint-disable @typescript-eslint/no-floating-promises */
import React, { useState } from 'react';
import { ActivityIndicator, Image } from 'react-native';
import styled from 'styled-components/native';
import { KakaoOAuthToken, login } from '@react-native-seoul/kakao-login';
import AsyncStorage from '@react-native-async-storage/async-storage';
import EncryptedStorage from 'react-native-encrypted-storage';
import userSlice from '../slices/user';
import { useAppDispatch } from '../store';
import { URL } from '../api/API';
import axios from 'axios';
import KakaoLogo from 'react-native-kakao-logo';

function LoginScreen() {
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);

  const redux = async (token: string, time: string, userId: string, userNickname: string) => {
    await AsyncStorage.setItem('userId', userId);
    await AsyncStorage.setItem('userNickname', userNickname);
    await AsyncStorage.setItem('login', 'true');
    await EncryptedStorage.setItem('accessToken', token);
    await EncryptedStorage.setItem('accessTokenExpiresAt', time);
    dispatch(
      userSlice.actions.setLogin({
        accessToken: token,
        accessTokenExpiresAt: time,
        userId: Number(userId),
        userNickname: userNickname,
      }),
    );
  };

  const signInWithKakao = async (): Promise<void> => {
    setLoading(true);
    const token: KakaoOAuthToken = await login();
    console.log(token.accessToken);
    console.log(token.refreshToken);
    await axios
      .get(`${URL}/kakao/oauth/login`, {
        headers: {
          AccessToken: token.accessToken,
          RefreshToken: token.refreshToken,
        },
      })
      .then(function (response) {
        console.log(response);
        console.log(response.data.data.userId);
        redux(
          token.accessToken,
          String(token.accessTokenExpiresAt),
          String(response.data.data.userId),
          String(response.data.data.userNickname),
        );
        setLoading(false);
      })
      .catch(function (error) {
        setLoading(false);
        console.log(error);
      });
    // redux();
  };

  return (
    <LoginScreenContainer>
      <Image source={require('../assets/images/login.png')} style={{ width: '70%', height: '70%' }} />
      <LoginButtonContainer onPress={signInWithKakao}>
        {loading ? (
          <ActivityIndicator color='black' />
        ) : (
          <>
            <KakaoLogo color={'black'} />
            <LoginButtonText>카카오로 로그인</LoginButtonText>
          </>
        )}
      </LoginButtonContainer>
    </LoginScreenContainer>
  );
}

const LoginScreenContainer = styled.View`
  flex: 1;
  align-items: center;
  background-color: #ffffff;
`;

const LoginButtonContainer = styled.TouchableOpacity`
  background-color: #fee500;
  border-radius: 18px;
  width: 60%;
  padding: 15px;
  margin: 10px 0px;
  justify-content: center;
  align-items: center;
  margin-top: 10%;
  flex-direction: row;
`;

const LoginButtonText = styled.Text`
  color: #000000;
  font-family: 'Regular';
  font-size: 20px;
  margin-left: 10%;
`;

export default LoginScreen;
