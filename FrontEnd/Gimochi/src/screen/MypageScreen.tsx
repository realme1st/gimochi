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
import EncryptedStorage from 'react-native-encrypted-storage';
import userSlice from '../slices/user';
import screenSlice from '../slices/screen';
import reloadSlice from '../slices/reload';
import { useAppDispatch } from '../store';
import { format } from 'date-fns';
import { URL } from '../api/API';
import { useSelector } from 'react-redux';
import { RootState } from '../store/reducer';
import axios from 'axios';

function MypageScreen() {
  const [result, setResult] = useState<string>('');
  const dispatch = useAppDispatch();
  const [tokenTime, setTokenTime] = useState<Date>();
  const accessToken = useSelector((state: RootState) => state.user.accessToken);

  const signOutWithKakao = async (): Promise<void> => {
    // 1. logout() 메서드 실행
    const message = await logout();
    // 2. AsyncStorage 'Login'값 변경, UserId 삭제
    await AsyncStorage.setItem('login', 'false');
    await AsyncStorage.removeItem('userId');
    await AsyncStorage.removeItem('userNickname');
    await EncryptedStorage.removeItem('accessToken');
    await EncryptedStorage.removeItem('accessTokenExpiresAt');
    setResult(message);
    console.log(message);
    // 3. redux isLogin값 변경
    // 4. redux screenName 홈으로 변경
    dispatch(
      screenSlice.actions.setScreen({
        screenName: 'HomeScreen',
      }),
    );
    dispatch(
      userSlice.actions.setLogin({
        isLogin: 'false',
        accessToken: '',
        accessTokenExpiresAt: '',
        userId: '',
      }),
    );
    dispatch(
      reloadSlice.actions.setReload({
        reload: String(new Date()),
      }),
    );
  };

  const getKakaoProfile = async (): Promise<void> => {
    const profile: KakaoProfile = await getProfile();

    setResult(JSON.stringify(profile));
  };

  const unlinkKakao = async (): Promise<void> => {
    const message = await unlink();

    setResult(message);
  };

  return (
    <View>
      <View style={styles.container}>
        <TouchableOpacity style={styles.button} onPress={() => getKakaoProfile()}>
          <Text style={styles.text}>프로필 조회</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={() => unlinkKakao()}>
          <Text style={styles.text}>카카오 언링크</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={() => signOutWithKakao()}>
          <Text style={styles.text}>카카오 로그아웃</Text>
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