/* eslint-disable @typescript-eslint/restrict-template-expressions */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import React, { useState, useEffect } from 'react';
import { Text } from 'react-native';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';
import { useAppDispatch } from '../../store';
import reloadSlice from '../../slices/reload';
import styled from 'styled-components/native';
import DismissKeyboardView from '../../components/DismissKeyboardView';

function RPMessageWriteScreen({ route, navigation }) {
  const userNickname = useSelector((state: RootState) => state.user.userNickname);
  const [nickname, setNickname] = useState<string>(userNickname);
  const [text, setText] = useState<string>('');
  // const [gifticon, setGifticon] = useState<string>('');
  const sessionId: number = route.params.RPId;
  const dispatch = useAppDispatch();

  const onSubmit = () => {
    axios
      .post(`${Config.API_URL}/session/message`, {
        field: text,
        // img: gifticon,
        nickname: nickname,
        sessionId: sessionId,
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
    <EntireContainer>
      <DismissKeyboardView style={{ backgroundColor: '#ffffff' }}>
        <TitleContainer>
          <TitleText>선물하기</TitleText>
        </TitleContainer>
        <DropdownContainer></DropdownContainer>
        <TitleContainer>
          <TitleText>편지쓰기</TitleText>
        </TitleContainer>
        <FormContainer>
          <Form
            placeholder='추카포카에 담아 보낼 말을 입력해주세요'
            value={text}
            onChangeText={setText}
          ></Form>
        </FormContainer>
        <SubmitButton onPress={onSubmit}>
          <SubmitText>제출</SubmitText>
        </SubmitButton>
      </DismissKeyboardView>
    </EntireContainer>
  );
}

const EntireContainer = styled.View`
  flex: 1;
`;

const TitleContainer = styled.View`
  margin: 5% 10%;
`;

const TitleText = styled.Text`
  font-size: 30px;
  font-family: 'Regular';
`;

const DropdownContainer = styled.View`
  margin: 0% 10%;
`;

const FormContainer = styled.View`
  margin: 5% 10%;
`;

const Form = styled.TextInput``;

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

export default RPMessageWriteScreen;
