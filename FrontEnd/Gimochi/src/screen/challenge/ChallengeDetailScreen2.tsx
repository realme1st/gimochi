import React, { useState, useEffect } from 'react';
import { Text, ScrollView, View, TouchableOpacity, StyleSheet } from 'react-native';
import {
  BottomSheet,
  Button,
  ListItem,
  Input,
  Tab,
  TabView,
  ThemeProvider,
  createTheme,
  SpeedDial,
  Dialog,
  Icon,
} from '@rneui/themed';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';
import { useAppDispatch } from '../../store';
import reloadSlice from '../../slices/reload';
import { FlatGrid } from 'react-native-super-grid';
import FastImage from 'react-native-fast-image';

// @@@@@@@@@@@@@@@중간결과에서 원하는것만 복사해오자
function ChallengeDetailScreen2({ route, navigation }) {
  const [visibleG, setVisibleG] = useState(false);
  const [gifticons, setGifticons] = useState([]);
  const [loading, setLoading] = useState(true);
  const [myChallenge, setMyChallenge] = useState([]); // 챌린지 정보
  const [totalDay, setTotalDay] = useState(0); // 챌린지 정보
  const [usersInfo, setUsersInfo] = useState([]); // 챌린지 참여 목록
  const [myInfo, setMyInfo] = useState([]); // 챌린지 내 순위 기록

  const userId = useSelector((state: RootState) => state.user.userId);
  // const challengeId = route.params.challengeId;
  const challengeId = 16;

  const goMain = () => {
    navigation.navigate('ChallengeMainScreen');
  };
  const toggleG = () => {
    setVisibleG(!visibleG);
  };

  useEffect(() => {
    axios
      .get(`${Config.API_URL}/challenge/rewardInfo/${challengeId}}`)
      .then(function (response) {
        // console.log(response.data.data.gifticonList);
        setGifticons(response.data.data.gifticonList);
      })
      .catch(function (error) {
        console.log(error);
      });

    axios
      .get(`${Config.API_URL}/challenge/challengeInfo/rank/${challengeId}/${userId}`)
      .then(function (response) {
        console.log(response.data.data);
        setMyInfo(response.data.data);
      })
      .catch(function (error) {
        console.log(error);
      });

    async function myF() {
      await axios
        .all([
          axios.get(`${Config.API_URL}/challenge/` + challengeId),
          axios.get(`${Config.API_URL}/challenge/userList/` + challengeId),
        ])
        .then(
          axios.spread(async (response1, response2) => {
            // console.log(response1.data.data);
            setMyChallenge(response1.data.data);
            const temp =
              new Date(response1.data.data.challengeEndDate) -
              new Date(response1.data.data.challengeStartDate);
            const result = parseInt(temp / 86400000);
            setTotalDay(result + 1);
            // console.log(response2.data.data);
            let temp2 = response2.data.data;
            temp2.sort((a, b) => b.successCnt - a.successCnt);
            setUsersInfo(temp2);
          }),
        )
        .catch(function (error) {
          console.log(error);
        })
        .finally(() => setLoading(false));
    }
    myF();
  }, []);

  return (
    <View style={{ backgroundColor: '#fff', flex: 1 }}>
      <ScrollView>
        <View
          style={{
            flexDirection: 'row',
            height: 100,
            padding: 20,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>챌린지가 종료 되었습니다.</Text>
        </View>
        <View
          style={{
            flexDirection: 'row',
            height: 100,
            padding: 20,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>제목 / 기간 / 매일 </Text>
        </View>

        <View
          style={{
            flexDirection: 'row',
            height: 100,
            padding: 20,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>12/15, 달성률 80%, 2등</Text>
        </View>
        <View
          style={{
            flexDirection: 'row',
            height: 100,
            padding: 20,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }} onPress={() => toggleG()}>
            보상 보기 (1위만)
          </Text>
          <Dialog isVisible={visibleG} onBackdropPress={toggleG}>
            <Dialog.Title title='내 기프티콘 목록' />
            <Text>클릭시 등록하시겠습니까?</Text>
            <FlatGrid
              itemDimension={95}
              data={gifticons.filter((gifticon) => !gifticon.gifticonUsed)}
              style={{}}
              spacing={5}
              renderItem={({ item, index }) => (
                <>
                  <FastImage source={{ uri: item.gifticonPath }} style={{ width: 80, height: 80 }} />
                  <Text style={{ color: 'black', fontFamily: 'Regular' }}>{item.gifticonPeriod}</Text>
                  <Text style={{ color: 'black', fontFamily: 'Regular' }}>{item.gifticonStore}</Text>
                </>
              )}
            />
          </Dialog>
        </View>

        <View
          style={{
            flexDirection: 'row',
            height: 100,
            padding: 20,
            marginRight: 60,
          }}
        >
          <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>전체 순위</Text>
        </View>
      </ScrollView>
      <Icon
        name='delete'
        type='material'
        color='#FFE7BC'
        size={25}
        reverse
        reverseColor='#FFA401'
        onPress={() => goMain()}
        iconStyle={{ fontSize: 33 }}
        containerStyle={{ position: 'absolute', top: '85%', left: '5%' }}
      />
    </View>
  );
}

export default ChallengeDetailScreen2;
