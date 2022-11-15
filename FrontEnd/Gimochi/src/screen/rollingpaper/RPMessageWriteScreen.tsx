/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/restrict-template-expressions */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import React, { useState, useEffect } from 'react';
import { Image, TouchableOpacity } from 'react-native';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';
import { useAppDispatch } from '../../store';
import screenSlice from '../../slices/screen';
import reloadSlice from '../../slices/reload';
import styled from 'styled-components/native';
import DismissKeyboardView from '../../components/DismissKeyboardView';

function RPMessageWriteScreen({ route, navigation }) {
  const userNickname = useSelector((state: RootState) => state.user.userNickname);
  const [nickname, setNickname] = useState<string>(userNickname);
  const [text, setText] = useState<string>('');
  const [type, setType] = useState<number>(1);
  // const [gifticon, setGifticon] = useState<string>('');
  const sessionId: number = route.params.RPId;
  const sessionType: number = route.params.type;
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(
      screenSlice.actions.addScreen({
        screen: 'RollingpaperScreen',
      }),
    );
    return () => {
      console.log('unmount');
      dispatch(screenSlice.actions.deleteScreen());
    };
  }, []);

  const onSubmit = () => {
    axios
      .post(`${Config.API_URL}/session/message`, {
        field: text,
        // gifticonId: gifticonId,
        nickname: nickname,
        sessionId: sessionId,
        messageType: type,
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

  return (
    <>
      <DismissKeyboardView style={{ flex: 2, backgroundColor: '#ffffff' }}>
        <TitleContainer>
          <TitleText>닉네임 입력</TitleText>
        </TitleContainer>
        <FormContainer>
          <Form placeholder='닉네임을 입력해주세요' value={nickname} onChangeText={setNickname}></Form>
        </FormContainer>
        <TitleContainer>
          <TitleText>선물하기</TitleText>
        </TitleContainer>
        <FormContainer>
          <DropdownContents>추후 추가예정</DropdownContents>
        </FormContainer>
        <TitleContainer>
          <TitleText>편지쓰기</TitleText>
        </TitleContainer>
        <FormContainer>
          <Form
            placeholder='추카포카에 담아 보낼 말을 입력해주세요'
            value={text}
            onChangeText={setText}
            multiline={true}
          ></Form>
        </FormContainer>
        <TitleContainer>
          <TitleText>메시지 장식하기</TitleText>
        </TitleContainer>
        <ImageContainer>
          <TouchableOpacity onPress={() => setType(1)}>
            {type === 1 ? (
              <Image
                source={require('../../assets/images/homeMochi.png')}
                resizeMode='contain'
                style={{ height: 70 }}
              />
            ) : (
              <Image
                source={require('../../assets/images/homeMochi.png')}
                resizeMode='contain'
                style={{ height: 70, opacity: 0.5 }}
              />
            )}
          </TouchableOpacity>
          <TouchableOpacity onPress={() => setType(2)}>
            {type === 2 ? (
              <Image
                source={require('../../assets/images/attendMochi.png')}
                resizeMode='contain'
                style={{ height: 70 }}
              />
            ) : (
              <Image
                source={require('../../assets/images/attendMochi.png')}
                resizeMode='contain'
                style={{ height: 70, opacity: 0.5 }}
              />
            )}
          </TouchableOpacity>
          <TouchableOpacity onPress={() => setType(3)}>
            {type === 3 ? (
              <Image
                source={require('../../assets/images/challengeMochi.png')}
                resizeMode='contain'
                style={{ height: 70 }}
              />
            ) : (
              <Image
                source={require('../../assets/images/challengeMochi.png')}
                resizeMode='contain'
                style={{ height: 70, opacity: 0.5 }}
              />
            )}
          </TouchableOpacity>
          <TouchableOpacity onPress={() => setType(4)}>
            {type === 4 ? (
              <Image
                source={require('../../assets/images/playMochi.png')}
                resizeMode='contain'
                style={{ height: 70 }}
              />
            ) : (
              <Image
                source={require('../../assets/images/playMochi.png')}
                resizeMode='contain'
                style={{ height: 70, opacity: 0.5 }}
              />
            )}
          </TouchableOpacity>
        </ImageContainer>
      </DismissKeyboardView>
      <SubmitButton onPress={onSubmit}>
        <SubmitText>제출</SubmitText>
      </SubmitButton>
    </>
  );
}

const EntireContainer = styled.ScrollView`
  flex: 1;
  background-color: #ffffff;
`;

const TitleContainer = styled.View`
  margin: 3% 10%;
`;

const TitleText = styled.Text`
  font-size: 25px;
  font-family: 'Regular';
  color: #000000;
`;

const DropdownContents = styled.Text`
  font-size: 15px;
  font-family: 'Regular';
  color: #000000;
`;

const FormContainer = styled.View`
  margin: 0% 10%;
  border: 1px;
  border-radius: 10px;
`;

const Form = styled.TextInput``;

const ImageContainer = styled.View`
  width: 80%;
  height: 80px;
  margin: 3% 10%;
  flex-direction: row;
`;

const SubmitButton = styled.TouchableOpacity`
  width: 20%;
  border-radius: 10px;
  background-color: #ffa401;
  align-items: center;
  position: absolute;
  left: 300px;
  top: 480px;
`;

const SubmitText = styled.Text`
  font-family: 'Regular';
  font-size: 20px;
  color: #ffffff;
  margin: 5%;
`;

export default RPMessageWriteScreen;
