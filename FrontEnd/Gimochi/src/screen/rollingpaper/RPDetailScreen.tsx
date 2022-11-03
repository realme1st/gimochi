import React from 'react';
import { View, Text } from 'react-native';

function RPDetailScreen({ route }) {
  const ID = route.params.RPId;
  return (
    <View>
      <Text>개별 추카포카</Text>
    </View>
  );
}

export default RPDetailScreen;
