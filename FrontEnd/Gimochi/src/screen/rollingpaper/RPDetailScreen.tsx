/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/restrict-template-expressions */
/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import React, { useEffect, useState } from 'react';
import { View, Text, ImageBackground, TextInput } from 'react-native';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';
import Modal from 'react-native-modal';
import styled from 'styled-components/native';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faMessage } from '@fortawesome/free-solid-svg-icons';
import { useAppDispatch } from '../../store';
import screenSlice from '../../slices/screen';
import reloadSlice from '../../slices/reload';
import DismissKeyboardView from '../../components/DismissKeyboardView';

function RPDetailScreen({ navigation, route }) {
  const sessionId: number = route.params.RPId;
  const sessionTypeId: number = route.params.sessionTypeId;
  const userNickname = useSelector((state: RootState) => state.user.userNickname);
  const reload = useSelector((state: RootState) => state.reload.reload);
  const [messageList, setMessageList] = useState([]);
  const [modal, setModal] = useState<boolean>(false);
  const [writeModal, setWriteModal] = useState<boolean>(false);
  const [nickname, setNickname] = useState('');
  const [writeNickname, setWriteNickname] = useState(userNickname);
  const [text, setText] = useState('');
  const [loading, setLoading] = useState(true);
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

  useEffect(() => {
    navigation.setOptions({
      title:
        sessionTypeId === 1
          ? '생일 추카포카'
          : sessionTypeId === 2
          ? '졸업 추카포카'
          : sessionTypeId === 3
          ? '크리스마스 추카포카'
          : sessionTypeId === 4
          ? '설날 추카포카'
          : '기타 추카포카',
    });
  }, [navigation]);

  useEffect(() => {
    axios
      .get(`${Config.API_URL}/session/${sessionId}`)
      .then(function (response) {
        console.log(response);
        console.log(response.data.data.anniversary);
        console.log(response.data.data.sessionMessageResDtoList);
        setMessageList(response.data.data.sessionMessageResDtoList);
      })
      .catch(function (error) {
        console.log(error);
      })
      .finally(() => setLoading(false));
  }, [reload]);

  const goMessageDetail = (index) => {
    // setIdx(index);
    setNickname(messageList[index].nickname);
    setText(messageList[index].field);
    setModal(true);
  };

  const goMessageWrite = () => {
    // setIdx(index);
    setText('');
    setWriteModal(true);
  };

  const onSubmit = () => {
    axios
      .post(`${Config.API_URL}/session/message`, {
        field: text,
        // img: gifticon,
        nickname: writeNickname,
        sessionId: sessionId,
      })
      .then(function (response) {
        console.log(response);
        dispatch(
          reloadSlice.actions.setReload({
            reload: String(new Date()),
          }),
        );
        setWriteModal(false);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  // const onPress = () => {
  //   navigation.navigate('RPMessageWriteScreen', { RPId: sessionId });
  // };

  if (loading)
    return (
      <View>
        <Text>Loading...</Text>
      </View>
    );

  return (
    <RPDetailContainer>
      <ImageBackground
        source={
          sessionTypeId === 1
            ? require('../../assets/images/birthday1.jpg')
            : sessionTypeId === 2
            ? require('../../assets/images/graduation.png')
            : sessionTypeId === 3
            ? require('../../assets/images/christmas1.jpg')
            : require('../../assets/images/birthday2.png')
        }
        // source={require('../../../android/app/src/main/assets/images/birthday1.jpg')}
        style={{ width: '100%', height: '100%' }}
        imageStyle={{ opacity: 0.3 }}
      >
        <DismissKeyboardView>
          <Text>{sessionId}번 추카포카</Text>
          <MessageListContainer>
            {messageList.map((message, index) => (
              <MessageItemButton key={index} onPress={() => goMessageDetail(index)}>
                <Text>{index}번 메시지</Text>
              </MessageItemButton>
            ))}
          </MessageListContainer>
          <Modal
            animationType='fade'
            transparent={true}
            visible={modal}
            // presentationStyle={'pageSheet'}
            onRequestClose={() => {
              // setModalVisible(!modalVisible);
              setModal(false);
            }}
            onBackdropPress={() => setModal(false)}
          >
            <ModalContainer>
              <ModalTitleText>{nickname}님이 보낸 선물</ModalTitleText>
              <ModalGifticonContainer>
                <ModalText>대충 선물</ModalText>
              </ModalGifticonContainer>
              <ModalTitleText>{nickname}님이 보낸 메시지</ModalTitleText>
              <ModalTextContainer>
                <ModalText>{text}</ModalText>
              </ModalTextContainer>
            </ModalContainer>
          </Modal>
          <Modal
            animationType='fade'
            transparent={true}
            visible={writeModal}
            // presentationStyle={'pageSheet'}
            onRequestClose={() => {
              // setModalVisible(!modalVisible);
              setWriteModal(false);
            }}
            onBackdropPress={() => setWriteModal(false)}
          >
            <WriteModalContainer>
              <WriteModalTitleText>닉네임 입력</WriteModalTitleText>
              <WriteModalTopContainer>
                <Form value={writeNickname} onChangeText={setWriteNickname}></Form>
              </WriteModalTopContainer>
              <WriteModalTitleText>선물하기</WriteModalTitleText>
              <WriteModalTopContainer>
                <ModalText>대충 선물</ModalText>
              </WriteModalTopContainer>
              <WriteModalTitleText>편지쓰기</WriteModalTitleText>
              <WriteModalTextContainer>
                <Form
                  placeholder='추카포카에 담아 보낼 말을 입력해주세요'
                  value={text}
                  onChangeText={setText}
                  multiline={true}
                ></Form>
              </WriteModalTextContainer>
              <ModalButton onPress={onSubmit}>
                <ModalButtonText>제출</ModalButtonText>
              </ModalButton>
            </WriteModalContainer>
          </Modal>
        </DismissKeyboardView>
        <CreateButton onPress={goMessageWrite}>
          <FontAwesomeIcon icon={faMessage} size={50} color={'#ffa401'} />
        </CreateButton>
      </ImageBackground>
    </RPDetailContainer>
  );
}

const RPDetailContainer = styled.View`
  background-color: #ffffff;
  flex: 1;
`;

const MessageListContainer = styled.View``;

const MessageItemButton = styled.TouchableOpacity``;

const CreateButton = styled.TouchableOpacity`
  position: absolute;
  left: 320px;
  top: 480px;
`;

const ModalContainer = styled.View`
  margin: 15%;
  width: 70%;
  height: 65%;
  background-color: #ffe7bc;
  border-radius: 15px;
  border: 1px solid #000;
  align-items: center;
`;

const WriteModalContainer = styled.View`
  margin: 7.5%;
  width: 85%;
  height: 450px;
  background-color: #ffe7bc;
  border-radius: 15px;
  border: 1px solid #000;
  align-items: center;
`;

const WriteModalTopContainer = styled.View`
  width: 80%;
  background-color: #ffffff;
  border-radius: 10px;
  height: 40px;
  justify-content: center;
`;

const ModalTitleText = styled.Text`
  margin: 5% auto 5% 10%;
  font-family: 'Regular';
  font-size: 15px;
`;

const WriteModalTitleText = styled.Text`
  margin: 3% auto 3% 10%;
  font-family: 'Regular';
  font-size: 15px;
`;

const ModalTextContainer = styled.View`
  background-color: #ffffff;
  width: 80%;
  height: 60%;
  border-radius: 10px;
`;

const WriteModalTextContainer = styled.View`
  background-color: #ffffff;
  width: 80%;
  height: 40%;
  border-radius: 10px;
`;

const ModalGifticonContainer = styled.View`
  background-color: #ffffff;
  width: 80%;
  border-radius: 10px;
  height: 10%;
  justify-content: center;
`;

const ModalText = styled.Text`
  font-family: 'Regular';
  font-size: 12px;
  margin: 3%;
`;

const Form = styled.TextInput`
  color: #000000;
  font-size: 12px;
`;

const ModalButton = styled.TouchableOpacity`
  background-color: #ffa401;
  margin: 5% 10% 0% auto;
  width: 20%;
  height: 30px;
  border-radius: 10px;
  align-items: center;
  justify-content: center;
`;
const ModalButtonText = styled.Text`
  color: #ffffff;
  font-family: 'Regular';
`;

export default RPDetailScreen;
