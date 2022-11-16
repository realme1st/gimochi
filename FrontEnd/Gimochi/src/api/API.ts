import axios from 'axios';
import Config from 'react-native-config';

export const axiosBasic = axios.create({
  baseURL: Config.API_URL,
});

export const singleNotification = async (receiverId: number, senderId: number, type: number) => {
  try {
    const response = await axiosBasic.post('/notification/message/single', {
      receiverId: receiverId,
      senderId: senderId,
      type: type,
    });
    return response;
  } catch (error) {
    console.log(error);
  }
};

export const multiNotification = async (userId: number, type: number) => {
  try {
    const response = await axiosBasic.post('/notification/message/single', {
      userId: userId,
      type: type,
    });
    return response;
  } catch (error) {
    console.log(error);
  }
};
