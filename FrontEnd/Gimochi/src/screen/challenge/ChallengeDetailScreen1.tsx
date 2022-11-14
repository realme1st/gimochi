import React, { useState, useEffect } from 'react';
import { Text, Image, ScrollView, View, TouchableOpacity, StyleSheet } from 'react-native';
import {
  BottomSheet,
  Button,
  ListItem,
  Slider,
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

function ChallengeDetailScreen1({ route, navigation }) {
  const userId = useSelector((state: RootState) => state.user.userId);
  const reload = useSelector((state: RootState) => state.reload.reload);
  const dispatch = useAppDispatch();
  const [index, setIndex] = useState(0);
  const challegneId = route.params.challengeId;
  console.log(challegneId);

  const [visibleDialogQ, setVisibleDialogQ] = useState(false);
  const [visibleDialogF, setVisibleDialogF] = useState(false);
  const [visibleDialogG, setVisibleDialogG] = useState(false);

  const toggleDialogQ = () => {
    setVisibleDialogQ(!visibleDialogQ);
  };
  const toggleDialogF = () => {
    setVisibleDialogF(!visibleDialogF);
  };
  const toggleDialogG = () => {
    setVisibleDialogG(!visibleDialogG);
  };

  const [value, setValue] = useState(0);
  const [vertValue, setVertValue] = useState(0);

  const interpolate = (start: number, end: number) => {
    let k = (value - 0) / 10; // 0 =>min  && 10 => MAX
    return Math.ceil((1 - k) * start + k * end) % 256;
  };

  const color = () => {
    let r = interpolate(255, 0);
    let g = interpolate(0, 255);
    let b = interpolate(0, 0);
    return `rgb(${r},${g},${b})`;
  };

  return (
    <View style={{ backgroundColor: '#ffffff', flex: 1 }}>
      <Tab
        value={index}
        onChange={(e) => setIndex(e)}
        indicatorStyle={{
          height: 0,
        }}
        style={{
          borderRadius: 21,
          backgroundColor: '#F6F6F6',
          marginTop: 21,
          marginHorizontal: 10,
          height: 42,
        }}
        variant='primary'
      >
        <Tab.Item
          title='달성률'
          containerStyle={{
            borderRadius: 20,
            backgroundColor: index == 0 ? 'white' : '#F6F6F6',
            margin: 2,
            height: 38,
            padding: 0,
          }}
          // buttonStyle={{}}
          titleStyle={{
            fontSize: 20,
            color: index == 0 ? '#FFA401' : '#686868',
            paddingHorizontal: 0,
            paddingVertical: 0,
            fontWeight: '900',
          }}
        />
        <Tab.Item
          title='인증샷'
          containerStyle={{
            borderRadius: 20,
            backgroundColor: index == 1 ? 'white' : '#F6F6F6',
            margin: 2,
            height: 38,
            padding: 0,
          }}
          // buttonStyle={{}}
          titleStyle={{
            fontSize: 20,
            color: index == 1 ? '#FFA401' : '#686868',
            paddingHorizontal: 0,
            paddingVertical: 0,
          }}
        />
      </Tab>

      <TabView value={index} onChange={setIndex} animationType='spring'>
        <TabView.Item style={{ backgroundColor: 'white', width: '100%' }}>
          <ScrollView>
            <Text>달성률 70% 오늘 끝or 3시간남음 어싱크스토리지해야됨참고(눈속임은 리덕스)</Text>
            <Text>최대 100%까지 달성 가능해요! 100% 달성으로 상품을 노려보세요!</Text>
            <Text>기간표시</Text>
            <Icon
              name='flag-checkered'
              type='font-awesome-5'
              size={20}
              // containerStyle={{ bottom: 20, right: 20 }}
            />
            <View style={{ padding: 20, width: '100%', justifyContent: 'center', alignItems: 'stretch' }}>
              <Text style={{ paddingTop: 20 }}>Value: {value}</Text>
              <Slider
                value={value}
                onValueChange={setValue}
                maximumValue={10}
                minimumValue={0}
                step={1}
                allowTouchTrack
                trackStyle={{ height: 5, backgroundColor: 'transparent' }}
                thumbStyle={{ height: 20, width: 20, backgroundColor: 'transparent' }}
                thumbProps={{
                  children: (
                    <Icon
                      name='circle'
                      type='font-awesome'
                      size={20}
                      containerStyle={{ bottom: 0, right: 0 }}
                      color={color()}
                    />
                  ),
                }}
              />
            </View>
            <Text>인증성공 1개| 인증 실패 2개 | 남은 인증 12개</Text>
            <Text>인증샷 기록 나열 or 달력</Text>
            <Text style={{ marginVertical: 20 }} onPress={() => toggleDialogG()}>
              총상금 or 기프티콘 누르면 설명
            </Text>
            <Dialog isVisible={visibleDialogG} onBackdropPress={toggleDialogG}>
              <Dialog.Title title='기프티콘/ 포인트 총상금 관련' />
              <Text>두두두둗</Text>
            </Dialog>
            <Text style={{ marginVertical: 20 }} onPress={() => toggleDialogF()}>
              현재 나의 순위 / 모달 랭킹
            </Text>
            <Dialog isVisible={visibleDialogF} onBackdropPress={toggleDialogF}>
              <Dialog.Title title='랭킹 화면 ' />
              <Text>두두두둗</Text>
              {/* <Image
                source={require('../../assets/images/leader.png')}
                resizeMode='contain'
                style={{ width: 80, height: 80 }}
              /> */}
            </Dialog>
          </ScrollView>
        </TabView.Item>
        <TabView.Item style={{ backgroundColor: 'white', width: '100%' }}>
          <ScrollView>
            <Icon
              name='camera'
              type='font-awesome'
              size={20}
              // containerStyle={{ bottom: 20, right: 20 }}
            />
            <Text>인증하기</Text>
            <Icon name='question' type='octicon' size={20} color='black' onPress={() => toggleDialogQ()} />

            <Dialog isVisible={visibleDialogQ} onBackdropPress={toggleDialogQ}>
              <Dialog.Title title='인증샷 설명' />
              <Text>두두두둗</Text>
            </Dialog>
            <Text>가져오기</Text>
          </ScrollView>
        </TabView.Item>
      </TabView>
    </View>
  );
}

export default ChallengeDetailScreen1;
