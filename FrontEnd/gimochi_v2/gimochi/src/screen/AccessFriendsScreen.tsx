/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/no-floating-promises */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
import axios from 'axios';
import React, { useState } from 'react';
import { View, LogBox, Text } from 'react-native';
import { WebView } from 'react-native-webview';
import EncryptedStorage from 'react-native-encrypted-storage';
import { useAppDispatch } from '../store';
import userSlice from '../slices/user';

LogBox.ignoreLogs(['Remote debugger']);

const runFirst = `window.ReactNativeWebView.postMessage("this is message from web");`;

function AccessFriendsScreen({ navigation }) {
  const [fToken, setFToken] = useState('');
  const dispatch = useAppDispatch();
  const parseAuthCode = async (url: string) => {
    const exp = 'code='; //url에 붙어 날라오는 인가코드는 code=뒤부터 parse하여 get
    const startIndex = url.indexOf(exp); //url에서 "code="으로 시작하는 index를 찾지 못하면 -1반환
    if (startIndex !== -1) {
      const authCode = url.substring(startIndex + exp.length);
      console.log('access code :: ' + authCode);

      await axios
        .get('https://k7a205.p.ssafy.io/api/kakao/oauth/token', {
          headers: {
            code: authCode,
          },
        })
        .then(function (response) {
          console.log(response);
          console.log(response.data.data.userSocialToken);
          navigation.navigate('HomeScreen');
          after(response.data.data.userSocialToken);
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  };

  const after = async (token: string) => {
    await EncryptedStorage.setItem('accessToken', token);
    dispatch(
      userSlice.actions.setToken({
        accessToken: token,
      }),
    );
    console.log(token);
  };

  return (
    <View style={{ flex: 1 }}>
      <Text>친구목록 동의를 얻기 위함</Text>
      <WebView
        originWhitelist={['*']}
        scalesPageToFit={false}
        style={{ marginTop: 30 }}
        source={{
          uri: 'https://kauth.kakao.com/oauth/authorize?client_id=0e3c9cecfd800e2aae8228d69a635959&redirect_uri=https://k7a205.p.ssafy.io/kakao/oauth&response_type=code&scope=friends',
        }}
        injectedJavaScript={runFirst}
        javaScriptEnabled={true}
        onMessage={(event) => {
          parseAuthCode(event.nativeEvent['url']);
        }}

        // onMessage ... :: webview에서 온 데이터를 event handler로 잡아서 logInProgress로 전달
      />
    </View>
  );
}

export default AccessFriendsScreen;
