import { useRef, useState, useEffect } from 'react'
import * as StompJs from '@stomp/stompjs'

const DockingChat = () => {
  const chatStyle = {
    width: '100%',
    height: '100%',
    borderRadius: '40px',
    border: '2px solid #FFFFFF',
    background:
      'linear-gradient(180deg, rgba(0, 0, 0, 0.5) 0%, rgba(0, 0, 0, 0) 100%)',
    filter:
      'drop-shadow(0px 0px 2px rgba(255, 255, 255, 0.25)) drop-shadow(0px 0px 5px rgba(0, 0, 0, 0.25))',
  }

  return <div style={chatStyle}></div>
}

export default DockingChat
