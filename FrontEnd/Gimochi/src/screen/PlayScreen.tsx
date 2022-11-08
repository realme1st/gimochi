/* eslint-disable @typescript-eslint/restrict-plus-operands */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
// import React, { useState, useEffect } from 'react';
// import { TouchableOpacity, StyleSheet, Text, View } from 'react-native';
// import { useAppDispatch } from '../store';
// import screenSlice from '../slices/screen';

// function PlayScreen() {
//   const dispatch = useAppDispatch();

//   useEffect(() => {
//     dispatch(
//       screenSlice.actions.addScreen({
//         screen: 'PlayScreen',
//       }),
//     );
//     return () => {
//       console.log('unmount');
//       dispatch(screenSlice.actions.deleteScreen());
//     };
//   }, []);

//   return (
//     <View>
//       <Text>놀이터</Text>
//     </View>
//   );
// }

// export default PlayScreen;

import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';
import React from 'react';
import { View, LogBox, Text } from 'react-native';
import { WebView } from 'react-native-webview';

LogBox.ignoreLogs(['Remote debugger']);

const runFirst = `window.ReactNativeWebView.postMessage("this is message from web");`;

const PlayScreen = ({ navigation }) => {
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
        })
        .catch(function (error) {
          console.log(error);
        });

      navigation.navigate('HomeScreen');
    }
  };

  return (
    <View style={{ flex: 1 }}>
      <Text>로그인 화면</Text>
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
};

export default PlayScreen;
