/* eslint-disable @typescript-eslint/restrict-template-expressions */
/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
import React, { useEffect, useState } from 'react';
import { Text, View } from 'react-native';
import styled from 'styled-components/native';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faCirclePlus } from '@fortawesome/free-solid-svg-icons';

function GifticonMainScreen({ navigation }) {
  const userId = useSelector((state: RootState) => state.user.userId);
  const reload = useSelector((state: RootState) => state.reload.reload);
  const [gifticons, setGifticons] = useState([]);

  useEffect(() => {
    axios
      .get(`${Config.API_URL}/gifticon/${userId}`)
      .then(function (response) {
        console.log(response);
        console.log(response.data.data);
        setGifticons(response.data.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [reload]);

  const goUpload = () => {
    navigation.navigate('GifticonUploadScreen');
  };

  return (
    <>
      <Text>기프티콘메인스크린</Text>
      {gifticons.map((gifticon, index) => (
        <View key={index}>
          <Text>{gifticon.gifticonStore}</Text>
          <Text>{gifticon.gifticonPeriod}</Text>
        </View>
      ))}
      <CreateButton onPress={goUpload}>
        <FontAwesomeIcon icon={faCirclePlus} size={50} color={'#ffa401'} />
      </CreateButton>
    </>
  );
}

const CreateButton = styled.TouchableOpacity`
  position: absolute;
  left: 80%;
  top: 85%;
`;

export default GifticonMainScreen;
