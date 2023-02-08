import React, { useState, useEffect, useRef } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import { SiRocketdotchat } from 'react-icons/si'
import SignalList from '../components/SignalComponents/SignalList'
import SignalSelected from '../components/SignalComponents/SignalSelected'

const Signal = () => {
  const tmpChatRoomSeq = useParams().tmpChatRoomSeq
  
  // 스토어에 저장된 userSeq가져오기
  // const userSeq = useSelector((state) => state.user.userSeq)
  const userSeq = 1
  const [chatRoom, setChatRoom] = useState([])

  useEffect( () => {
    axios.get('https://i8a107.p.ssafy.io/api/chatroom/'+ userSeq).then((response)=>{
      setChatRoom(response.data)
      console.log(response.data)
    })
  }, [])

  const windowStyle = {
    boxSizing: 'border-box',
    display: 'flex',
    flexDirection: 'row',
    padding: '40px',
    gap: '20px',
    justifyContent: 'center',
    alignItems: 'flex-start',
    position: 'absolute',
    width: '80%',
    height: '80%',
    background:
      'linear-gradient(180deg, rgba(0, 0, 0, 0.5) 0%, rgba(0, 0, 0, 0) 100%)',
    border: '2px solid #FFFFFF',
    filter:
      'drop-shadow(0px 0px 2px rgba(255, 255, 255, 0.25)) drop-shadow(0px 0px 5px rgba(0, 0, 0, 0.25))',
    borderRadius: '50px',
  }
  const asideStyle = {
    display: 'flex',
    width: '35%',
    flexDirection: 'column',
    padding: '10px',
    gap: '20px',
    justifyContent: 'center',
    alignItems: 'center',
  }

  const iconStyle = {
    color: 'white',
    width: '60px',
    height: '60px',
  }

  return (
    <div style={{
      display: 'flex',
      justifyContent: 'center',
      margin: '20px',
    }}>
      <div className="window" style={windowStyle}>
        <div className="aside" style={asideStyle}>
          <SiRocketdotchat style={iconStyle} />
          <h1>Signal</h1>
          <div
            className="SignalList"
            style={{
              display: 'flex',
              flexDirection: 'column',
              gap: '20px',
              width: '100%',
            }}
          >
            {chatRoom ? 
              (chatRoom.map((chat) => (
                <SignalList userSeq={userSeq} chat={chat} key={chat.chatRoomSeq} />
              ))
             ) : (
              <h1>주고받은 시그널이 없습니다.</h1>
            )}
          </div>
        </div>
        <div className="content" style={{ width: '65%' }}>
          {tmpChatRoomSeq ? (
            <SignalSelected userSeq={userSeq} roomSeq={tmpChatRoomSeq} />
          ) : (
            <div>
              <h1>메시지를 선택하세요.</h1>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}

export default Signal
