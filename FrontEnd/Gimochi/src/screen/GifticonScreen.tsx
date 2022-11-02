/* eslint-disable @typescript-eslint/no-misused-promises */
/* eslint-disable @typescript-eslint/no-unsafe-return */
/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/restrict-template-expressions */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
import React, { useCallback, useState } from 'react';
import { Text, View, TouchableOpacity, Alert, Image, Dimensions } from 'react-native';
import ImageCropPicker from 'react-native-image-crop-picker';
import ImageResizer from 'react-native-image-resizer';
import axios, { AxiosError } from 'axios';
import { useSelector } from 'react-redux';
import { RootState } from '../store/reducer';
// import Config from 'react-native-config';

function GifticonScreen() {
  const [image, setImage] = useState<{ uri: string; name: string; type: string }>();
  const [preview, setPreview] = useState<{ uri: string }>();
  const accessToken = useSelector((state: RootState) => state.user.accessToken);
  console.log(preview);
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
      console.log(r.uri, r.name);

      setImage({
        uri: r.uri,
        name: r.name,
        type: response.mime,
      });
    });
  }, []);

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
    const formData = new FormData();
    formData.append('image', image);
    try {
      await axios.post('https://k7a205.p.ssafy.io/api/gifticon', formData, {
        headers: {
          authorization: `Bearer ${accessToken}`,
        },
      });
      Alert.alert('알림', '기프티콘 업로드 완료되었습니다.');
    } catch (error) {
      const errorResponse = (error as AxiosError).response;
      if (errorResponse) {
        Alert.alert('알림', errorResponse.data.message);
      }
    }
    console.log(formData);
  }, [image, accessToken]);

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
