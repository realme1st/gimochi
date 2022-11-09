/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/no-misused-promises */
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
        console.log(response.data.data);
        setFriends(response.data.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [reload]);

  const requestFriend = async (id: number) => {
    await axios
      .post(`${Config.API_URL}/user/follow-request`, { followerUserId: userId, followingUserId: id })
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
        <View key={index}>
          <Text>{friend.userName}</Text>
          {!friend.friend && (
            <TouchableOpacity onPress={() => requestFriend(friend.userId)}>
              <Text>친구요청</Text>
            </TouchableOpacity>
          )}
        </View>
      ))}
    </EntireContainer>
  );
}

const EntireContainer = styled.ScrollView`
  background-color: #ffffff;
`;

export default FriendRecomScreen;
