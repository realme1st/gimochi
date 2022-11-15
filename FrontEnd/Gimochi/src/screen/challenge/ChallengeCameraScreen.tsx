import React, { useState, useCallback, useEffect, useRef } from 'react';
import {
  ScrollView,
  TouchableOpacity,
  Alert,
  Dimensions,
  Image,
  Pressable,
  StyleSheet,
  Text,
  View,
  Platform,
} from 'react-native';
import {
  BottomSheet,
  Button,
  ListItem,
  Input,
  Tab,
  TabView,
  ThemeProvider,
  createTheme,
  SpeedDial,
  Dialog,
  Icon,
} from '@rneui/themed';
import ImagePicker from 'react-native-image-crop-picker';
import ImageResizer from 'react-native-image-resizer';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';
import { useAppDispatch } from '../../store';
import reloadSlice from '../../slices/reload';

function ChallengeCameraScreen({ navigation, route }) {
  const [image, setImage] = useState<{ uri: string; name: string; type: string }>();
  const [preview, setPreview] = useState<{ uri: string }>();
  const accessToken = useSelector((state: RootState) => state.user.accessToken);
  const userId = useSelector((state: RootState) => state.user.userId);

  const chId = route.params.chId ? route.params.chId : '';
  const dispatch = useAppDispatch();

  console.log(chId);

  // axios
  // const onComplete = useCallback(async () => {
  //   if (!image) {
  //     Alert.alert('알림', '파일을 업로드해주세요.');
  //     return;
  //   }
  //   if (!chId) {
  //     Alert.alert('알림', '유효하지 않은 주문입니다.');
  //     return;
  //   }
  //   const formData = new FormData();

  //   formData.append('chId', chId);
  //   formData.append('image', image);
  //   // 이미지 서버에 트큰이랑 보내줌?
  //   try {
  //     await axios.post(`${Config.API_URL}/complete`, formData, {
  //       headers: {
  //         authorization: `Bearer ${accessToken}`,
  //       },
  //     });
  //     Alert.alert('알림', '완료처리 되었습니다.');
  //     navigation.goBack(); // 뒤로가기  스택관리
  //     // navigation.navigate('Settings');
  //     dispatch(orderSlice.actions.rejectOrder(chId));
  //   } catch (error) {
  //     const errorResponse = (error as AxiosError).response;
  //     if (errorResponse) {
  //       Alert.alert('알림', errorResponse.data.message);
  //     }
  //   }
  // }, [dispatch, navigation, image, chId, accessToken]);

  const goMain = () => {
    navigation.navigate('ChallengeMainScreen');
  };
  // ImageCropPicker에서 crop된 사진을 resizing한 후 객체 형태(경로, 파일이름, 타입)로 이미지 파일 저장하는 메서드
  const onResponse = useCallback(async (response) => {
    console.log(response.width, response.height, response.exif);
    setPreview({ uri: `data:${response.mime};base64,${response.data}` });
    const orientation = (response.exif as any)?.Orientation;
    console.log('orientation', orientation);
    return ImageResizer.createResizedImage(
      response.path, // 파일 경로
      600,
      600,
      response.mime.includes('jpeg') ? 'JPEG' : 'PNG',
      100,
      0,
      // orientation === 3 ? -90 : 0, // 이미지 돌아가있을때
    ).then((r) => {
      console.log(r.uri, r.name);

      setImage({
        uri: r.uri,
        name: r.name,
        type: response.mime,
        // type: 'image/jpeg',
      });
    });
  }, []);
  // 권한 설정 된 상태에서 카메라 촬영
  const onTakePhoto = useCallback(() => {
    return ImagePicker.openCamera({
      includeBase64: true,
      includeExif: true,
      saveToPhotos: true,
      cropping: true,
      freeStyleCropEnabled: true,
      mediaType: 'photo',
    })
      .then(onResponse)
      .catch(console.log);
  }, [onResponse]);
  // 갤러리에서 사진 파일을 골라서 crop하는 메서드
  const onChangeFile = useCallback(() => {
    return ImagePicker.openPicker({
      includeExif: true,
      includeBase64: true,
      cropping: true,
      freeStyleCropEnabled: true,
      mediaType: 'photo',
    })
      .then(onResponse)
      .catch(console.log);
  }, [onResponse]);

  return (
    <View style={{ backgroundColor: '#fff', flex: 1 }}>
      <ScrollView>
        <View style={styles.orderId}>
          <Text>챌린지번호: {chId}</Text>
        </View>
        <View style={styles.preview}>
          {preview && <Image style={styles.previewImage} source={preview} />}
        </View>
        <View style={styles.buttonWrapper}>
          <Pressable style={styles.button} onPress={onTakePhoto}>
            <Text style={styles.buttonText}>이미지 촬영</Text>
          </Pressable>
          <Pressable style={styles.button} onPress={onChangeFile}>
            <Text style={styles.buttonText}>이미지 선택</Text>
          </Pressable>
          {/* 업로드시 버튼 광클 방지 로직 해보기   | 디스에이블, 로딩*/}
          <Pressable
            style={image ? styles.button : StyleSheet.compose(styles.button, styles.buttonDisabled)}
            // onPress={onComplete}
          >
            <Text style={styles.buttonText}>완료</Text>
          </Pressable>
        </View>
      </ScrollView>
      <Icon
        name='delete'
        type='material'
        color='#FFE7BC'
        size={25}
        reverse
        reverseColor='#FFA401'
        onPress={() => goMain()}
        iconStyle={{ fontSize: 33 }}
        containerStyle={{ position: 'absolute', top: '85%', left: '5%' }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  orderId: {
    padding: 20,
  },
  preview: {
    marginHorizontal: 10,
    width: Dimensions.get('window').width - 20,
    height: Dimensions.get('window').height / 3,
    backgroundColor: '#D2D2D2',
    marginBottom: 10,
  },
  previewImage: {
    height: Dimensions.get('window').height / 3,
    resizeMode: 'contain',
    // resizeMode: 'cover','center',
  },
  buttonWrapper: { flexDirection: 'row', justifyContent: 'center' },
  button: {
    paddingHorizontal: 20,
    paddingVertical: 10,
    width: 120,
    alignItems: 'center',
    backgroundColor: 'yellow',
    borderRadius: 5,
    margin: 5,
  },
  buttonText: {
    color: 'black',
  },
  buttonDisabled: {
    backgroundColor: 'gray',
  },
});

export default ChallengeCameraScreen;

// import {
//   NavigationProp,
//   RouteProp,
//   useNavigation,
//   useRoute,
// } from '@react-navigation/native';
// import {LoggedInParamList} from '../../AppInner';
// import axios, {AxiosError} from 'axios';
// import Config from 'react-native-config';
// import {useSelector} from 'react-redux';
// import {RootState} from '../store/reducer';
// import orderSlice from '../slices/order';
// import {useAppDispatch} from '../store';

// function Complete() {

//   const route = useRoute<RouteProp<LoggedInParamList>>();
//   const navigation = useNavigation<NavigationProp<LoggedInParamList>>();

//   const accessToken = useSelector((state: RootState) => state.user.accessToken);
