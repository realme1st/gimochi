import React, { useLayoutEffect } from 'react';
import { View, Text, Pressable, StyleSheet, TouchableOpacity } from 'react-native';
import { getFocusedRouteNameFromRoute } from '@react-navigation/native';

function HomeModal({ navigation, route }) {
  const goHome = () => {
    navigation.goBack();
    navigation.navigate('HomeScreen');
  };

  const goAttendance = () => {
    // goBack을 하지 않으면 이동한 스택에서 뒤로가면 모달이 뜸, 그래서 스택을 뒤로 옮기고 다음 스크린으로 이동
    navigation.goBack();
    navigation.navigate('AttendanceScreen');
  };

  const goPlay = () => {
    navigation.goBack();
    navigation.navigate('PlayScreen');
  };

  const goRollingpaper = () => {
    navigation.goBack();
    navigation.navigate('RollingpaperScreen');
  };

  const goSchedule = () => {
    navigation.goBack();
    navigation.navigate('ScheduleScreen');
  };

  const goChallenge = () => {
    navigation.goBack();
    navigation.navigate('ChallengeScreen');
  };

  return (
    <View style={{ flex: 1 }}>
      <Pressable
        style={[StyleSheet.absoluteFill, { backgroundColor: 'rgba(0, 0, 0, 0.3)' }]}
        onPress={navigation.goBack}
      />
      <View
        style={{ width: '100%', height: '30%', position: 'absolute', bottom: 0, backgroundColor: 'white' }}
      >
        <Text style={{ textAlign: 'center' }}>Create Posts !! This is Modal</Text>
        <TouchableOpacity onPress={() => goHome()}>
          <Text>홈</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={() => goAttendance()}>
          <Text>출첵</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={() => goPlay()}>
          <Text>놀이터</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={() => goRollingpaper()}>
          <Text>추카포카</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={() => goSchedule()}>
          <Text>일정관리</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={() => goChallenge()}>
          <Text>챌린지콘</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

export default HomeModal;
