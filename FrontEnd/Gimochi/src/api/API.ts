import axios from 'axios';

export const URL = 'https://k7a205.p.ssafy.io/api';

export const axiosBasic = axios.create({
  baseURL: URL,
  // headers: {
  //   'Content-Type': 'application/json',
  // },
  timeout: 10000,
});

// 토큰 유효성 검사하는 api 작성하기
