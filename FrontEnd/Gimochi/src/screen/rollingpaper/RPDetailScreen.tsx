/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import React from 'react';
import { View, Text } from 'react-native';

function RPDetailScreen({ route }) {
  const ID = route.params.RPId;
  return (
    <View>
      <Text>{ID}번 추카포카</Text>
    </View>
  );
}

export default RPDetailScreen;
