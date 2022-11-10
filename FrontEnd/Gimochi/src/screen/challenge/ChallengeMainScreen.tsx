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

function ChallengeMainScreen({ navigation }) {
  const userId = useSelector((state: RootState) => state.user.userId);
  const reload = useSelector((state: RootState) => state.reload.reload);
  const dispatch = useAppDispatch();
  const [index, setIndex] = useState(0);
  console.log(index);
  const goDetail = (id) => {
    navigation.navigate('ChallengeDetailScreen', { challengeId: id });
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
    axios
      .get(`${Config.API_URL}/challenge/challengeList/` + userId)
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [reload]);
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
            <TouchableOpacity onPress={() => goDetail(1)}>
              <Text>챌린지 상세보기 버튼</Text>
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
        containerStyle={{ position: 'absolute', top: 480, left: 330 }}
      />
    </View>
  );
}

export default ChallengeMainScreen;
