/* eslint-disable @typescript-eslint/no-misused-promises */
/* eslint-disable @typescript-eslint/no-unsafe-return */
/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/restrict-template-expressions */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
import React, { useCallback, useState, useEffect } from 'react';
import { Text, View, TouchableOpacity, Alert, Image, Dimensions } from 'react-native';
import ImageCropPicker from 'react-native-image-crop-picker';
import ImageResizer from 'react-native-image-resizer';
import axios, { AxiosError } from 'axios';
import { useSelector } from 'react-redux';
import { RootState } from '../store/reducer';
import Config from 'react-native-config';

function GifticonScreen() {
  const [image, setImage] = useState<{ uri: string; name: string; type: string }>();
  const [preview, setPreview] = useState<{ uri: string }>();
  const accessToken = useSelector((state: RootState) => state.user.accessToken);
  const userId = useSelector((state: RootState) => state.user.userId);
  console.log(preview);

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

  const onComplete = useCallback(async () => {
    if (!image) {
      Alert.alert('알림', '파일을 업로드해주세요.');
      return;
    }
    console.log(preview);
    const formData = new FormData();
    // const info = { userId: userId, gifticonScore: 'testStore', gifticonPeriod: '2022-11-08' };
    formData.append('file', image);
    // formData.append('gifticon', info);
    console.log(formData);
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

  return (
    <View>
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
      <TouchableOpacity onPress={onComplete}>
        <Text>기프티콘 제출</Text>
      </TouchableOpacity>
    </View>
  );
}

export default GifticonScreen;
