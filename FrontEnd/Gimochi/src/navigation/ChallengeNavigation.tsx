import React from 'react';
import { createNativeStackNavigator, NativeStackScreenProps } from '@react-navigation/native-stack';
import ChallengeMainScreen from '../screen/challenge/ChallengeMainScreen';
import ChallengeDetailScreen from '../screen/challenge/ChallengeDetailScreen';
import ChallengeCreateScreen1 from '../screen/challenge/ChallengeCreateScreen1';
import ChallengeCreateScreen2 from '../screen/challenge/ChallengeCreateScreen2';

export type ChallengeStackParamList = {
  ChallengeMainScreen: undefined;
  ChallengeDetailScreen: undefined;
  ChallengeCreateScreen1: undefined;
  ChallengeCreateScreen2: undefined;
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
        name='ChallengeCreateScreen1'
        component={ChallengeCreateScreen1}
        options={{ title: '챌린지 생성하기1' }}
      ></Challenge.Screen>
      <Challenge.Screen
        name='ChallengeCreateScreen2'
        component={ChallengeCreateScreen2}
        options={{ title: '챌린지 생성하기2' }}
      ></Challenge.Screen>
    </Challenge.Navigator>
  );
}

export default ChallengeNavigation;
