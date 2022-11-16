/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/no-misused-promises */
/* eslint-disable @typescript-eslint/restrict-template-expressions */
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Config from 'react-native-config';
import { useSelector } from 'react-redux';
import { RootState } from '../../store/reducer';
import { useAppDispatch } from '../../store';
import reloadSlice from '../../slices/reload';
import styled from 'styled-components/native';
import { singleNotification } from '../../api/API';

function FriendRecomScreen() {
  const accessToken = useSelector((state: RootState) => state.user.accessToken);
  const userId = Number(useSelector((state: RootState) => state.user.userId));
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
        const notification = singleNotification(id, userId, 1);
        console.log(notification);
      })
      .catch(function (error) {
        console.log(error);
      });
  };
  return (
    <EntireContainer>
      <FriendRecomTitleContainer>
        <FriendRecomTitle>알 수도 있는 친구들</FriendRecomTitle>
      </FriendRecomTitleContainer>
      <FriendListContainer>
        {friends.map(
          (friend, index) =>
            !friend.friend && (
              <FriendItemContainer key={index}>
                <FriendItemText>{friend.userName}</FriendItemText>
                {!friend.friend && (
                  <FriendItemButton onPress={() => requestFriend(friend.userId)}>
                    <FriendItemText>친구요청</FriendItemText>
                  </FriendItemButton>
                )}
              </FriendItemContainer>
            ),
        )}
      </FriendListContainer>
    </EntireContainer>
  );
}

const EntireContainer = styled.ScrollView`
  background-color: #ffffff;
  flex: 1;
`;

const FriendRecomTitle = styled.Text`
  font-family: 'Regular';
  font-size: 30px;
  color: #000000;
  margin-bottom: 2%;
`;

const FriendRecomTitleContainer = styled.View`
  margin: 5% 5% 0;
  border-bottom-width: 1px;
  border-bottom-color: #ffa401;
  flex-direction: row;
`;

const FriendListContainer = styled.View`
  margin: 3%;
`;

const FriendItemContainer = styled.View`
  align-items: center;
  flex-direction: row;
  border-radius: 10px;
  background-color: #ffe7bc;
  margin: 2%;
  height: 50px;
  elevation: 10;
`;

const FriendItemText = styled.Text`
  font-family: 'Regular';
  font-size: 20px;
  margin-left: 5%;
  color: #000000;
`;

const FriendItemButton = styled.TouchableOpacity`
  margin-left: 50%;
`;

export default FriendRecomScreen;
