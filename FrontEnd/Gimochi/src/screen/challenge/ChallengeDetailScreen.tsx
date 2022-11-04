import React from 'react';
import { View, Text, TouchableOpacity, ScrollView } from 'react-native';
import { URL } from '../../api/API';
import axios from 'axios';
import styled from 'styled-components/native';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';

function ChallengeDetailScreen({ route, navigation }) {
  const userId = useSelector((state: RootState) => state.user.userId);
  console.log(userId);

  const challegneId = route.params.challengeId;
  console.log(challegneId);
  const goMain = () => {
    navigation.navigate('ChallengeMainScreen');
  };

  const test1 = async () => {
    await axios
      .get(`${URL}/challenge`)
      .then(function (response) {
        console.log('야호');
        console.log(response);
        // redux(token.accessToken, String(token.accessTokenExpiresAt));
      })
      .catch(function (error) {
        console.log('ㅠㅠ');
        console.log(error);
      });
  };
  const test2 = async () => {
    const jsonData = {
      challengeDescription: '테스트테스트',
      challengeEndTime: '2022.11.30 13:00:00',
      challengeLeaderId: 4,
      challengeLeaderName: '전민재',
      challengeParticipant: 4,
      challengeRewardType: 1,
      challengeStartTime: '2022.11.05 13:00:00',
      challengeTitle: '1일 1커밋',
    };
    await axios
      .post(`${URL}/challenge`, jsonData)
      .then(function (response) {
        console.log('야호');
        console.log(response);
      })
      .catch(function (error) {
        console.log('ㅠㅠ');
        console.log(error);
      });
  };
  const test3 = async (challengeId) => {
    await axios
      .get(`${URL}/challenge/` + challengeId)
      .then(function (response) {
        console.log('야호');
        console.log(response);
      })
      .catch(function (error) {
        console.log('ㅠㅠ');
        console.log(error);
      });
  };
  const test4 = async (challengeId) => {
    await axios
      .delete(`${URL}/challenge/` + challengeId)
      .then(function (response) {
        console.log('야호');
        console.log(response);
      })
      .catch(function (error) {
        console.log('ㅠㅠ');
        console.log(error);
      });
  };

  const test5 = async () => {
    const jsonData = {
      challengeId: 10,
      userId: 4,
    };
    await axios
      .post(`${URL}/challenge/challengeInvite`, jsonData)
      .then(function (response) {
        console.log('야호');
        console.log(response);
      })
      .catch(function (error) {
        console.log('ㅠㅠ');
        console.log(error);
      });
  };

  const test6 = async (userId) => {
    await axios
      .get(`${URL}/challenge/challengeInvite/` + userId)
      .then(function (response) {
        console.log('야호');
        console.log(response);
      })
      .catch(function (error) {
        console.log('ㅠㅠ');
        console.log(error);
      });
  };

  const test7 = async (challengeInviteId) => {
    await axios
      .post(`${URL}/challenge/challengeInvite/accept/` + challengeInviteId)
      .then(function (response) {
        console.log('야호');
        console.log(response);
      })
      .catch(function (error) {
        console.log('ㅠㅠ');
        console.log(error);
      });
  };
  const test8 = async (userId) => {
    await axios
      .get(`${URL}/challenge/challengeList/` + userId)
      .then(function (response) {
        console.log('야호');
        console.log(response);
      })
      .catch(function (error) {
        console.log('ㅠㅠ');
        console.log(error);
      });
  };
  const test9 = async (challengeId) => {
    await axios
      .get(`${URL}/challenge/userList/` + challengeId)
      .then(function (response) {
        console.log('야호');
        console.log(response);
      })
      .catch(function (error) {
        console.log('ㅠㅠ');
        console.log(error);
      });
  };

  return (
    <ScrollView>
      <Text>챌린지 상세보기</Text>
      <TouchableOpacity onPress={goMain}>
        <Text>추카포카 메인으로 </Text>
      </TouchableOpacity>

      <TButton onPress={() => test1()}>
        <Text>모든 챌린지 조회 </Text>
      </TButton>
      <TButton onPress={() => test2()}>
        <Text>챌린지 생성 </Text>
      </TButton>

      <TButton onPress={() => test3(11)}>
        <Text>챌린지 조회 @challengeId</Text>
      </TButton>
      <TButton onPress={() => test4(10)}>
        <Text>!!챌린지 삭제 @challengeId </Text>
      </TButton>
      <TButton onPress={() => test5()}>
        <Text>초대장 전송 </Text>
      </TButton>
      <TButton onPress={() => test6(userId)}>
        <Text>??참가한 초대장 조회 @userId </Text>
      </TButton>
      <TButton onPress={() => test7(1)}>
        <Text>초대장 수락 @challengeInviteId </Text>
      </TButton>
      <TButton onPress={() => test8(userId)}>
        <Text> 유저 참여 챌린지 조회 @userId </Text>
      </TButton>
      <TButton onPress={() => test9(10)}>
        <Text> 챌린지 참여한 사용자 조회 @challengeId </Text>
      </TButton>
    </ScrollView>
  );
}
const TButton = styled.TouchableOpacity`
  background-color: #fee500;
  border-radius: 18px;
  width: 60%;
  padding: 15px;
  margin: 10px 0px;
  justify-content: center;
  align-items: center;
`;

export default ChallengeDetailScreen;
