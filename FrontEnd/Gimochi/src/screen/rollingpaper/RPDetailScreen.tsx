/* eslint-disable @typescript-eslint/restrict-template-expressions */
/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import React, { useEffect, useState } from 'react';
import { View, Text } from 'react-native';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';
import { List } from 'reselect/es/types';

function RPDetailScreen({ route }) {
  const sessionId: number = route.params.RPId;
  const userNickname = useSelector((state: RootState) => state.user.userNickname);
  const reload = useSelector((state: RootState) => state.reload.reload);
  const [messageList, setMessageList] = useState<List>([]);
  const [nickname, setNickname] = useState<string>(userNickname);
  const [message, setMessage] = useState<string>('');
  const [image, setImage] = useState<string>('');
  const [modal, setModal] = useState<boolean>(false);

  useEffect(() => {
    axios
      .get(`${Config.API_URL}/session/${sessionId}`)
      .then(function (response) {
        console.log(response);
        console.log(response.data.data.anniversary);
        console.log(response.data.data.sessionMessagesList);
        setMessageList(response.data.data.sessionMessagesList);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [reload]);

  const onPress = () => {
    setModal(true);
  };

  const onSubmit = () => {
    axios
      .post(`${URL}/session/message`, {
        field: message,
        img: image,
        nickname: nickname,
        sessionId: sessionId,
      })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
    setModal(false);
  };
  return (
    <View>
      <Text>{sessionId}번 추카포카</Text>
    </View>
  );
}

export default RPDetailScreen;
