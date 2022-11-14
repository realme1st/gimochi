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
  const [myChIdList, setmyChIdList] = useState([]);
  const [my0ChIdList, setmy0ChIdList] = useState([]);
  const [my1ChIdList, setmy1ChIdList] = useState([]);
  const [my2ChIdList, setmy2ChIdList] = useState([]);
  console.log(index);
  console.log('JMJ1');
  // console.log('M');
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
  const goCamera = () => {
    navigation.navigate('ChallengeCameraScreen');
  };

  const goWrite1 = () => {
    navigation.navigate('ChallengeCreateScreen1');
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
    console.log('JMJ2');
    // let myChIdList = [16, 19, 20, 21, 22, 23, 24];
    async function myList() {
      const response = await axios.get(`${Config.API_URL}/challenge/challengeList/` + userId);
      // console.log(response.data.data);
      setmyChIdList(response.data.data);
      // response.data.data.forEach((chall) => {
      //   // console.log(chall.challengeId);
      //   myChIdList.push(chall.challengeId);
      // });
      // console.log(myChIdList);
    }
    myList();
    // console.log(myChIdList);
  }, [reload]);

  useEffect(() => {
    // console.log(myChIdList);
    myChIdList.forEach((chId, i) => {
      // console.log(chId);
      // console.log(chId.challengeId);
      axios
        .all([
          axios.get(`${Config.API_URL}/challenge/challengeList/` + userId),
          axios.get(`${Config.API_URL}/challenge/` + chId.challengeId),
        ])
        .then(
          axios.spread((response1, response2) => {
            console.log('L');
            // console.log(response1.data.data);
            // console.log(response1.data.data[i]);0
            // console.log(response2.data.data);
            const temp = { ...response1.data.data[i], ...response2.data.data };
            console.log(temp);
            // setmy0ChIdList(temp);
            my0ChIdList.push(temp);
            console.log(my0ChIdList);
          }),
        )
        .catch(function (error) {
          console.log(error);
        });
    });

    // async function myList() {
    //   await axios
    //     .get(`${Config.API_URL}/challenge/challengeList/` + userId)
    //     .then(function (response) {
    //       console.log('ttt');
    //       console.log(response.data.data);
    //       console.log(response.data.data[0]);
    //       setmyChIdList(response.data.data);
    //       TT = response.data.data[0];
    //       // console.log(myChIdList);
    //     })
    //     .catch(function (error) {
    //       console.log(error);
    //     });
    // }
    // myList();
    // await axios
    // .get(`${Config.API_URL}/challenge/` + 16)
    // .then(function (response) {
    //   console.log('ttt2');
    //   console.log(response.data.data);
    //   console.log(myChIdList[0]);
    //   const temp = { ...TT, ...response.data.data };
    //   const temp2 = { ...{ a: 1, b: 2 }, ...response.data.data };

    //   console.log(temp);
    //   console.log(temp2);
    //   setmy0ChIdList(temp);
    // })
    // .catch(function (error) {
    //   console.log(error);
    // });
  }, [myChIdList]);
  // console.log(tempList);
  // setmy0ChIdList(tempList);
  // console.log(my0ChIdList);

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
            <TouchableOpacity
              style={{ marginVertical: 10, backgroundColor: 'yellow' }}
              onPress={() => goCamera()}
            >
              <Text>챌린지 카메라</Text>
            </TouchableOpacity>
            <Text>대기중 진행중 여기서 표시 삭제는 각 챌린지 들어가서</Text>
            <Text>챌린지 진행중</Text>
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

export default ChallengeMainScreen;
