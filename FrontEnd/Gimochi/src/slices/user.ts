/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  userNickname: '',
  accessToken: '',
  accessTokenExpiresAt: '',
  userId: '',
  isFriendAccess: false,
};
const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setLogin(state, action) {
      state.userNickname = action.payload.userNickname;
      state.accessToken = action.payload.accessToken;
      state.accessTokenExpiresAt = action.payload.accessTokenExpiresAt;
      state.userId = action.payload.userId;
      state.isFriendAccess = action.payload.isFriendAccess;
    },
    setLogout(state) {
      state.userNickname = '';
      state.accessToken = '';
      state.accessTokenExpiresAt = '';
      state.userId = '';
      state.isFriendAccess = false;
    },
    setToken(state, action) {
      state.accessToken = action.payload.accessToken;
    },
    setFriendAccess(state) {
      state.isFriendAccess = true;
    },
  },
  extraReducers: (builder) => {},
});

export default userSlice;
