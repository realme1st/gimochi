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
import axios from 'axios';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';
import { useAppDispatch } from '../../store';
import reloadSlice from '../../slices/reload';
import Config from 'react-native-config';
import DismissKeyboardView from '../../components/DismissKeyboardView';
import { format } from 'date-fns';
import ko from 'date-fns/esm/locale/ko/index.js';
import DateTimePicker from '@react-native-community/datetimepicker';
import styled from 'styled-components/native';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faCalendar } from '@fortawesome/free-solid-svg-icons';

function GifticonUploadScreen({ navigation }) {
  const [image, setImage] = useState<{ uri: string; name: string; type: string }>();
  const [preview, setPreview] = useState<{ uri: string }>();
  const accessToken = useSelector((state: RootState) => state.user.accessToken);
  const userId = useSelector((state: RootState) => state.user.userId);
  const [store, setStore] = useState('');
  // const [period, setPeriod] = useState('');
  const [date, onChangeDate] = useState<Date>(new Date());
  const [visible, setVisible] = useState<boolean>(false); // 달력 모달 노출 여부
  const dispatch = useAppDispatch();

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

  // 백으로 사진파일 보내서 OCR 처리 후 얻은 텍스트값 받음
  const postOCR = useCallback(async () => {
    if (!image) {
      Alert.alert('알림', '파일을 업로드해주세요.');
      return;
    }
    console.log(preview);
    const formData = new FormData();
    formData.append('file', image);
    console.log(formData);
    await axios
      .post(`${Config.API_URL}/gifticon/ocr/${userId}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      .then(function (response) {
        console.log(response);
        // setPeriod(response.data.data.period);
        // setStore(response.data.data.store);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [image, accessToken, userId]);

  // OCR 처리 후 텍스트 전처리된 값 받아와서 필요시 수정
  const postInfo = async () => {
    const formData = new FormData();
    formData.append('file', image);
    await axios
      .post(`${Config.API_URL}/gifticon/info`, {
        gifticonPeriod: format(date, 'yyyy-MM-dd'),
        gifticonStore: store,
        userId: userId,
      })
      .then(function (response) {
        console.log(response);
        postImage(response.data.data.gifticonId, formData);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  // 이미지 서버에 post
  const postImage = async (id: number, formData: FormData) => {
    await axios
      .post(`${Config.API_URL}/gifticon/img/${id}`, formData, {
        headers: {
          'content-type': 'multipart/form-data',
        },
      })
      .then(function (response) {
        console.log(response);
        dispatch(
          reloadSlice.actions.setReload({
            reload: String(new Date()),
          }),
        );
        navigation.goBack();
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  const onPressDate = () => {
    // 날짜 클릭 시
    setVisible(true); // 모달 open
  };

  const onChange = (event: void, selectedDate: Date) => {
    const currentDate: Date = selectedDate || date;
    onChangeDate(currentDate);
    console.log(currentDate);
    setVisible(false);
  };

  const onConfirm = (selectedDate: Date) => {
    // 날짜 또는 시간 선택 시
    setVisible(false); // 모달 close
    onChangeDate(selectedDate); // 선택한 날짜 변경
  };

  const onCancel = () => {
    // 취소 시
    setVisible(false); // 모달 close
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
      <FormContainer>
        <DateButton onPress={onPressDate}>
          <DateButtonContainer>
            <FontAwesomeIcon icon={faCalendar} size={20} />
            <DateText>{format(new Date(date), 'PPP', { locale: ko })}</DateText>
          </DateButtonContainer>
        </DateButton>
        {visible && (
          <DateTimePicker
            mode={'date'}
            display='spinner'
            onConfirm={onConfirm}
            onCancel={onCancel}
            onChange={onChange}
            value={date}
            locale='ko'
          />
        )}
      </FormContainer>
      <SubmitButton onPress={postInfo}>
        <SubmitText>제출</SubmitText>
      </SubmitButton>
    </DismissKeyboardView>
  );
}

const FormContainer = styled.View`
  margin: 5% 10%;
`;

const DateButton = styled.TouchableOpacity`
  width: 60%;
`;

const DateButtonContainer = styled.View`
  align-items: center;
  flex-direction: row;
`;

const DateText = styled.Text`
  font-size: 20px;
  font-family: 'Regular';
  margin-left: 5%;
`;

const SubmitButton = styled.TouchableOpacity`
  width: 20%;
  border-radius: 10px;
  background-color: #ffa401;
  align-items: center;
  margin-left: 70%;
`;

const SubmitText = styled.Text`
  font-family: 'Regular';
  font-size: 20px;
  color: #ffffff;
  margin: 5%;
`;

export default GifticonUploadScreen;
