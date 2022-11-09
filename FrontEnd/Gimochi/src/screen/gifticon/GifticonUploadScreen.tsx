/* eslint-disable @typescript-eslint/no-floating-promises */
/* eslint-disable @typescript-eslint/no-misused-promises */
/* eslint-disable @typescript-eslint/no-unsafe-return */
/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/restrict-template-expressions */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
import React, { useCallback, useState, useEffect } from 'react';
import { Text, View, TouchableOpacity, Alert, Image, Dimensions, TextInput } from 'react-native';
import ImageCropPicker from 'react-native-image-crop-picker';
import ImageResizer from 'react-native-image-resizer';
import axios, { AxiosError } from 'axios';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';
import Config from 'react-native-config';
import DismissKeyboardView from '../../components/DismissKeyboardView';

function GifticonUploadScreen() {
  const [image, setImage] = useState<{ uri: string; name: string; type: string }>();
  const [preview, setPreview] = useState<{ uri: string }>();
  const accessToken = useSelector((state: RootState) => state.user.accessToken);
  const userId = useSelector((state: RootState) => state.user.userId);
  const [store, setStore] = useState('');
  const [period, setPeriod] = useState('');
  const [gifticonId, setGifticonId] = useState(1);

  // console.log(preview);

  // ImageCropPicker에서 crop된 사진을 resizing한 후 객체 형태(경로, 파일이름, 타입)로 이미지 파일 저장하는 메서드
  const onResponse = useCallback(async (response) => {
    console.log(response.width, response.height, response.exif);
    setPreview({ uri: `data:${response.mime};base64,${response.data}` });
    const orientation = response.exif?.Orientation;
    console.log('orientation', orientation);
    return ImageResizer.createResizedImage(
      response.path,
      600,
      600,
      response.mime.includes('jpeg') ? 'JPEG' : 'PNG',
      100,
      0,
    ).then((r) => {
      console.log(r);
      console.log(r.uri);
      console.log(r.name);
      setImage({
        uri: r.uri,
        name: r.name,
        type: 'image/jpeg',
      });
    });
  }, []);

  // 갤러리에서 사진 파일을 골라서 crop하는 메서드
  const onChangeFile = useCallback(() => {
    return ImageCropPicker.openPicker({
      includeExif: true,
      includeBase64: true,
      cropping: true,
      freeStyleCropEnabled: true,
      mediaType: 'photo',
    })
      .then(onResponse)
      .catch(console.log);
  }, [onResponse]);

  const postOCR = useCallback(async () => {
    if (!image) {
      Alert.alert('알림', '파일을 업로드해주세요.');
      return;
    }
    console.log(preview);
    const formData = new FormData();
    // const info = { userId: userId, gifticonScore: 'testStore', gifticonPeriod: '2022-11-08' };
    formData.append('file', image);
    // formData.append('gifticon', info);
    // console.log(formData);
    await axios
      .post(`${Config.API_URL}/gifticon/ocr/${userId}`, formData, {
        headers: {
          'content-type': 'multipart/form-data',
        },
      })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [image, accessToken, userId]);

  const postInfo = async () => {
    const formData = new FormData();
    formData.append('file', image);
    await axios
      .post(`${Config.API_URL}/gifticon/info`, {
        gifticonPeriod: period,
        gifticonStore: store,
        userId: userId,
      })
      .then(function (response) {
        console.log(response);
        // setGifticonId(response.data.data.gifticonId);
        postImage(response.data.data.gifticonId, formData);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  const postImage = async (id: number, formData: FormData) => {
    await axios
      .post(`${Config.API_URL}/gifticon/img/${id}`, formData, {
        headers: {
          'content-type': 'multipart/form-data',
        },
      })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  return (
    <DismissKeyboardView style={{ backgroundColor: '#ffffff' }}>
      <Text>티콘모아</Text>
      <TouchableOpacity onPress={onChangeFile}>
        <Text>기프티콘 등록</Text>
      </TouchableOpacity>
      {preview && (
        <Image
          style={{ height: Dimensions.get('window').height / 3, resizeMode: 'contain' }}
          source={preview}
        />
      )}
      <TouchableOpacity onPress={postOCR}>
        <Text>1차 이미지 제출(OCR)</Text>
      </TouchableOpacity>
      <TextInput placeholder='대충 사용처' value={store} onChangeText={setStore}></TextInput>
      <TextInput placeholder='대충 기간' value={period} onChangeText={setPeriod}></TextInput>
      <TouchableOpacity onPress={postInfo}>
        <Text>정보 제출</Text>
      </TouchableOpacity>
    </DismissKeyboardView>
  );
}

export default GifticonUploadScreen;
