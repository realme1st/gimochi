import React, { useState } from 'react';
import { ScrollView, Text, View, TouchableOpacity } from 'react-native';
import Sliders from './ss';

function ChallengeMainScreen({ navigation }) {
  const goDetail = (id) => {
    navigation.navigate('ChallengeDetailScreen', { challengeId: id });
  };

  const goWrite = () => {
    navigation.navigate('ChallengeCreateScreen');
  };

  return (
    <ScrollView>
      <Text>챌린지 진행 / 완료 조건부 랜더링 | 챌린지 목록들 보여줄 예정입니다.</Text>
      <TouchableOpacity onPress={() => goDetail(12)}>
        <Text>추카포카 상세보기 버튼</Text>
      </TouchableOpacity>
      <TouchableOpacity onPress={goWrite}>
        <Text>추카포카 생성하기 버튼</Text>
      </TouchableOpacity>

      <Sliders></Sliders>
    </ScrollView>
  );
}

export default ChallengeMainScreen;
