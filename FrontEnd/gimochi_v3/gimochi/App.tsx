import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import TabNavigation from './src/navigation/TabNavigation';
import LoginScreen from './src/screen/LoginScreen';

const Stack = createNativeStackNavigator();
function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName='Home'>
        <Stack.Screen name='Home' component={TabNavigation} options={{ headerShown: false }}></Stack.Screen>
        <Stack.Screen name='Login' component={LoginScreen} options={{ title: '로그인' }}></Stack.Screen>
      </Stack.Navigator>
    </NavigationContainer>
  );
}

export default App;
