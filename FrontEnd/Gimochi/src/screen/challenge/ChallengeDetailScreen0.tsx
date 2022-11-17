import React, { useState, useEffect, useRef } from 'react';
import { Text, ScrollView, Image, View, TouchableOpacity, StyleSheet } from 'react-native';
import {
  BottomSheet,
  Button,
  ListItem,
  Input,
  Tab,
  TabView,
  ThemeProvider,
  createTheme,
  Slider,
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

function ChallengeDetailScreen0({ route, navigation }) {
  const [time, setTime] = useState(new Date());
  const [caltime, setCalTime] = useState();
  const [visibleDialogF, setVisibleDialogF] = useState(false);
  const [index, setIndex] = useState(0);
  const [loading, setLoading] = useState(true);
  const [myChallenge, setMyChallenge] = useState([]); // 챌린지 정보
  const [usersInfo, setUsersInfo] = useState([]); // 챌린지 참여 목록
  const [usersInfoL, setUsersInfoL] = useState([]); // 챌린지 참여 종합목록
  const [inviteUsers, setInviteUsers] = useState([]); // 챌린지 초대장 보낸 유저목록
  // const [loadingF, setLoadingF] = useState(true);
  const [myfollower, setMyfollower] = useState([]); // 친구목록
  const userId = useSelector((state: RootState) => state.user.userId);
  const reload = useSelector((state: RootState) => state.reload.reload);
  const dispatch = useAppDispatch();
  // const challengeId = route.params.challengeId;
  const challengeId = 16;
  const toggleDialogF = () => {
    setVisibleDialogF(!visibleDialogF);
  };

  const interval = useRef(null);
  useEffect(() => {
    console.log('1초');
    interval.current = setInterval(() => {
      // console.log(new Date());
      // console.log(SSTT - new Date() - 32400000);
      setTime(new Date());
      setCalTime(SSTT - new Date() - 32400000);
    }, 100000);
    // 타이머 컴포넌트가 언마운트 될 때 실행
    return () => clearInterval(interval.current);
  });
  // Start End Time 테스트용  !! 한국 표준시 9시간 빼줘야함 -32400000
  // 86400000 24시간 3600000 1시간  60000 1분  1000 1초
  const SSTT = new Date('2022-11-17'); //useState로 만들기 .challengeStartDate
  // const EETT = new Date('2022-11-09');

  const dd = parseInt(caltime / 86400000);
  const d = parseInt(caltime / 86400000) + 1;
  var tt = caltime - dd * 86400000;
  const h = parseInt(tt / 3600000);
  var tt = caltime - dd * 86400000 - h * 3600000;
  const m = parseInt(tt / 60000);
  var tt = caltime - dd * 86400000 - h * 3600000 - m * 60000;
  const t = parseInt(tt / 1000);
  console.log(dd, h, m, t);

  const goMain = () => {
    navigation.navigate('ChallengeMainScreen');
  };

  const myFollow = async () => {
    await axios
      .get(`${Config.API_URL}/user/follower/` + userId)
      .then(function (response) {
        console.log(response.data.data);
        setMyfollower(response.data.data);
      })

      .catch(function (error) {
        console.log(error);
      })
      .finally(() => toggleDialogF());
  };

  const doInvite = async (id) => {
    const jsonData = {
      challengeId: challengeId,
      userId: id,
    };
    console.log(jsonData);
    await axios
      .post(`${Config.API_URL}/challenge/challengeInvite`, jsonData)
      .then(function (response) {
        console.log(response);
        // 실시간 상태관리용, 무지성 복붙할것
        dispatch(
          reloadSlice.actions.setReload({
            reload: String(new Date()),
          }),
        );
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  useEffect(() => {
    axios
      .get(`${Config.API_URL}/challenge/challengeInvite/userList/` + challengeId)
      .then(function (response) {
        console.log(response.data.data);
        setInviteUsers(response.data.data);
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
            console.log(response1.data.data);
            setMyChallenge(response1.data.data);
            console.log(response2.data.data);
            setUsersInfo(response2.data.data);
            let test = [];
            for (var i = 0; i < response2.data.data.length; i++) {
              await axios
                .get(`${Config.API_URL}/user/info/` + response2.data.data[i].userId)
                .then(function (response) {
                  console.log(response.data.data);
                  const temp = { ...response.data.data, ...response2.data.data[i] };
                  console.log(temp);
                  test.push(temp);
                  // console.log(usersInfoL);
                })
                .catch(function (error) {
                  console.log(error);
                });
            }
            setUsersInfoL(test);
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
      <View style={{ backgroundColor: '#fff', flex: 1 }}>
        <ScrollView>
          <View
            style={{
              flexDirection: 'row',
              height: 80,
              padding: 20,
              marginRight: 60,
            }}
          >
            <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>
              제목{'  '}:{myChallenge.challengeTitle}
            </Text>
          </View>
          <View
            style={{
              flexDirection: 'row',
              height: 130,
              padding: 20,
              marginRight: 60,
            }}
          >
            <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>
              기간{'  '}: {myChallenge.challengeStartDate}부터{myChallenge.challengeEndDate}까지 매일
            </Text>
          </View>
          <View
            style={{
              flexDirection: 'row',
              height: 80,
              padding: 20,
              marginRight: 60,
            }}
          >
            <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>
              설명{'  '}:{myChallenge.challengeDescription}
            </Text>
          </View>
          <View
            style={{
              flexDirection: 'row',
              height: 80,
              padding: 20,
              marginRight: 60,
            }}
          >
            <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }} onPress={() => myFollow()}>
              참가 인원 {usersInfo.length}명 | 친구초대하기
            </Text>
            <Dialog isVisible={visibleDialogF} onBackdropPress={toggleDialogF} overlayStyle={{ height: 300 }}>
              <Tab
                // index 0 :포인트  1 :기프티콘
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
                  title='참가 현황'
                  containerStyle={{
                    borderRadius: 20,
                    backgroundColor: index == 0 ? 'white' : '#F6F6F6',
                    margin: 2,
                    height: 38,
                    padding: 0,
                  }}
                  titleStyle={{
                    fontSize: 20,
                    color: index == 0 ? '#FFA401' : '#686868',
                    paddingHorizontal: 0,
                    paddingVertical: 0,
                    fontWeight: '900',
                  }}
                />
                <Tab.Item
                  title='친구 목록'
                  containerStyle={{
                    borderRadius: 20,
                    backgroundColor: index == 1 ? 'white' : '#F6F6F6',
                    margin: 2,
                    height: 38,
                    padding: 0,
                  }}
                  titleStyle={{
                    fontSize: 20,
                    color: index == 1 ? '#FFA401' : '#686868',
                    paddingHorizontal: 0,
                    paddingVertical: 0,
                  }}
                />
              </Tab>

              <TabView value={index} onChange={setIndex} animationType='spring'>
                {index == 0 ? (
                  <TabView.Item style={{ backgroundColor: 'red', width: '100%', height: 200 }}>
                    <ScrollView style={{ backgroundColor: '#F6F6', marginHorizontal: 10, borderRadius: 20 }}>
                      {usersInfoL.map((user, index) => (
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
                          <Text>참가 완료</Text>
                        </>
                      ))}
                    </ScrollView>
                  </TabView.Item>
                ) : (
                  <TabView.Item></TabView.Item>
                )}
                {index == 1 ? (
                  <TabView.Item style={{ backgroundColor: 'blue', width: '100%', height: 200 }}>
                    <ScrollView style={{ backgroundColor: '#F6F6', marginHorizontal: 10, borderRadius: 20 }}>
                      {myfollower.map((myF, index) => (
                        <>
                          {myF.friend === true && (
                            <>
                              <Text>친구:{myF.userName}</Text>
                              {/* <Text onPress={() => doInvite(myF.userId)}>초대하기</Text> */}
                              {/* <Text>취소하기</Text> */}

                              <Icon
                                name='close'
                                type='fontisto'
                                color='#FFE7BC'
                                size={25}
                                iconStyle={{ fontSize: 33 }}
                              />
                            </>
                          )}
                        </>
                      ))}
                    </ScrollView>
                  </TabView.Item>
                ) : (
                  <TabView.Item></TabView.Item>
                )}
              </TabView>
            </Dialog>
          </View>
          <View
            style={{
              flexDirection: 'row',
              height: 80,
              padding: 20,
              marginRight: 60,
            }}
          >
            <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>
              누적 상금확인{myChallenge.challengeRewardPoint} 클릭 설명
            </Text>
          </View>
          <View
            style={{
              flexDirection: 'row',
              height: 80,
              padding: 20,
              marginRight: 60,
            }}
          >
            <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>
              기프티콘 클릭 확인(모달)
            </Text>
          </View>
          <View
            style={{
              flexDirection: 'row',
              height: 80,
              padding: 20,
              marginRight: 60,
            }}
          >
            <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>
              인증 방법 (모달고정내용 )
            </Text>
          </View>
          <View
            style={{
              flexDirection: 'row',
              height: 80,
              padding: 20,
              marginRight: 10,
              marginBottom: 60,
            }}
          >
            <Text style={{ fontSize: 25, marginTop: 5, fontFamily: 'Regular' }}>
              {d} {h} {m} {t} @@@
              {d > 0 ? (
                <Text>{d ? d : ''}일 후 자동시작</Text>
              ) : h > 0 ? (
                <Text>{h ? h : ''}시간 후 자동시작</Text>
              ) : m > 0 ? (
                <Text>{m ? m : ''}분 후 자동시작</Text>
              ) : t > 0 ? (
                <Text>{t ? t : ''}초 후 자동시작</Text>
              ) : (
                <Text onPress={goMain}>바로 시작 됩니다. 메인으로 가기</Text>
              )}{' '}
            </Text>
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
}

export default ChallengeDetailScreen0;
