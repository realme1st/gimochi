import React, { useEffect } from 'react';
import { Provider } from 'react-redux';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import store from './src/store';
import TabNavigation from './src/navigation/TabNavigation';
import LoginScreen from './src/screen/LoginScreen';
import { useSelector } from 'react-redux';
import { RootState } from './src/store/reducer';
import SplashScreen from 'react-native-splash-screen';

const Stack = createNativeStackNavigator();

function AppInner() {
  SplashScreen.hide();
  const isLoggedIn = useSelector((state: RootState) => !!state.user.accessToken);

  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName='Home'>
        <Stack.Screen name='Home' component={TabNavigation} options={{ headerShown: false }}></Stack.Screen>
        <Stack.Screen name='Login' component={LoginScreen} options={{ title: '로그인' }}></Stack.Screen>
      </Stack.Navigator>
    </NavigationContainer>
  );
}
function App() {
  return (
    <Provider store={store}>
      <AppInner />
    </Provider>
  );
}

export default App;
