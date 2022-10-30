/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import { combineReducers } from 'redux';

import screenSlice from '../slices/screen';
import userSlice from '../slices/user';

const rootReducer = combineReducers({
  user: userSlice.reducer,
  screen: screenSlice.reducer,
});

export type RootState = ReturnType<typeof rootReducer>;
export default rootReducer;
