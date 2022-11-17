import React, { useState, useEffect } from 'react';
import { Text, ScrollView, View, TouchableOpacity, StyleSheet } from 'react-native';
import { Tab, Icon, TabView, ThemeProvider, createTheme } from '@rneui/themed';
import { useAppDispatch } from '../../store';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';

import screenSlice from '../../slices/screen';

function ChallengeMainScreen({ navigation, route }) {
  const userId = useSelector((state: RootState) => state.user.userId);
  const reload = useSelector((state: RootState) => state.reload.reload);
  const dispatch = useAppDispatch();
  const [index, setIndex] = useState(0);
  const [loading, setLoading] = useState(true);

  const [myChList, setmyChList] = useState([]);
  const [my0ChList, setmy0ChList] = useState([]);
  const [my1ChList, setmy1ChList] = useState([]);
  const [my2ChList, setmy2ChList] = useState([]);

  const goDetail0 = (id) => {
    navigation.navigate('ChallengeDetailScreen0', { challengeId: id });
  };
  const goDetail1 = (id) => {
    navigation.navigate('ChallengeDetailScreen1', { challengeId: id });
  };
  const goDetail2 = (id) => {
    navigation.navigate('ChallengeDetailScreen2', { challengeId: id });
  };

  const goWrite1 = () => {
    navigation.navigate('ChallengeCreateScreen1');
  };

  const create012List = (res) => {
    res.forEach((chall, i) => {
      if (chall.challengeActive === 0) {
        my0ChList.push(chall);
      } else if (chall.challengeActive === 1) {
        my1ChList.push(chall);
      } else if (chall.challengeActive === 2) {
        my2ChList.push(chall);
      }
      console.log(myChList, my0ChList, my1ChList, my2ChList);
    });
  };
  const calSDay = (time) => {
    const t = new Date(time) - new Date() - 32400000;
    const result = parseInt(t / 86400000);
    return result === 0 ? '' : result + 1;
  };
  const calEDay = (time) => {
    const t = new Date(time) - new Date() - 32400000;
    const result = parseInt(t / 86400000);
    return result === 0 ? '' : result + 1;
  };
  useEffect(() => {
    dispatch(
      screenSlice.actions.addScreen({
        screen: 'ChallengeScreen',
      }),
    );
    return () => {
      console.log('unmount');
      dispatch(screenSlice.actions.deleteScreen());
    };
  }, []);

  useEffect(() => {
    axios
      .get(`${Config.API_URL}/challenge/challengeList/` + userId)
      .then(function (response) {
        console.log(response.data.data);
        // myChList.push(response.data.data);
        setmyChList(response.data.data);
        create012List(response.data.data);
        // const temp = { ...response1.data.data[i], ...response2.data.data };
        my0ChList.sort((a, b) => new Date(a.challengeStartDate) - new Date(b.challengeStartDate));
        my1ChList.sort((a, b) => new Date(a.challengeEndDate) - new Date(b.challengeEndDate));
        my2ChList.sort((a, b) => new Date(b.challengeEndDate) - new Date(a.challengeEndDate));
        // console.log(myChList, my0ChList, my1ChList, my2ChList);
      })
      .catch(function (error) {
        console.log(error);
      })
      .finally(() => setLoading(false));
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
          // containerStyle={{
          //   borderRadius: 21,
          //   backgroundColor: 'red',
          //   marginTop: 21,
          //   height: 42,
          // }}
          // buttonStyle={{
          //   borderRadius: 21,
          //   backgroundColor: 'white',
          //   marginTop: 21,
          //   height: 42,
          // }}
          // titleStyle={{
          //   margin: 2,
          //   height: 38,
          //   borderRadius: 21,
          //   color: 'red',
          // }}
          variant='primary'
        >
          <Tab.Item
            title='진행중인 챌린지'
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
            title='종료된 챌린지'
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
              <Text>---진행중(맨위가 종료임박)---</Text>
              {my1ChList.map((Ch, index) => (
                <TouchableOpacity key={index + 10000} onPress={() => goDetail1(Ch.challengeId)}>
                  <Text>{Ch.challengeRewardType === 1 ? '포인트' : '기프티콘'}</Text>
                  <Text>제목:{Ch.challengeTitle}</Text>
                  <Text>끝나기까지 D-{calEDay(Ch.challengeEndDate)}day</Text>
                  <Text>게시자:{Ch.challengeLeaderName}</Text>
                  <Text>참여자수:{Ch.challenger_cnt}명</Text>
                  <Text>현재 1위{Ch.winnerName}</Text>
                  {/* <Text>1위 없음</Text> */}
                  <Text>------------------------</Text>
                </TouchableOpacity>
              ))}

              <Text>---대기중(맨위가 시작임박)---</Text>
              {my0ChList.map((Ch, index) => (
                <TouchableOpacity key={index + 100} onPress={() => goDetail0(Ch.challengeId)}>
                  <Text>{Ch.challengeRewardType === 1 ? '포인트' : '기프티콘'}</Text>
                  <Text>제목:{Ch.challengeTitle}</Text>
                  <Text>시작하기까지 D-{calSDay(Ch.challengeStartDate)}day</Text>
                  <Text>게시자:{Ch.challengeLeaderName}</Text>
                  <Text>참여자수:{Ch.challenger_cnt}명</Text>
                  {/* <Text>1위:{Ch.winnerName}</Text> */}
                  <Text>순위 없음</Text>
                  <Text>------------------------</Text>
                </TouchableOpacity>
              ))}
            </ScrollView>
          </TabView.Item>
          <TabView.Item style={{ backgroundColor: 'white', width: '100%' }}>
            <ScrollView>
              <Text>---종료됨(맨위가 최신종료)---</Text>
              {my2ChList.map((Ch, index) => (
                <TouchableOpacity key={index + 1000} onPress={() => goDetail2(Ch.challengeId)}>
                  <Text>{Ch.challengeRewardType === 1 ? '포인트' : '기프티콘'}</Text>
                  <Text>제목:{Ch.challengeTitle}</Text>
                  <Text>종료</Text>
                  {/* <Text>D-{calEDay(Ch.challengeEndDate)}day</Text> */}
                  <Text>게시자:{Ch.challengeLeaderName}</Text>
                  <Text>참여자수:{Ch.challenger_cnt}명</Text>
                  <Text>우승자:{Ch.winnerName}</Text>
                  {/* <Text>1위 없음</Text> */}
                  <Text>------------------------</Text>
                </TouchableOpacity>
              ))}
            </ScrollView>
          </TabView.Item>
        </TabView>

        <Icon
          name='add'
          type='material'
          color='#FFE7BC'
          size={25}
          reverse
          reverseColor='#FFA401'
          onPress={() => goWrite1()}
          iconStyle={{ fontSize: 43 }}
          containerStyle={{ position: 'absolute', top: '85%', left: '80%' }}
        />
      </View>
    );
  }
}

export default ChallengeMainScreen;
