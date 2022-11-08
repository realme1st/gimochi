import React, { useState, useEffect } from 'react';
import { ScrollView, View, TouchableOpacity, StyleSheet } from 'react-native';
import { Tab, Text, TabView, ThemeProvider, createTheme } from '@rneui/themed';

function ChallengeCreateScreen({ navigation }) {
  const [index, setIndex] = useState(0);
  console.log(index);

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
          title='포인트'
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
          title='기프티콘'
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
      <Text
        style={{
          backgroundColor: 'yellow',
          textAlign: 'center',
          marginVertical: 5,
        }}
      >
        도움말
      </Text>
      <View
        style={{
          width: 0,
          height: 0,
          // backgroundColor: 'transparent',
          borderStyle: 'solid',
          borderLeftWidth: 20,
          borderRightWidth: 20,
          borderBottomWidth: 30,
          // borderTopWidth: 1,
          borderLeftColor: 'transparent',
          borderRightColor: 'transparent',
          borderBottomColor: '#F6F6',
          position: 'absolute',
          top: 65,
          left: 88,
          display: index == 0 ? 'flex' : 'none',
        }}
      ></View>
      <View
        style={{
          width: 0,
          height: 0,
          // backgroundColor: 'transparent',
          borderStyle: 'solid',
          borderLeftWidth: 20,
          borderRightWidth: 20,
          borderBottomWidth: 30,
          // borderTopWidth: 1,
          borderLeftColor: 'transparent',
          borderRightColor: 'transparent',
          borderBottomColor: '#F6F6',
          position: 'absolute',
          top: 65,
          right: 88,
          display: index == 1 ? 'flex' : 'none',
        }}
      ></View>
      <TabView value={index} onChange={setIndex} animationType='spring'>
        <TabView.Item style={{ backgroundColor: 'white', width: '100%' }}>
          <ScrollView style={{ backgroundColor: '#F6F6', marginHorizontal: 10, borderRadius: 20 }}>
            <Text h1>포인트 도움말 스크린 왼쪽 끝 왜 짤려 어떻게 바꿔~~ㄴㅇㄴㅇㄴㅇㄴㅇㄴㅇㄴㅇㄴ ~~~</Text>
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
          <ScrollView style={{ backgroundColor: '#F6F6', marginHorizontal: 10, borderRadius: 20 }}>
            <Text h1>종료된 목록</Text>
            <Text h1>Favorite</Text>
          </ScrollView>
        </TabView.Item>
      </TabView>
    </View>
  );
}

export default ChallengeCreateScreen;
