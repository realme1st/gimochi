/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  screenName: 'HomeScreen',
  screenArray: ['HomeScreen'],
};
const screenSlice = createSlice({
  name: 'screen',
  initialState,
  reducers: {
    setScreen(state, action) {
      state.screenName = action.payload.screenName;
    },
    addScreen(state, action) {
      state.screenArray = [...state.screenArray, action.payload.screen];
    },
    deleteScreen(state) {
      state.screenArray = state.screenArray.slice(0, state.screenArray.length - 1);
    },
  },
  extraReducers: (builder) => {},
});

export default screenSlice;
