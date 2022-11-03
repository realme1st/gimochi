/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import React from 'react';
import { Text, View, TouchableOpacity } from 'react-native';
import { RPNavigationProps } from '../../navigation/RPNavigation';
import styled from 'styled-components';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faCirclePlus } from '@fortawesome/free-solid-svg-icons';

function RPMainScreen({ navigation }: RPNavigationProps) {
  const ID = 1;
  const goDetail = (id) => {
    navigation.navigate('RPDetailScreen', { RPId: id });
  };

  const goWrite = () => {
    navigation.navigate('RPWriteScreen');
  };

  return (
    <RPContainer>
      <RPTitle>추카포카</RPTitle>
      <TouchableOpacity onPress={() => goDetail(ID)}>
        <Text>추카포카 디테일</Text>
      </TouchableOpacity>
      <CreateButton onPress={goWrite}>
        <FontAwesomeIcon icon={faCirclePlus} size={50} color={'#ffa401'} />
      </CreateButton>
    </RPContainer>
  );
}

const RPContainer = styled.ScrollView`
  background-color: #ffffff;
`;

const RPTitle = styled.Text`
  font-family: 'Regular';
  font-size: 30px;
`;

const CreateButton = styled.TouchableOpacity`
  position: absolute;
  left: 320px;
  top: 480px;
`;

export default RPMainScreen;
