import axios from 'axios';
// import { useSelector } from 'react-redux';
// import { RootState } from '../store/reducer';

export const URL = 'https://k7a205.p.ssafy.io/api';

// const accessToken = useSelector((state: RootState) => state.user.accessToken);

export const axiosBasic = axios.create({
  baseURL: URL,
  // headers: {
  //   AccessToken: accessToken,
  // },
  timeout: 10000,
});
