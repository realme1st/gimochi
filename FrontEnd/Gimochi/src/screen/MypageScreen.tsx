/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import React from 'react';
import axios from 'axios';
import { Text, View, LogBox, TouchableOpacity } from 'react-native';
import { Linking } from 'react-native';
import { WebView } from 'react-native-webview';
import AsyncStorage from '@react-native-async-storage/async-storage';

LogBox.ignoreLogs(['Remote debugger']);

const runFirst = 'window.ReactNativeWebView.postMessage("this is message from web");';

const MypageScreen = ({ navigation: { navigate } }) => {
  // const REST_API_KEY = process.env.REACT_APP_REST_API_KEY;
  const REST_API_KEY = '0e3c9cecfd800e2aae8228d69a635959';
  const REDIRECT_URI = 'http://localhost:8081/kakao/oauth';
  const KAKAO_AUTH_URI = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

  const parseAuthCode = async (url) => {
    const exp = 'code='; //url에 붙어 날라오는 인가코드는 code=뒤부터 parse하여 get
    const startIndex = url.indexOf(exp); //url에서 "code="으로 시작하는 index를 찾지 못하면 -1반환
    if (startIndex !== -1) {
      const authCode = url.substring(startIndex + exp.length);
      console.log('access code :: ' + authCode);
      console.log(url);
      // await axios
      //   .get('http://k7a205.p.ssafy.io/api/kakao/oauth/token', {
      //     header: {
      //       code: authCode,
      //     },
      //   })
      //   .then(function (res) {
      //     console.log(res);
      //   })
      //   .catch(function (error) {
      //     console.log(error);
      //   });
      console.log('hi');
    }
  };

  return (
    <View style={{ flex: 1 }}>
      <Text>로그인 화면a</Text>
      <WebView
        originWhitelist={['*']}
        scalesPageToFit={false}
        style={{ marginTop: 30 }}
        source={{
          uri: KAKAO_AUTH_URI,
        }}
        injectedJavaScript={runFirst}
        javaScriptEnabled={true}
        onMessage={(event) => {
          parseAuthCode(event.nativeEvent['url']);
        }}

        // onMessage ... :: webview에서 온 데이터를 event handler로 감지하여 parseAuthCood로 전달
      />
    </View>
  );
};

export default MypageScreen;
