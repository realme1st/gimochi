/* eslint-disable @typescript-eslint/require-await */
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
import screenSlice from '../slices/screen';
import { useAppDispatch } from '../store';
import { format } from 'date-fns';

function MypageScreen() {
  const [result, setResult] = useState<string>('');
  const dispatch = useAppDispatch();
  const [tokenTime, setTokenTime] = useState<Date>();

  const signInWithKakao = async (): Promise<void> => {
    // 1. 로그인 메서드 실행
    const token: KakaoOAuthToken = await login();
    // 3. AsyncStorage에 Login값 변경
    await AsyncStorage.setItem('Login', 'true');
    // 4. EncryptedStorage, redux에 accessToken, accessTokenExpiresAt 값 저장
    // 5. redux isLogin값 변경
    dispatch(
      userSlice.actions.setLogin({
        isLogin: 'true',
      }),
    );
    console.log(token);
    console.log(token.accessTokenExpiresAt);
    setTokenTime(token.accessTokenExpiresAt);
  };

  const signOutWithKakao = async (): Promise<void> => {
    // 1. logout() 메서드 실행
    const message = await logout();
    // 2. AsyncStorage 'Login'값 변경
    await AsyncStorage.setItem('Login', 'false');
    setResult(message);
    console.log(message);
    // 3. redux isLogin값 변경
    dispatch(
      userSlice.actions.setLogin({
        isLogin: 'false',
      }),
    );
    // 4. redux screenName 홈으로 변경
    dispatch(
      screenSlice.actions.setScreen({
        screenName: 'HomeScreen',
      }),
    );
    // 5. 백에 accessToken, refreshToken 지우는 axios 요청 보내기
  };

  const getKakaoProfile = async (): Promise<void> => {
    const profile: KakaoProfile = await getProfile();

    setResult(JSON.stringify(profile));
  };

  // const unlinkKakao = async (): Promise<void> => {
  //   const message = await unlink();

  //   setResult(message);
  // };

  const unlinkKakao = async (): Promise<void> => {
    const date = new Date();
    const now = format(date, 'yyyy-MM-dd HH:mm:ss');
    const time1 = new Date(tokenTime);
    console.log(now);
    console.log(tokenTime);
    console.log(time1);
    console.log(format(time1 - date, 'HH') >= 2);
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
          <Text style={styles.text}>테스트</Text>
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

export default MypageScreen;

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
