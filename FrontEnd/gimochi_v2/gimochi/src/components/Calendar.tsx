/* eslint-disable react/jsx-no-undef */
import React from 'react';
import { Calendar } from 'react-native-calendars';

function Calendars() {
  return (
    <Calendar
      // Handler which gets executed on day press. Default = undefined
      onDayPress={(day) => {
        console.log('selected day', day);
      }}
      // Handler which gets executed on day long press. Default = undefined
      onDayLongPress={(day) => {
        console.log('selected day', day);
      }}
      // Month format in calendar title. Formatting values: http://arshaw.com/xdate/#Formatting
      monthFormat={'yyyy년 MM월'}
      // Handler which gets executed when visible month changes in calendar. Default = undefined
      onMonthChange={(month) => {
        console.log('month changed', month);
      }}
      // Hide month navigation arrows. Default = false
      onPressArrowLeft={(subtractMonth) => subtractMonth()}
      // Handler which gets executed when press arrow icon right. It receive a callback can go next month
      onPressArrowRight={(addMonth) => addMonth()}
      // Disable all touch events for disabled days. can be override with disableTouchEvent in markedDates
      disableAllTouchEventsForDisabledDays={true}
      // Replace default month and year title with custom one. the function receive a date as parameter
      // Enable the option to swipe between months. Default = false
      enableSwipeMonths={true}
      style={{ width: '90%', margin: '5%', borderRadius: 10, borderWidth: 1 }}
      theme={{
        textDayFontFamily: 'Regular',
        textMonthFontFamily: 'Regular',
        textDayHeaderFontFamily: 'Regular',
      }}
    />
  );
}

export default Calendars;
