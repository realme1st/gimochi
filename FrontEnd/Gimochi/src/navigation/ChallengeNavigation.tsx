import React from 'react';
import { createNativeStackNavigator, NativeStackScreenProps } from '@react-navigation/native-stack';
import ChallengeMainScreen from '../screen/challenge/ChallengeMainScreen';
import ChallengeDetailScreen from '../screen/challenge/ChallengeDetailScreen';
import ChallengeCreateScreen from '../screen/challenge/ChallengeCreateScreen';

export type ChallengeStackParamList = {
  ChallengeMainScreen: undefined;
  ChallengeDetailScreen: undefined;
  ChallengeCreateScreen: undefined;
};

const Challenge = createNativeStackNavigator<ChallengeStackParamList>();

function ChallengeNavigation() {
  return (
    <Challenge.Navigator
      initialRouteName='ChallengeMainScreen'
      screenOptions={{
        headerTitleAlign: 'center',
        headerTitleStyle: {
          fontFamily: 'Regular',
        },
      }}
    >
      <Challenge.Screen
        name='ChallengeMainScreen'
        component={ChallengeMainScreen}
        options={{ title: '챌린지' }}
      ></Challenge.Screen>
      <Challenge.Screen
        name='ChallengeDetailScreen'
        component={ChallengeDetailScreen}
        options={{ title: '챌린지 상세보기' }}
      ></Challenge.Screen>
      <Challenge.Screen
        name='ChallengeCreateScreen'
        component={ChallengeCreateScreen}
        options={{ title: '챌린지 생성하기' }}
      ></Challenge.Screen>
    </Challenge.Navigator>
  );
}

export default ChallengeNavigation;
