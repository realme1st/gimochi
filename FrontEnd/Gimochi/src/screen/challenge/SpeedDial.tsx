import React from 'react';
import { SpeedDial } from '@rneui/themed';

// 생성 삭제 다이얼 참고용
// 이모티콘은 FAB 참고  | 버튼은 button 참고
export default () => {
  const [open, setOpen] = React.useState(false);
  return (
    <SpeedDial
      style={{ width: 400, height: 600 }} //위치
      buttonStyle={{ backgroundColor: '#FFE7BC' }}
      isOpen={open}
      icon={{ name: 'edit', color: '#FFA401' }}
      openIcon={{ name: 'close', color: '#FFA401' }}
      onOpen={() => setOpen(!open)}
      onClose={() => setOpen(!open)}
    >
      <SpeedDial.Action
        buttonStyle={{ backgroundColor: '#FFE7BC' }}
        icon={{ name: 'add', color: '#FFA401' }}
        title='Add'
        onPress={() => console.log('Add Something')}
      />
      <SpeedDial.Action
        buttonStyle={{ backgroundColor: '#FFE7BC' }}
        icon={{ name: 'delete', color: '#FFA401' }}
        title='Delete'
        onPress={() => console.log('Delete Something')}
      />
    </SpeedDial>
  );
};
