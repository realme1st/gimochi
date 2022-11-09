/* eslint-disable @typescript-eslint/restrict-template-expressions */
import React, { useState, useEffect } from 'react';
import { TouchableOpacity, StyleSheet, Text, View } from 'react-native';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';
import { useAppDispatch } from '../../store';
import reloadSlice from '../../slices/reload';
import styled from 'styled-components/native';

function FriendRecomScreen() {
  const accessToken = useSelector((state: RootState) => state.user.accessToken);
  const userId = useSelector((state: RootState) => state.user.userId);
  const reload = useSelector((state: RootState) => state.reload.reload);
  const [friends, setFriends] = useState([]);
  const dispatch = useAppDispatch();

  useEffect(() => {
    axios
      .get(`${Config.API_URL}/kakao/friends`, {
        headers: {
          token: accessToken,
        },
      })
      .then(function (response) {
        console.log(response);
        setFriends(response.data.data.elements);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [reload]);

  const addFriend = async (id: number) => {
    await axios
      .post(`${Config.API_URL}/user/follow`, { followerUserId: userId, followingUserId: id })
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
    <EntireContainer>
      <Text>알 수도 있는 친구들</Text>
      {friends.map((friend, index) => (
        <Text key={index}>{friend.profile_nickname}</Text>
      ))}
    </EntireContainer>
  );
}

const EntireContainer = styled.ScrollView`
  background-color: #ffffff;
`;

export default FriendRecomScreen;
