/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  name: '',
  accessToken: '',
  isLogin: '',
};
const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setUser(state, action) {
      state.name = action.payload.name;
      state.accessToken = action.payload.accessToken;
    },
    setLogin(state, action) {
      state.isLogin = action.payload.isLogin;
    },
  },
  extraReducers: (builder) => {},
});

export default userSlice;
