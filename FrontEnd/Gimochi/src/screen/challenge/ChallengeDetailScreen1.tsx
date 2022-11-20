import React, { useState, useEffect } from 'react';
import { Text, Image, ScrollView, View, TouchableOpacity, StyleSheet } from 'react-native';
import {
  BottomSheet,
  Button,
  ListItem,
  Slider,
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

function ChallengeDetailScreen1({ route, navigation }) {
  const userId = useSelector((state: RootState) => state.user.userId);
  const reload = useSelector((state: RootState) => state.reload.reload);
  const dispatch = useAppDispatch();
  const [index, setIndex] = useState(0);
  const [visibleDialogQ, setVisibleDialogQ] = useState(false);
  const [visibleDialogF, setVisibleDialogF] = useState(false);
  const [visibleDialogG, setVisibleDialogG] = useState(false);
  const [visibleListG, setVisibleListG] = useState(false);
  const [myAuthLists, setMyAuthLists] = useState(false);
  const [allAuthLists, setAllAuthLists] = useState(false);
  const [loading, setLoading] = useState(true);
  const [myChallenge, setMyChallenge] = useState([]); // 챌린지 정보
  const [totalDay, setTotalDay] = useState(0); // 챌린지 정보 날짜 총
  const [usersInfo, setUsersInfo] = useState([]); // 챌린지 참여 목록
  const [myInfo, setMyInfo] = useState([]); // 챌린지 내 순위 기록
  const [chGifticons, setChGifticons] = useState([]);
  // const challengeId = route.params.challengeId;
  const challengeId = 16;

  const toggleDialogQ = () => {
    setVisibleDialogQ(!visibleDialogQ);
  };
  const toggleDialogF = () => {
    setVisibleDialogF(!visibleDialogF);
  };
  const toggleDialogG = () => {
    setVisibleDialogG(!visibleDialogG);
  };
  const toggleListG = () => {
    setVisibleListG(!visibleListG);
  };

  const interpolate = (start: number, end: number) => {
    let k = (successRate(myInfo.successCnt) - 0) / 10; // 0 =>min  && 10 => MAX
    return Math.ceil((1 - k) * start + k * end) % 256;
  };

  const color = () => {
    let r = interpolate(255, 0);
    let g = interpolate(0, 255);
    let b = interpolate(0, 0);
    return `rgb(${r},${g},${b})`;
  };

  const goCamera = (id) => {
    navigation.navigate('ChallengeCameraScreen', { chId: id }); //adasd[0].id
  };

  const successRate = (cnt) => {
    const temp = (100 * cnt) / totalDay;
    return Math.round(temp);
  };

  useEffect(() => {
    axios
      .get(`${Config.API_URL}/challenge/challengeAuth/${challengeId}`)
      .then(function (response) {
        setAllAuthLists(response.data.data);
        let temp = [];
        for (var i = 0; i < response.data.data.length; i++) {
          if (response.data.data[i].userId === userId) {
            temp.push(response.data.data[i]);
          }
        }
        setMyAuthLists(temp);
      })
      .catch(function (error) {
        console.log(error);
      });

    axios
      .get(`${Config.API_URL}/challenge/rewardInfo/${challengeId}}`)
      .then(function (response) {
        // console.log('ttt', response.data.data.gifticonList);
        setChGifticons(response.data.data.gifticonList);
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
            //             var arr = [];
            // for(var i = 0; i < result + 1; i++) {
            // 		arr.push(i);
            // }
            let temp2 = response2.data.data.sort((a, b) => b.successCnt - a.successCnt);
            // console.log('ttt', response2.data.data);
            // console.log(temp2);
            setUsersInfo(temp2);
          }),
        )
        .catch(function (error) {
          console.log(error);
        })
        .finally(() => setLoading(false));
    }
    myF();
  }, [reload]);

  if (loading) {
    return (
      <View>
        <Text>Loading...</Text>
      </View>
    );
  } else {
    return (
      <View style={{ backgroundColor: '#ffffff', flex: 1 }}>
        <Tab
          value={index}
          onChange={(e) => setIndex(e)}
          indicatorStyle={{
            height: 0,
          }}
          style={{
            borderRadius: 21,
            backgroundColor: '#F6F6F6',
            marginTop: 21,
            marginHorizontal: 10,
            height: 42,
          }}
          variant='primary'
        >
          <Tab.Item
            title='달성률'
            containerStyle={{
              borderRadius: 20,
              backgroundColor: index == 0 ? 'white' : '#F6F6F6',
              margin: 2,
              height: 38,
              padding: 0,
            }}
            // buttonStyle={{}}
            titleStyle={{
              fontSize: 20,
              color: index == 0 ? '#FFA401' : '#686868',
              paddingHorizontal: 0,
              paddingVertical: 0,
              fontWeight: '900',
            }}
          />
          <Tab.Item
            title='인증샷'
            containerStyle={{
              borderRadius: 20,
              backgroundColor: index == 1 ? 'white' : '#F6F6F6',
              margin: 2,
              height: 38,
              padding: 0,
            }}
            // buttonStyle={{}}
            titleStyle={{
              fontSize: 20,
              color: index == 1 ? '#FFA401' : '#686868',
              paddingHorizontal: 0,
              paddingVertical: 0,
            }}
          />
        </Tab>

        <TabView value={index} onChange={setIndex} animationType='spring'>
          <TabView.Item style={{ backgroundColor: 'white', width: '100%' }}>
            <ScrollView>
              <Text>제목:{myChallenge.challengeTitle}</Text>
              <Text>달성률 {successRate(myInfo.successCnt)}% </Text>
              <Text>
                {myChallenge.challengeStartDate}부터 {myChallenge.challengeEndDate}{' '}
              </Text>
              <Text>최대 100%까지 달성 가능해요! 100% 달성으로 상품을 노려보세요!</Text>

              <Icon
                name='flag-checkered'
                type='font-awesome-5'
                size={20}
                // containerStyle={{ bottom: 20, right: 20 }}
              />
              <View style={{ padding: 20, width: '100%', justifyContent: 'center', alignItems: 'stretch' }}>
                <Text style={{ paddingTop: 20 }}>Value: {successRate(myInfo.successCnt)}</Text>
                <Slider
                  value={successRate(myInfo.successCnt)}
                  // onValueChange={setValue}
                  maximumValue={100}
                  minimumValue={0}
                  // step={1}
                  // allowTouchTrack
                  trackStyle={{ height: 5, backgroundColor: 'transparent' }}
                  thumbStyle={{ height: 20, width: 20, backgroundColor: 'transparent' }}
                  thumbProps={{
                    children: (
                      <Icon
                        name='circle'
                        type='font-awesome'
                        size={20}
                        containerStyle={{ bottom: 0, right: 0 }}
                        color={color()}
                      />
                    ),
                  }}
                />
              </View>
              <Text>인증성공 1개| 인증 실패 2개 | 남은 인증 12개</Text>
              <Text>인증샷 기록 나열 or 달력</Text>

              <View>
                <FlatGrid
                  itemDimension={95}
                  data={myAuthLists}
                  style={{}}
                  spacing={5}
                  renderItem={({ item, index }) => (
                    <TouchableOpacity style={{ alignItems: 'center' }}>
                      <FastImage source={{ uri: item.challengePath }} style={{ width: 80, height: 80 }} />
                      {/* <Text style={{ color: 'black', fontFamily: 'Regular' }}>{item.gifticonPeriod}</Text>
                      <Text style={{ color: 'black', fontFamily: 'Regular' }}>{item.gifticonStore}</Text> */}
                    </TouchableOpacity>
                  )}
                />
              </View>

              <Text style={{ marginVertical: 20 }} onPress={() => toggleDialogG()}>
                총상금 {myChallenge.challengeRewardPoint} 원
              </Text>
              <Dialog
                isVisible={visibleDialogG}
                onBackdropPress={toggleDialogG}
                overlayStyle={{ height: 300 }}
              >
                <Dialog.Title title='기프티콘/ 포인트 총상금 관련' />
                <Text>두두두둗</Text>
              </Dialog>

              <Text
                onPress={() => toggleListG()}
                style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}
              >
                기프티콘 등록 목록
              </Text>

              <Dialog isVisible={visibleListG} onBackdropPress={toggleListG}>
                <Dialog.Title title='챌린지 등록된 기프티콘 목록 ' />

                <FlatGrid
                  itemDimension={95}
                  data={chGifticons.filter((gifticon) => !gifticon.gifticonUsed)}
                  style={{}}
                  spacing={5}
                  renderItem={({ item, index }) => (
                    <TouchableOpacity style={{ alignItems: 'center' }}>
                      {/* <FastImage source={{ uri: item.gifticonPath }} style={{ width: 80, height: 80 }} /> */}
                      <Text style={{ color: 'black', fontFamily: 'Regular' }}>{item.gifticonPeriod}</Text>
                      <Text style={{ color: 'black', fontFamily: 'Regular' }}>{item.gifticonStore}</Text>
                    </TouchableOpacity>
                  )}
                />
              </Dialog>

              <Text style={{ marginVertical: 20 }} onPress={() => toggleDialogF()}>
                {myInfo.myRank} 위 나의 순위 / 모달 랭킹
              </Text>
              <Dialog
                isVisible={visibleDialogF}
                onBackdropPress={toggleDialogF}
                overlayStyle={{ height: 500 }}
              >
                <Dialog.Title title='랭킹 화면 ' />
                <View style={{ flexDirection: 'row' }}>
                  {usersInfo.map((user, index) => (
                    <View style={{ flexDirection: 'column' }}>
                      {index <= 2 && (
                        <View
                          style={
                            index === 0
                              ? { position: 'absolute', top: -50, right: -150, zIndex: 9 }
                              : index === 1
                              ? { position: 'absolute', top: -30, right: -50, zIndex: 9 }
                              : { position: 'absolute', top: -10, right: -250, zIndex: 9 }
                          }
                        >
                          <Image
                            source={
                              user.userProfile
                                ? {
                                    uri: user.userProfile,
                                  }
                                : require('../../assets/images/homeMochi.png')
                            }
                            resizeMode='contain'
                            style={{ width: 50, height: 50, borderRadius: 100 }}
                          />
                          <Text>{user.userNickname}</Text>
                          <Text>--{successRate(user.successCnt)}%--</Text>
                        </View>
                      )}
                    </View>
                  ))}
                </View>
                <Image
                  source={require('../../assets/images/leader.png')}
                  resizeMode='contain'
                  style={{ width: 270, height: 270, marginVertical: -50 }}
                />
                <ScrollView style={{ backgroundColor: '#F6F6', marginHorizontal: 10, borderRadius: 20 }}>
                  {usersInfo.map((user, index) => (
                    <>
                      {index > 2 && (
                        <>
                          <Image
                            source={
                              user.userProfile
                                ? {
                                    uri: user.userProfile,
                                  }
                                : require('../../assets/images/homeMochi.png')
                            }
                            resizeMode='contain'
                            style={{ width: 40, height: 40, borderRadius: 100 }}
                          />
                          <Text>{user.userNickname}</Text>
                          <Text>--{successRate(user.successCnt)}%--</Text>
                        </>
                      )}
                    </>
                  ))}
                </ScrollView>
              </Dialog>
            </ScrollView>
          </TabView.Item>
          <TabView.Item style={{ backgroundColor: 'white', width: '100%' }}>
            <ScrollView>
              <Icon
                name='camera'
                type='font-awesome'
                size={20}
                // containerStyle={{ bottom: 20, right: 20 }}
              />
              <Text onPress={() => goCamera(challengeId)}>인증하기</Text>
              <Icon name='question' type='octicon' size={20} color='black' onPress={() => toggleDialogQ()} />

              <Dialog isVisible={visibleDialogQ} onBackdropPress={toggleDialogQ}>
                <Dialog.Title title='인증샷 설명' />
                <Text>두두두둗</Text>
              </Dialog>

              <Text>전체 유저 인증샷 목록</Text>
              <View>
                <FlatGrid
                  itemDimension={95}
                  data={allAuthLists}
                  style={{}}
                  spacing={5}
                  renderItem={({ item, index }) => (
                    <TouchableOpacity style={{ alignItems: 'center' }}>
                      <FastImage source={{ uri: item.challengePath }} style={{ width: 80, height: 80 }} />
                      {/* <Text style={{ color: 'black', fontFamily: 'Regular' }}>{item.gifticonPeriod}</Text>
                      <Text style={{ color: 'black', fontFamily: 'Regular' }}>{item.gifticonStore}</Text> */}
                    </TouchableOpacity>
                  )}
                />
              </View>
            </ScrollView>
          </TabView.Item>
        </TabView>
      </View>
    );
  }
}

export default ChallengeDetailScreen1;
