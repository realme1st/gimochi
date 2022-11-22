import React, { useState, useEffect } from 'react';
import { Text, ScrollView, View, TouchableOpacity, StyleSheet } from 'react-native';
import {
  BottomSheet,
  Button,
  ListItem,
  Input,
  Tab,
  TabView,
  ThemeProvider,
  createTheme,
  SpeedDial,
  Dialog,
  Icon,
} from '@rneui/themed';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';
import { useAppDispatch } from '../../store';
import reloadSlice from '../../slices/reload';

function ChallengeDetailScreen0({ route, navigation }) {
  const userId = useSelector((state: RootState) => state.user.userId);
  console.log(userId);

  const challegneId = route.params.challengeId;
  console.log(challegneId);
  // const myList = [];
  const goMain = async () => {
    // const response = await axios.get(`${Config.API_URL}/challenge/challengeList/` + userId);
    // console.log(response.data.data);
    // response.data.data.forEach((chall) => {
    //   // console.log(chall.challengeId);
    //   myList.push(chall.challengeId);
    // });
    // console.log(myList);
    // navigation.navigate('ChallengeDetailScreen1', { myList: myList });

    navigation.navigate('ChallengeMainScreen');
  };

  return (
    <View style={{ backgroundColor: '#fff', flex: 1 }}>
      <ScrollView>
        <View
          style={{
            flexDirection: 'row',
            height: 80,
            padding: 20,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>제목{'  '}:</Text>
        </View>
        <View
          style={{
            flexDirection: 'row',
            height: 80,
            padding: 20,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>기간{'  '}: S~E매일</Text>
        </View>
        <View
          style={{
            flexDirection: 'row',
            height: 150,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>
            참가 인원 1명 | 친구초대(친구목록(보내기/취소하기)|초대현황(대기중/참가중/취소아이콘)):
          </Text>
        </View>
        <View
          style={{
            flexDirection: 'row',
            height: 80,
            padding: 20,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>
            누적 상금|기프티콘 /?아이콘
          </Text>
        </View>
        <View
          style={{
            flexDirection: 'row',
            height: 80,
            padding: 20,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>인증 방법 확인하기 (모달)</Text>
        </View>
        <View
          style={{
            flexDirection: 'row',
            height: 80,
            padding: 20,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>
            타임스탬프 00되면 자동시작
          </Text>
        </View>
      </ScrollView>
      <Icon
        name='delete'
        type='material'
        color='#FFE7BC'
        size={25}
        reverse
        reverseColor='#FFA401'
        onPress={() => goMain()}
        iconStyle={{ fontSize: 33 }}
        containerStyle={{ position: 'absolute', top: '85%', left: '5%' }}
      />
    </View>
  );
}

export default ChallengeDetailScreen0;
