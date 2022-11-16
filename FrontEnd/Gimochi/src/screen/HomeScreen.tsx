/* eslint-disable @typescript-eslint/require-await */
/* eslint-disable @typescript-eslint/restrict-plus-operands */
/* eslint-disable no-var */
/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable no-prototype-builtins */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/restrict-template-expressions */
/* eslint-disable @typescript-eslint/no-floating-promises */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import React, { useEffect, useState } from 'react';
import { Text } from 'react-native';
import styled from 'styled-components/native';
import Calendars from '../components/Calendar';
import { useSelector } from 'react-redux';
import { RootState } from '../store/reducer';
import { useAppDispatch } from '../store';
import screenSlice from '../slices/screen';
import notificationSlice from '../slices/notification';
import axios from 'axios';
import Config from 'react-native-config';

function HomeScreen() {
  const userId = useSelector((state: RootState) => state.user.userId);
  const userNickname = useSelector((state: RootState) => state.user.userNickname);
  const reload = useSelector((state: RootState) => state.reload.reload);
  const dispatch = useAppDispatch();
  const [gifticons, setGifticons] = useState([]);
  const [data, setData] = useState({});
  const notifications = useSelector((state: RootState) => state.notification.notification);
  useEffect(() => {
    dispatch(
      screenSlice.actions.addScreen({
        screen: 'HomeScreen',
      }),
    );
    return () => {
      dispatch(screenSlice.actions.deleteScreen());
    };
  }, []);

  useEffect(() => {
    axios
      .get(`${Config.API_URL}/gifticon/uid/${userId}`)
      .then(function (response) {
        console.log(response);
        setGifticons(response.data.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [reload]);

  useEffect(() => {
    let notiCount = 0;
    axios
      .all([
        axios.get(`${Config.API_URL}/user/following-request/${userId}`),
        axios.get(`${Config.API_URL}/challenge/challengeInvite/challengeList/${userId}`),
      ])
      .then(
        axios.spread((response1, response2) => {
          const followList = response1.data.data;
          notiCount = notiCount + followList.length;
          const inviteList = response2.data.data;
          notiCount = notiCount + inviteList.length;
        }),
      )
      .catch(function (error) {
        console.log(error);
      })
      .finally(() => {
        dispatch(notificationSlice.actions.setNotification({ notification: Number(notiCount) }));
      });
  }, []);

  const selected = { color: '#00bbf2', selectedDotColor: 'blue' };

  useEffect(() => {
    var markedDates = {};
    gifticons.forEach((gifticon) => {
      if (!gifticon.gifticonUsed) {
        var period = gifticon.gifticonPeriod;
        // console.log(period);
        if (!markedDates.hasOwnProperty(period)) {
          markedDates[period] = { dots: [selected], gifticons: [gifticon], clickable: true };
        } else {
          markedDates[period].dots.push(selected);
          markedDates[period].gifticons.push(gifticon);
        }
      }
    });
    console.log(markedDates);
    setData(markedDates);
  }, [gifticons]);

  return (
    <EntireContainer>
      <Text>{userId}</Text>
      <Text>{userNickname}</Text>
      <Calendars type='multi-dot' data={data} />
    </EntireContainer>
  );
}
const EntireContainer = styled.View`
  flex: 1;
  background-color: #ffffff;
`;

export default HomeScreen;
