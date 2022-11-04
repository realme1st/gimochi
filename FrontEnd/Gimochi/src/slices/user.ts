/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  name: '',
  accessToken: '',
  accessTokenExpiresAt: '',
  isLogin: '',
  userId: '',
};
const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setUser(state, action) {
      state.name = action.payload.name;
    },
    setLogin(state, action) {
      state.isLogin = action.payload.isLogin;
      state.accessToken = action.payload.accessToken;
      state.accessTokenExpiresAt = action.payload.accessTokenExpiresAt;
      state.userId = action.payload.userId;
    },
  },
  extraReducers: (builder) => {},
});

export default userSlice;
