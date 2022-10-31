/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable react/jsx-key */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import React, { useLayoutEffect } from 'react';
import styled from 'styled-components/native';
import { View, Text, Pressable, StyleSheet, TouchableOpacity, Image } from 'react-native';
import { getFocusedRouteNameFromRoute } from '@react-navigation/native';
import RoundMenu from './RoundMenu';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import {
  faEnvelope,
  faSquareCheck,
  faCalendarDays,
  faTicket,
  faTrophy,
} from '@fortawesome/free-solid-svg-icons';
import { useAppDispatch } from '../store';
import { useSelector } from 'react-redux';
import screenSlice from '../slices/screen';
import { RootState } from '../store/reducer';
import { HomeModalProps } from '../navigation/HomeNavigation';

function HomeModal({ navigation }: HomeModalProps) {
  const currentScreen = useSelector((state: RootState) => state.screen.screenName);
  // console.log(currentScreen);

  const dispatch = useAppDispatch();

  const goHome = () => {
    navigation.goBack();
    navigation.navigate('HomeScreen');
    dispatch(
      screenSlice.actions.setScreen({
        screenName: 'HomeScreen',
      }),
    );
  };

  const goAttendance = () => {
    // goBack을 하지 않으면 이동한 스택에서 뒤로가면 모달이 뜸, 그래서 스택을 뒤로 옮기고 다음 스크린으로 이동
    navigation.goBack();
    navigation.navigate('AttendanceScreen');
    dispatch(
      screenSlice.actions.setScreen({
        screenName: 'AttendanceScreen',
      }),
    );
  };

  const goPlay = () => {
    navigation.goBack();
    navigation.navigate('PlayScreen');
    dispatch(
      screenSlice.actions.setScreen({
        screenName: 'PlayScreen',
      }),
    );
  };

  const goRollingpaper = () => {
    navigation.goBack();
    navigation.navigate('RollingpaperScreen');
    dispatch(
      screenSlice.actions.setScreen({
        screenName: 'RollingpaperScreen',
      }),
    );
  };

  const goSchedule = () => {
    navigation.goBack();
    navigation.navigate('ScheduleScreen');
    dispatch(
      screenSlice.actions.setScreen({
        screenName: 'ScheduleScreen',
      }),
    );
  };

  const goChallenge = () => {
    navigation.goBack();
    navigation.navigate('ChallengeScreen');
    dispatch(
      screenSlice.actions.setScreen({
        screenName: 'ChallengeScreen',
      }),
    );
  };

  const content = [
    <MenuButtonContainer onPress={goRollingpaper}>
      <FontAwesomeIcon icon={faEnvelope} size={40} color='#ffa401' />
      <MenuButtonText>추카포카</MenuButtonText>
    </MenuButtonContainer>,
    <MenuButtonContainer onPress={goAttendance}>
      <FontAwesomeIcon icon={faSquareCheck} size={40} color='#ffa401' />
      <MenuButtonText>출석체크</MenuButtonText>
    </MenuButtonContainer>,
    <MenuButtonContainer onPress={goSchedule}>
      <FontAwesomeIcon icon={faCalendarDays} size={40} color='#ffa401' />
      <MenuButtonText>일정관리</MenuButtonText>
    </MenuButtonContainer>,
    <MenuButtonContainer onPress={goPlay}>
      <FontAwesomeIcon icon={faTicket} size={40} color='#ffa401' />
      <MenuButtonText>놀이터</MenuButtonText>
    </MenuButtonContainer>,
    <MenuButtonContainer onPress={goChallenge}>
      <FontAwesomeIcon icon={faTrophy} size={40} color='#ffa401' />
      <MenuButtonText>챌린지콘</MenuButtonText>
    </MenuButtonContainer>,
  ];

  return (
    <View style={{ flex: 1 }}>
      <Pressable
        style={[StyleSheet.absoluteFill, { backgroundColor: 'rgba(0, 0, 0, 0.3)' }]}
        onPress={navigation.goBack}
      />
      <View
        style={{
          width: '100%',
          height: '45%',
          position: 'absolute',
          bottom: 0,
          backgroundColor: 'white',
          alignItems: 'center',
        }}
      >
        <RoundMenu
          rotateCenterImage={true}
          size={300}
          centerContent={
            <TouchableOpacity onPress={goHome}>
              {currentScreen === 'HomeScreen' && (
                <Image
                  source={require('../assets/images/homeMochi.png')}
                  resizeMode='contain'
                  style={{ width: 80, height: 80 }}
                />
              )}
              {currentScreen === 'AttendanceScreen' && (
                <Image
                  source={require('../assets/images/homeMochi.png')}
                  resizeMode='contain'
                  style={{ width: 70, height: 70 }}
                />
              )}
              {currentScreen === 'PlayScreen' && (
                <Image
                  source={require('../assets/images/homeMochi.png')}
                  resizeMode='contain'
                  style={{ width: 60, height: 60 }}
                />
              )}
              {currentScreen === 'RollingpaperScreen' && (
                <Image
                  source={require('../assets/images/homeMochi.png')}
                  resizeMode='contain'
                  style={{ width: 50, height: 50 }}
                />
              )}
              {currentScreen === 'ScheduleScreen' && (
                <Image
                  source={require('../assets/images/homeMochi.png')}
                  resizeMode='contain'
                  style={{ width: 40, height: 40 }}
                />
              )}
              {currentScreen === 'ChallengeScreen' && (
                <Image
                  source={require('../assets/images/homeMochi.png')}
                  resizeMode='contain'
                  style={{ width: 30, height: 30 }}
                />
              )}
            </TouchableOpacity>
          }
          content={content}
        />
      </View>
    </View>
  );
}

const MenuButtonContainer = styled.TouchableOpacity`
  align-items: center;
`;

const MenuButtonText = styled.Text`
  font-family: 'Regular';
  color: #686868;
  margin-top: 5%;
`;

export default HomeModal;
