/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import React from 'react';
import { Text, View, TouchableOpacity } from 'react-native';
import { RPNavigationProps } from '../../navigation/RPNavigation';

function RPMainScreen({ navigation }: RPNavigationProps) {
  const ID = 1;
  const goDetail = (id) => {
    navigation.navigate('RPDetailScreen', { RPId: id });
  };

  const goWrite = () => {
    navigation.navigate('RPWriteScreen');
  };

  return (
    <View>
      <Text>추카포카</Text>
      <TouchableOpacity onPress={() => goDetail(ID)}>
        <Text>추카포카 디테일</Text>
      </TouchableOpacity>
      <TouchableOpacity onPress={() => goWrite}>
        <Text>추카포카 작성</Text>
      </TouchableOpacity>
    </View>
  );
}

export default RPMainScreen;
