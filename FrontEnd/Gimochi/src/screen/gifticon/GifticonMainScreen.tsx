/* eslint-disable react-hooks/rules-of-hooks */
/* eslint-disable @typescript-eslint/no-misused-promises */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/no-unsafe-return */
/* eslint-disable @typescript-eslint/restrict-template-expressions */
/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
import React, { useEffect, useState } from 'react';
import { Text, View, TouchableOpacity } from 'react-native';
import styled from 'styled-components/native';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faCirclePlus } from '@fortawesome/free-solid-svg-icons';
import { FlatGrid } from 'react-native-super-grid';
import FastImage from 'react-native-fast-image';
import { Tab, Icon, TabView, ThemeProvider, createTheme } from '@rneui/themed';
import Modal from 'react-native-modal';
import { useAppDispatch } from '../../store';
import reloadSlice from '../../slices/reload';

function GifticonMainScreen({ navigation }) {
  const userId = useSelector((state: RootState) => state.user.userId);
  const reload = useSelector((state: RootState) => state.reload.reload);
  const [gifticons, setGifticons] = useState([]);
  const [loading, setLoading] = useState(true);
  const [index, setIndex] = useState(0);
  const [modal, setModal] = useState(false);
  const [period, setPeriod] = useState(new Date());
  const [store, setStore] = useState('');
  const [id, setId] = useState('');
  const [path, setPath] = useState('');
  const dispatch = useAppDispatch();

  useEffect(() => {
    axios
      .get(`${Config.API_URL}/gifticon/uid/${userId}`)
      .then(function (response) {
        setGifticons(response.data.data);
        console.log(response);
        // console.log(response.data.data);
      })
      .catch(function (error) {
        console.log(error);
      })
      .finally(() => setLoading(false));
  }, [reload]);

  const goUpload = () => {
    navigation.navigate('GifticonUploadScreen');
  };

  const goGifticonDetail = (index) => {
    setPeriod(gifticons[index].gifticonPeriod);
    setStore(gifticons[index].gifticonStore);
    setId(gifticons[index].gifticonId);
    setPath(gifticons[index].gifticonPath);
    setModal(true);
    console.log(gifticons[index].gifticonPath);
  };

  const useGifticon = async (id: string) => {
    await axios
      .put(`${Config.API_URL}/gifticon/used`, {
        gifticonId: id,
        userId: userId,
      })
      .then(function (response) {
        console.log(response);
        dispatch(
          reloadSlice.actions.setReload({
            reload: String(new Date()),
          }),
        );
        setModal(false);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  const deleteGifticon = async (id) => {
    await axios
      .delete(`${Config.API_URL}/gifticon/${userId}/${id}`)
      .then(function (response) {
        console.log(response);
        dispatch(
          reloadSlice.actions.setReload({
            reload: String(new Date()),
          }),
        );
        setModal(false);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  if (loading)
    return (
      <View>
        <Text>Loading...</Text>
      </View>
    );
  return (
    <EntireContainer>
      <Tab
        value={index}
        onChange={(e) => setIndex(e)}
        indicatorStyle={{
          height: 0,
        }}
        style={{
          borderRadius: 21,
          backgroundColor: '#F6F6F6',
          marginTop: 21,
          marginHorizontal: 10,
          height: 42,
        }}
        variant='primary'
      >
        <Tab.Item
          title='미사용'
          containerStyle={{
            borderRadius: 20,
            backgroundColor: index == 0 ? 'white' : '#F6F6F6',
            margin: 2,
            height: 40,
            padding: 0,
            justifyContent: 'center',
          }}
          titleStyle={{
            fontSize: 20,
            color: index == 0 ? '#FFA401' : '#686868',
            paddingHorizontal: 0,
            paddingVertical: 0,
            fontFamily: 'Regular',
          }}
        />
        <Tab.Item
          title='사용 완료'
          containerStyle={{
            borderRadius: 20,
            backgroundColor: index == 1 ? 'white' : '#F6F6F6',
            margin: 2,
            height: 40,
            padding: 0,
            justifyContent: 'center',
          }}
          titleStyle={{
            fontSize: 20,
            color: index == 1 ? '#FFA401' : '#686868',
            paddingHorizontal: 0,
            paddingVertical: 0,
            fontFamily: 'Regular',
          }}
        />
      </Tab>
      <TabView value={index} onChange={setIndex} animationType='spring'>
        <TabView.Item style={{ backgroundColor: 'white', width: '100%' }}>
          <FlatGrid
            itemDimension={140}
            data={gifticons.filter((gifticon) => !gifticon.gifticonUsed)}
            style={{}}
            spacing={10}
            renderItem={({ item, index }) => (
              <GifticonItemContainer onPress={() => goGifticonDetail(index)}>
                {/* 사용기한 얼마 안남았을때 여기다가 D-XX 쓰기 */}
                <FastImage source={{ uri: item.gifticonPath }} style={{ width: 80, height: 80 }} />
                <GifticonItemText>{item.gifticonPeriod}</GifticonItemText>
                <GifticonItemText>{item.gifticonStore}</GifticonItemText>
              </GifticonItemContainer>
            )}
          />
        </TabView.Item>
        <TabView.Item style={{ backgroundColor: 'white', width: '100%' }}>
          <FlatGrid
            itemDimension={130}
            data={gifticons.filter((gifticon) => gifticon.gifticonUsed)}
            style={{}}
            spacing={10}
            renderItem={({ item, index }) => (
              <GifticonItemContainer onPress={() => goGifticonDetail(index)}>
                <FastImage
                  source={{ uri: item.gifticonPath }}
                  style={{ width: 80, height: 80 }}
                  resizeMode='contain'
                />
                <GifticonItemText>{item.gifticonPeriod}</GifticonItemText>
                <GifticonItemText>{item.gifticonStore}</GifticonItemText>
              </GifticonItemContainer>
            )}
          />
        </TabView.Item>
      </TabView>
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
          <FastImage source={{ uri: path }} style={{ width: 120, height: 180 }} resizeMode='contain' />
          <ModalText>{period}</ModalText>
          <ModalText>{store}</ModalText>
          <ModalButtonContainer>
            <ModalButton>
              <ModalButtonText onPress={() => useGifticon(id)}>사용완료</ModalButtonText>
            </ModalButton>
            <ModalButton>
              <ModalButtonText onPress={() => deleteGifticon(id)}>삭제하기</ModalButtonText>
            </ModalButton>
          </ModalButtonContainer>
        </ModalContainer>
      </Modal>

      <CreateButton onPress={goUpload}>
        <FontAwesomeIcon icon={faCirclePlus} size={50} color={'#ffa401'} />
      </CreateButton>
    </EntireContainer>
  );
}

const EntireContainer = styled.View`
  flex: 1;
  background-color: #ffffff;
`;

const CreateButton = styled.TouchableOpacity`
  position: absolute;
  left: 80%;
  top: 85%;
`;

const GifticonItemContainer = styled.TouchableOpacity`
  align-items: center;
`;

const GifticonItemText = styled.Text`
  font-family: 'Regular';
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

const ModalText = styled.Text`
  font-family: 'Regular';
  font-size: 15px;
  margin: 2%;
`;

const ModalButtonContainer = styled.View`
  flex-direction: row;
  height: 15%;
`;

const ModalButton = styled.TouchableOpacity`
  background-color: #ffa401;
  width: 30%;
  border-radius: 10px;
  align-items: center;
  justify-content: center;
  margin: 5%;
`;
const ModalButtonText = styled.Text`
  color: #ffffff;
  font-family: 'Regular';
`;

export default GifticonMainScreen;
