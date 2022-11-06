import React from 'react';
import { Tab, Text, TabView } from '@rneui/themed';

export default () => {
  const [index, setIndex] = React.useState(0);

  return (
    <>
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
          titleStyle={{ fontSize: 12 }}
          icon={{ name: 'timer', type: 'ionicon', color: 'white' }}
        />
        <Tab.Item
          title='종료된 챌린지'
          titleStyle={{ fontSize: 12 }}
          icon={{ name: 'heart', type: 'ionicon', color: 'white' }}
        />
      </Tab>

      <TabView value={index} onChange={setIndex} animationType='spring'>
        <TabView.Item style={{ backgroundColor: 'red', width: '100%' }}>
          <Text h1>챌린지</Text>
        </TabView.Item>
        <TabView.Item style={{ backgroundColor: 'blue', width: '100%' }}>
          <Text h1>종료된</Text>
        </TabView.Item>
      </TabView>
    </>
  );
};
