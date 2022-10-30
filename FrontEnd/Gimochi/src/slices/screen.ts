/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  screenName: 'HomeScreen',
};
const screenSlice = createSlice({
  name: 'screen',
  initialState,
  reducers: {
    setScreen(state, action) {
      state.screenName = action.payload.screenName;
    },
  },
  extraReducers: (builder) => {},
});

export default screenSlice;
