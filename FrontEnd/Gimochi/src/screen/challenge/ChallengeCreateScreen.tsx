import React from 'react';
import { View, Text, TouchableOpacity } from 'react-native';

function ChallengeCreateScreen({ navigation }) {
  const goMain = () => {
    navigation.navigate('ChallengeMainScreen');
  };
  return (
    <View>
      <Text>챌린지 생성합니다</Text>

      <TouchableOpacity onPress={goMain}>
        <Text>추카포카 메인으로 </Text>
      </TouchableOpacity>
    </View>
  );
}

export default ChallengeCreateScreen;
