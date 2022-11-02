/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-misused-promises */
/* eslint-disable @typescript-eslint/no-floating-promises */
import React, { useState } from 'react';
import { TouchableOpacity, StyleSheet, Text, View } from 'react-native';
import {
  KakaoOAuthToken,
  logout,
  getProfile,
  unlink,
  KakaoProfile,
  login,
  getAccessToken,
} from '@react-native-seoul/kakao-login';
import AsyncStorage from '@react-native-async-storage/async-storage';
import userSlice from '../slices/user';
import { useAppDispatch } from '../store';

function PlayScreen() {
  const [result, setResult] = useState<string>('');
  const dispatch = useAppDispatch();

  const signInWithKakao = async (): Promise<void> => {
    const token: KakaoOAuthToken = await login();
    await AsyncStorage.setItem('Login', 'true');
    dispatch(
      userSlice.actions.setLogin({
        isLogin: 'true',
      }),
    );
  };

  const signOutWithKakao = async (): Promise<void> => {
    const message = await logout();
    await AsyncStorage.setItem('Login', 'false');
    setResult(message);
    dispatch(
      userSlice.actions.setLogin({
        isLogin: 'false',
      }),
    );
  };

  // const getKakaoProfile = async (): Promise<void> => {
  //   const profile: KakaoProfile = await getProfile();

  //   setResult(JSON.stringify(profile));
  // };

  // const unlinkKakao = async (): Promise<void> => {
  //   const message = await unlink();

  //   setResult(message);
  // };

  const unlinkKakao = async (): Promise<void> => {
    const message = await AsyncStorage.getItem('Login');

    console.log(message);
  };

  const getToken = async (): Promise<void> => {
    const accessToken = await getAccessToken();

    setResult(accessToken);
  };
  console.log(result);

  return (
    <View>
      <View style={styles.container}>
        <TouchableOpacity
          style={styles.button}
          onPress={() => {
            signInWithKakao();
          }}
        >
          <Text style={styles.text}>카카오 로그인</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={() => getKakaoProfile()}>
          <Text style={styles.text}>프로필 조회</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={() => unlinkKakao()}>
          <Text style={styles.text}>링크 해제</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={() => signOutWithKakao()}>
          <Text style={styles.text}>카카오 로그아웃</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={() => getToken()}>
          <Text style={styles.text}>토큰받기</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

export default PlayScreen;

const styles = StyleSheet.create({
  container: {
    height: '100%',
    justifyContent: 'flex-end',
    alignItems: 'center',
    paddingBottom: 100,
  },
  button: {
    backgroundColor: '#FEE500',
    borderRadius: 40,
    borderWidth: 1,
    width: 250,
    height: 40,
    paddingHorizontal: 20,
    paddingVertical: 10,
    marginTop: 10,
  },
  text: {
    textAlign: 'center',
  },
});
