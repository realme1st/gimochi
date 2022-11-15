/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-return */
import React from 'react';
import { TouchableOpacity } from 'react-native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import GifticonMainScreen from '../screen/gifticon/GifticonMainScreen';
import GifticonUploadScreen from '../screen/gifticon/GifticonUploadScreen';
import GifticonBarcodeScreen from '../screen/gifticon/GifticonBarcodeScreen';
import { faBell } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { Icon } from '@rneui/themed';

export type GifticonStackParamList = {
  GifticonMainScreen: undefined;
  GifticonUploadScreen: undefined;
  GifticonBarcodeScreen: undefined;
};

const Gifticon = createNativeStackNavigator<GifticonStackParamList>();

function GifticonNavigation({ navigation }) {
  return (
    <Gifticon.Navigator
      initialRouteName='GifticonMainScreen'
      screenOptions={{
        headerTitleAlign: 'center',
        headerTitleStyle: {
          fontFamily: 'Regular',
        },
      }}
    >
      <Gifticon.Screen
        name='GifticonMainScreen'
        component={GifticonMainScreen}
        options={{
          title: '티콘모아',
          headerRight: () => (
            <Icon
              name='bell'
              type='evilicon'
              onPress={() => navigation.navigate('NotificationScreen')}
              size={30}
            />
          ),
        }}
      ></Gifticon.Screen>
      <Gifticon.Screen
        name='GifticonUploadScreen'
        component={GifticonUploadScreen}
        options={{ title: '기프티콘 등록' }}
      ></Gifticon.Screen>
      <Gifticon.Screen
        name='GifticonBarcodeScreen'
        component={GifticonBarcodeScreen}
        options={{ presentation: 'transparentModal', headerShown: false, gestureEnabled: true }}
      ></Gifticon.Screen>
    </Gifticon.Navigator>
  );
}

export default GifticonNavigation;
