/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import React, { useEffect, useState } from 'react';
import { Text, View, TouchableOpacity } from 'react-native';
import { RPNavigationProps } from '../../navigation/RPNavigation';
import styled from 'styled-components/native';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faCirclePlus, faCalendar } from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';
import { URL } from '../../api/API';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';

function RPMainScreen({ navigation }: RPNavigationProps) {
  const userId = useSelector((state: RootState) => state.user.userId);
  const userNickname = useSelector((state: RootState) => state.user.userNickname);
  const [myRPList, setMyRPList] = useState([]);
  console.log(userId);
  // useEffect쓸때 [reload] 무지성 복붙할것
  const reload = useSelector((state: RootState) => state.reload.reload);
  useEffect(() => {
    axios
      .get(`${URL}/session/user/${userId}`)
      .then(function (response) {
        console.log(response.data.data);
        setMyRPList(response.data.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [reload]);
  const goDetail = (id) => {
    navigation.navigate('RPDetailScreen', { RPId: id });
  };

  const goWrite = () => {
    navigation.navigate('RPWriteScreen');
  };

  return (
    <RPContainer>
      <RPTitleContainer>
        <RPTitle>{userNickname}님의 추카포카</RPTitle>
      </RPTitleContainer>
      <RPListContainer>
        {myRPList.map((RP, index) => (
          <RPItemButton key={index} onPress={() => goDetail(RP.sessionId)}>
            <RPItemContainer>
              <FontAwesomeIcon icon={faCalendar} size={20} />
              <RPListText>{RP.anniversary}</RPListText>
              {RP.sessionTypeId === 1 && <RPListText>생일</RPListText>}
              {RP.sessionTypeId === 2 && <RPListText>졸업</RPListText>}
              {RP.sessionTypeId === 3 && <RPListText>크리스마스</RPListText>}
              {RP.sessionTypeId === 4 && <RPListText>설날</RPListText>}
              {RP.sessionTypeId === 5 && <RPListText>{RP.name}</RPListText>}
            </RPItemContainer>
          </RPItemButton>
        ))}
      </RPListContainer>
      <CreateButton onPress={goWrite}>
        <FontAwesomeIcon icon={faCirclePlus} size={50} color={'#ffa401'} />
      </CreateButton>
    </RPContainer>
  );
}

const RPContainer = styled.View`
  background-color: #ffffff;
  flex: 1;
`;

const RPTitle = styled.Text`
  font-family: 'Regular';
  font-size: 30px;
`;
const RPTitleContainer = styled.View``;

const RPListContainer = styled.View``;

const RPItemButton = styled.TouchableOpacity``;

const RPItemContainer = styled.View`
  align-items: center;
  flex-direction: row;
`;

const RPListText = styled.Text`
  font-family: 'Regular';
  font-size: 20px;
  margin-left: 5%;
`;

const CreateButton = styled.TouchableOpacity`
  position: absolute;
  left: 320px;
  top: 480px;
`;

export default RPMainScreen;
