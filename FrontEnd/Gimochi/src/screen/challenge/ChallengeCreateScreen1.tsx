import React, { useState, useEffect } from 'react';
import { Text, ScrollView, View, TouchableOpacity, StyleSheet } from 'react-native';
import { Tab, TabView, ThemeProvider, createTheme, SpeedDial } from '@rneui/themed';
import { Icon } from '@rneui/themed';

function ChallengeCreateScreen1({ navigation }) {
  const [index, setIndex] = useState(0);
  console.log(index);

  const goWrite2 = (index2) => {
    // index 0 :포인트  1 :기프티콘
    navigation.navigate('ChallengeCreateScreen2', { indexOfGP: index2 + 1 });
  };

  return (
    <View style={{ backgroundColor: '#ffffff', flex: 1 }}>
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
            <Text>포인트 도움말 ㄴㅇㄴㅇㄴㅇㄴㅇㄴㅇㄴㅇㄴ ~~~</Text>
            <Text>1</Text>
            <Text>2</Text>
            <Text>3</Text>
            <Text>4</Text>
          </ScrollView>
        </TabView.Item>
        <TabView.Item style={{ backgroundColor: 'white', width: '100%' }}>
          <ScrollView style={{ backgroundColor: '#F6F6', marginHorizontal: 10, borderRadius: 20 }}>
            <Text>기프티콘 도움말</Text>
            <Text>1</Text>
            <Text>2</Text>
            <Text>3</Text>
            <Text>4</Text>
          </ScrollView>
        </TabView.Item>
      </TabView>

      <Icon
        name='navigate-next'
        type='material'
        color='#FFE7BC'
        size={25}
        reverse
        reverseColor='#FFA401'
        onPress={() => goWrite2(index)}
        iconStyle={{ fontSize: 43 }}
        containerStyle={{ position: 'absolute', top: 480, left: 330 }}
      />
    </View>
  );
}

export default ChallengeCreateScreen1;
