/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  userNickname: '',
  accessToken: '',
  accessTokenExpiresAt: '',
  isLogin: '',
  userId: '',
  phoneToken: '',
};
const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setLogin(state, action) {
      state.userNickname = action.payload.userNickname;
      state.isLogin = action.payload.isLogin;
      state.accessToken = action.payload.accessToken;
      state.accessTokenExpiresAt = action.payload.accessTokenExpiresAt;
      state.userId = action.payload.userId;
      state.phoneToken = action.payload.phoneToken;
    },
    setPhoneToken(state, action) {
      state.phoneToken = action.payload;
    },
  },
  extraReducers: (builder) => {},
});

export default userSlice;
