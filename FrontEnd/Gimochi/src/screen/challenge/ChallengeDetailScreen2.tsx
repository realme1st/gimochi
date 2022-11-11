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

function ChallengeDetailScreen2({ route, navigation }) {
  const userId = useSelector((state: RootState) => state.user.userId);
  console.log(userId);

  const challegneId = route.params.challengeId;
  console.log(challegneId);
  const goMain = () => {
    navigation.navigate('ChallengeMainScreen');
  };

  return (
    <View style={{ backgroundColor: '#fff', flex: 1 }}>
      <ScrollView>
        <View
          style={{
            flexDirection: 'row',
            height: 100,
            padding: 20,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>챌린지가 종료 되었습니다.</Text>
        </View>
        <View
          style={{
            flexDirection: 'row',
            height: 100,
            padding: 20,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>제목 / 기간 / 매일 </Text>
        </View>
        <View
          style={{
            flexDirection: 'row',
            height: 100,
            padding: 20,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>
            결과 자세히보기 (진행중 했던 기록 )
          </Text>
        </View>

        <View
          style={{
            flexDirection: 'row',
            height: 100,
            padding: 20,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>12/15, 달성률 80%, 2등</Text>
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

export default ChallengeDetailScreen2;
