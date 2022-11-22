/* eslint-disable @typescript-eslint/no-misused-promises */
/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/restrict-template-expressions */
import React, { useState, useEffect } from 'react';
import { View, Text, TouchableOpacity } from 'react-native';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../store/reducer';
import { useAppDispatch } from '../store';
import reloadSlice from '../slices/reload';
import styled from 'styled-components/native';

function NotificationScreen() {
  const userId = useSelector((state: RootState) => state.user.userId);
  const reload = useSelector((state: RootState) => state.reload.reload);
  const [friends, setFriends] = useState([]);
  const dispatch = useAppDispatch();

  useEffect(() => {
    axios
      .get(`${Config.API_URL}/user/following-request/${userId}`)
      .then(function (response) {
        console.log(response);
        setFriends(response.data.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [reload]);

  const acceptFriend = async (id: number) => {
    console.log(id);
    await axios
      .post(`${Config.API_URL}/user/follow-accept`, { followerUserId: id, followingUserId: userId })
      .then(function (response) {
        console.log(response);
        dispatch(
          reloadSlice.actions.setReload({
            reload: String(new Date()),
          }),
        );
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  const rejectFriend = async (id: number) => {
    console.log(id);

    await axios
      .post(`${Config.API_URL}/user/follow-reject`, { followerUserId: id, followingUserId: userId })
      .then(function (response) {
        console.log(response);
        dispatch(
          reloadSlice.actions.setReload({
            reload: String(new Date()),
          }),
        );
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  return (
    <>
      <Text>알림스크린</Text>
      {friends.map((friend, index) => (
        <View key={index}>
          <Text>{friend.userName}</Text>
          <TouchableOpacity onPress={() => acceptFriend(friend.userId)}>
            <Text>친구추가</Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={() => rejectFriend(friend.userId)}>
            <Text>거절</Text>
          </TouchableOpacity>
        </View>
      ))}
    </>
  );
}

export default NotificationScreen;
