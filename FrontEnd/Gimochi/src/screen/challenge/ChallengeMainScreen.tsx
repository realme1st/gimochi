import React, { useState, useEffect } from 'react';
import { Text, ScrollView, View, TouchableOpacity, StyleSheet } from 'react-native';
import { Tab, Icon, TabView, ThemeProvider, createTheme } from '@rneui/themed';
import { useAppDispatch } from '../../store';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';
import reloadSlice from '../../slices/reload';
import screenSlice from '../../slices/screen';

function ChallengeMainScreen({ navigation, route }) {
  const userId = useSelector((state: RootState) => state.user.userId);
  const reload = useSelector((state: RootState) => state.reload.reload);
  const dispatch = useAppDispatch();
  const [index, setIndex] = useState(0);
  const [loading, setLoading] = useState(1);

  const [myChList, setmyChList] = useState([]);
  const [myChListC, setmyChListC] = useState([]);

  var temp = [];
  const [my0ChList, setmy0ChList] = useState([]);
  const [my1ChList, setmy1ChList] = useState([]);
  const [my2ChList, setmy2ChList] = useState([]);
  console.log('JMJ1');
  //  console.log('M');
  // const myList = route.params.myList ? route.params.myList : [];
  // console.log(myList);
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

  // const create012List = (chall) => {
  //         // response.data.data.forEach((chall) => {
  //         if (chall.challengeActive === 0) {
  //           my0ChList.push(chall.challengeActive);
  //         } else if (chall.challengeActive === 1) {
  //           my1ChList.push(chall.challengeActive);
  //         } else if (chall.challengeActive === 2) {
  //           my2ChList.push(chall.challengeActive);
  //         }
  //         console.log(myChList, my0ChList, my1ChList, my2ChList);
  //       };

  //   let pensByColors = pens.sort((a,b) => (a.price - b.price));
  // console.log(pensByColors);

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
    console.log('e1');
    async function myList() {
      const response = await axios.get(`${Config.API_URL}/challenge/challengeList/` + userId);
      console.log(response.data.data);
      setmyChList(response.data.data);
    }
    myList();
  }, [reload]);

  useEffect(() => {
    console.log('e2');
    console.log(myChList);

    async function myList2() {
      myChList.forEach((chId, i) => {
        // console.log(chId, i);
        axios
          .all([
            axios.get(`${Config.API_URL}/challenge/challengeList/` + userId),
            axios.get(`${Config.API_URL}/challenge/challengeInfo/rank/${chId.challengeId}/${userId}`),
          ])
          .then(
            axios.spread((response1, response2) => {
              console.log('T');
              console.log(response1.data.data);
              console.log(response1.data.data[i]);
              console.log(chId.challengeId, response2.data.data);
              var temp = { ...response1.data.data[i], ...response2.data.data };
              console.log(temp);
              myChListC.push(temp);
            }),
          )
          .catch(function (error) {
            console.log(error);
          });
      });
      setLoading(false);
    }
    // try {
    myList2();
    // if (confirm('에러를 만드시겠습니까?')) 이상한_코드();
    // } catch (error) {
    //   console.log(error);
    // } finally {
    //   console.log('load');
    // setLoading(false);
    // }

    console.log('a');
    console.log(myChListC);
  }, [myChList]);
  console.log('aa');
  console.log(myChListC);

  useEffect(() => {
    console.log('e3');
    // myChListC.push(temp);
    console.log(myChListC);
  }, [temp]);

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
              <TouchableOpacity
                style={{ marginVertical: 10, backgroundColor: 'red' }}
                onPress={() => goDetail0(1)}
              >
                <Text>챌린지 대기중 상세보기</Text>
              </TouchableOpacity>
              <TouchableOpacity
                style={{ marginVertical: 10, backgroundColor: 'green' }}
                onPress={() => goDetail1(1)}
              >
                <Text>챌린지 진행중 상세보기</Text>
              </TouchableOpacity>
              <TouchableOpacity
                style={{ marginVertical: 10, backgroundColor: 'blue' }}
                onPress={() => goDetail2(1)}
              >
                <Text>챌린지 종료된 상세보기</Text>
              </TouchableOpacity>

              <Text>대기중 진행중 여기서 표시 삭제는 각 챌린지 들어가서</Text>

              {/* <Text>{myChList ? myChList : ''} </Text> */}
              {/* <Text>{myChListC ? myChListC : ''} </Text> */}
              {/* <Text>{myChList[0] ? myChList[0] : ''} </Text> */}
              {/* <Text>{myChList[0].challengeId ? myChList[0].challengeId : ''} </Text> */}
              {/* <Text>{myChListC ? myChListC[0].challengeId : ''} 시</Text> */}
              <Text>챌린지 진행중</Text>
              <Text>챌린지 대기중</Text>
              <Text>챌린지 대기중</Text>
            </ScrollView>
          </TabView.Item>
          <TabView.Item style={{ backgroundColor: 'white', width: '100%' }}>
            <ScrollView>
              <Text>종료된 목록들 표시 삭제는 각 챌린지 들어가서 </Text>
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
