import React, { useState } from 'react';
import { ScrollView, View, TouchableOpacity, StyleSheet } from 'react-native';
import { Tab, Text, TabView, ThemeProvider, createTheme } from '@rneui/themed';
// import styled from 'styled-components/native';
// import { ThemeProvider, createTheme } from '@rneui/themed';
import SpeedDial from './SpeedDial';
import { Divider } from '@rneui/themed';
const theme = createTheme({
  components: {
    Button: {
      titleStyle: {
        // color: 'red',
        // height: 8,
      },
    },
  },
});

function ChallengeMainScreen({ navigation }) {
  const [index, setIndex] = useState(0);
  const goDetail = (id) => {
    navigation.navigate('ChallengeDetailScreen', { challengeId: id });
  };

  const goWrite = () => {
    navigation.navigate('ChallengeCreateScreen');
  };

  console.log(index);

  return (
    <>
      <Text>챌린지 진행 / 완료 조건부 랜더링 | 챌린지 목록들 보여줄 예정입니다.</Text>
      <TouchableOpacity onPress={() => goDetail(12)}>
        <Text>추카포카 상세보기 버튼</Text>
      </TouchableOpacity>
      <TouchableOpacity onPress={goWrite}>
        <Text>추카포카 생성하기 버튼</Text>
      </TouchableOpacity>
      <ThemeProvider theme={theme}>
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
      </ThemeProvider>
      <TabView value={index} onChange={setIndex} animationType='spring'>
        <TabView.Item style={{ backgroundColor: 'white', width: '100%' }}>
          <ScrollView>
            <Text h1>진행중 목록</Text>
            <Text h1>진행중 목록</Text>
            <Text h1>진행중 목록</Text>
            <Text h1>진행중 목록</Text>
            <Text h1>진행중 목록</Text>
            <Text h1>진행중 목록</Text>
            <Text h1>진행중 목록</Text>
            <Text h1>진행중 목록</Text>
            <Text h1>진행중 목록</Text>
          </ScrollView>
        </TabView.Item>
        <TabView.Item style={{ backgroundColor: 'white', width: '100%' }}>
          <ScrollView>
            <Text h1>종료된 목록</Text>
            <Text h1>Favorite</Text>
          </ScrollView>
        </TabView.Item>
      </TabView>
      <SpeedDial></SpeedDial>
    </>
  );
}

export default ChallengeMainScreen;
