import { React, useRef, useState, useEffect } from 'react'
import * as StompJs from '@stomp/stompjs'

const DockingChat = () => {
    const chatStyle = {
        width: '100%',
        height: '375px',
        borderRadius: '40px',
        border: '2px solid #FFFFFF',
        background: 'rgba(23, 49, 71, 0.8)',
        filter: 'drop-shadow(0px 0px 2px rgba(255, 255, 255, 0.25)) drop-shadow(0px 0px 5px rgba(0, 0, 0, 0.25))',
    }
    

    return (
        <div style={chatStyle}>
            
        </div>
    )
}

export default DockingChat