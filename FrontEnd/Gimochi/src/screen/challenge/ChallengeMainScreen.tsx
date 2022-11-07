import React, { useState } from 'react';
import { ScrollView, View, TouchableOpacity } from 'react-native';
import { Tab, Text, TabView } from '@rneui/themed';

function ChallengeMainScreen({ navigation }) {
  const [index, setIndex] = useState(0);
  const goDetail = (id) => {
    navigation.navigate('ChallengeDetailScreen', { challengeId: id });
  };

  const goWrite = () => {
    navigation.navigate('ChallengeCreateScreen');
  };

  return (
    <>
      {/* <ScrollView> */}
      <Text>챌린지 진행 / 완료 조건부 랜더링 | 챌린지 목록들 보여줄 예정입니다.</Text>
      <TouchableOpacity onPress={() => goDetail(12)}>
        <Text>추카포카 상세보기 버튼</Text>
      </TouchableOpacity>
      <TouchableOpacity onPress={goWrite}>
        <Text>추카포카 생성하기 버튼</Text>
      </TouchableOpacity>
      <Tab
        value={index}
        onChange={(e) => setIndex(e)}
        indicatorStyle={{
          //   backgroundColor: '#FFA401',
          backgroundColor: 'white',
          height: 5,
        }}
        variant='primary'
      >
        <Tab.Item
          title='진행중인 챌린지'
          titleStyle={{ fontSize: 20 }}
          icon={{ name: 'timer', type: 'ionicon', color: 'white' }}
        />
        <Tab.Item
          title='종료된 챌린지'
          titleStyle={{ fontSize: 20 }}
          icon={{ name: 'heart', type: 'ionicon', color: 'white' }}
        />
      </Tab>
      <TabView value={index} onChange={setIndex} animationType='spring'>
        <TabView.Item style={{ backgroundColor: 'red', width: '100%' }}>
          <Text h1>Recent</Text> <Text h1>Recent</Text> <Text h1>Recent</Text> <Text h1>Recent</Text>{' '}
          <Text h1>Recent</Text> <Text h1>Recent</Text> <Text h1>Recent</Text> <Text h1>Recent</Text>{' '}
          <Text h1>Recent</Text> <Text h1>Recent</Text>
        </TabView.Item>
        <TabView.Item style={{ backgroundColor: 'blue', width: '100%' }}>
          <Text h1>Favorite</Text>
        </TabView.Item>
      </TabView>
      {/* </ScrollView> */}
    </>
  );
}

export default ChallengeMainScreen;
