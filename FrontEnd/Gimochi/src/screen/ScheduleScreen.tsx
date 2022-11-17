/* eslint-disable @typescript-eslint/no-unsafe-call */
/* eslint-disable @typescript-eslint/no-floating-promises */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
/* eslint-disable @typescript-eslint/restrict-template-expressions */
import React, { useEffect, useState } from 'react';
import { Text, View } from 'react-native';
import styled from 'styled-components/native';
import Calendars from '../components/Calendar';
import { useAppDispatch } from '../store';
import screenSlice from '../slices/screen';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../store/reducer';

function ScheduleScreen() {
  const userId = useSelector((state: RootState) => state.user.userId);
  const accessToken = useSelector((state: RootState) => state.user.accessToken);
  const reload = useSelector((state: RootState) => state.reload.reload);
  const [loading, setLoading] = useState(true);
  const dispatch = useAppDispatch();
  const [scheduleList, setScheduleList] = useState([]);

  useEffect(() => {
    dispatch(
      screenSlice.actions.addScreen({
        screen: 'ScheduleScreen',
      }),
    );
    return () => {
      console.log('unmount');
      dispatch(screenSlice.actions.deleteScreen());
    };
  }, []);

  useEffect(() => {
    axios
      .get(`${Config.API_URL}/user/follower/${userId}`, {
        headers: {
          token: accessToken,
        },
      })
      .then(function (response) {
        console.log(response);
        const friends = response.data.data;
        friends.forEach((friend: { userId: any }) => {
          // getSchedule(friend.userId);
          console.log(friend.userId);
        });
      })
      .catch(function (error) {
        console.log(error);
      })
      .finally(() => {
        console.log('1', scheduleList);
        setLoading(false);
      });
  }, [reload]);

  // if (loading)
  //   return (
  //     <View>
  //       <Text>Loading...</Text>
  //     </View>
  //   );
  return (
    <View>
      <Text>일정관리</Text>
      <Calendars />
    </View>
  );
}

export default ScheduleScreen;
